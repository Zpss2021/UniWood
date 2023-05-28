package info.zpss.uniwood.desktop.server.util.socket;

import info.zpss.uniwood.desktop.server.entity.*;
import info.zpss.uniwood.desktop.common.MsgProto;
import info.zpss.uniwood.desktop.server.Main;
import info.zpss.uniwood.desktop.server.service.UserService;
import info.zpss.uniwood.desktop.server.service.ZoneService;
import info.zpss.uniwood.desktop.server.util.ServerLogger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static info.zpss.uniwood.desktop.common.Command.*;

public class SocketHandler extends Thread {
    private final Socket socket;
    private final SocketListener listener;
    private final ScheduledExecutorService checker;    // 定时修改在线状态为离线
    private final ScheduledExecutorService heartbeat;   // 若客户端离线超过一定时间，则断开连接并将用户状态置为离线
    private Integer userId;
    private boolean online;
    private BufferedReader reader;
    private PrintWriter writer;

    public SocketHandler(Socket socket, SocketListener listener) {
        this.socket = socket;
        this.listener = listener;
        this.checker = Executors.newSingleThreadScheduledExecutor();
        this.heartbeat = Executors.newSingleThreadScheduledExecutor();
        this.userId = null;
        this.online = true;
    }

    public void send(String message) {
        writer.println(message);
        writer.flush();
    }

    private void initSchedule() {
        checker.scheduleAtFixedRate(() -> online = false, 0, 3, TimeUnit.SECONDS);
        heartbeat.scheduleAtFixedRate(() -> {
            try {
                if (!online) {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(500);
                        if (online) return;
                    }
                    Main.logger().add(String.format("客户端%s超时未响应，断开连接！", this),
                            ServerLogger.Type.WARN, Thread.currentThread());
                    socket.close();
                }
            } catch (InterruptedException e) {
                Main.logger().add(String.format("客户端%s心跳检测线程被中断！", this),
                        ServerLogger.Type.WARN, Thread.currentThread());
            } catch (IOException e) {
                Main.logger().add(String.format("客户端%s套接字连接异常！", this),
                        ServerLogger.Type.WARN, Thread.currentThread());
                Main.logger().add(e, Thread.currentThread());
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    private String handleMessage(String message) {
        if (Main.debug())
            Main.logger().add(String.format("收到客户端%s消息：%s", this, message), Thread.currentThread());
        MsgProto msgProto = MsgProto.parse(message);
        if (msgProto.cmd == null) {  // TODO
            Main.logger().add(String.format("客户端%s消息解析失败！", this), ServerLogger.Type.WARN, Thread.currentThread());
            Main.logger().add(String.format("未知信息：%s", message), ServerLogger.Type.WARN, Thread.currentThread());
            return MsgProto.build(UNKNOWN).toString();
        }
        switch (msgProto.cmd) {
            case LOGIN:
                User loginUser = UserService.getInstance().login(msgProto.args[0], msgProto.args[1]);
                if (loginUser != null) {
                    if (loginUser.getStatus().equals("DISABLED"))
                        return MsgProto.build(LOGIN_FAILED, "该用户已被禁用！").toString();
                    Main.logger().add(String.format("用户%s登录成功！", loginUser.getUsername()),
                            ServerLogger.Type.INFO, Thread.currentThread());
                    userId = loginUser.getId();
                    return MsgProto.build(LOGIN_SUCCESS, loginUser.getId().toString(), loginUser.getUsername(),
                            loginUser.getUniversity(), loginUser.getAvatar()).toString();
                }
                return MsgProto.build(LOGIN_FAILED, "登录失败，请检查用户名和密码后重试！").toString();
            case LOGOUT:
                userId = null;
                // TODO
                break;
            case HEARTBEAT:
                online = true;
                break;
            case UNIV_LIST:
                String[] universities = ZoneService.getInstance().getUniversities();
                return MsgProto.build(UNIV_LIST, universities).toString();
            case REGISTER:
                User registerUser = UserService.getInstance().register(msgProto.args[0], msgProto.args[1],
                        msgProto.args[2], msgProto.args[3]);
                if (registerUser != null) {
                    Main.logger().add(String.format("用户%s注册成功！", registerUser.getUsername()),
                            ServerLogger.Type.INFO, Thread.currentThread());
                    registerUser = UserService.getInstance().login(registerUser.getUsername(), registerUser.getPassword());
                    userId = registerUser.getId();
                    return MsgProto.build(REGISTER_SUCCESS, registerUser.getId().toString(), registerUser.getUsername(),
                            registerUser.getUniversity(), registerUser.getAvatar()).toString();
                }
                return MsgProto.build(REGISTER_FAILED, "注册失败，请检查用户名和密码后重试！").toString();
            default:
                Main.logger().add(String.format("收到客户端%s未知命令：%s", this, msgProto.cmd),
                        ServerLogger.Type.WARN, Thread.currentThread());
                break;
        }
        return null;    // 当返回值为null时，不发送任何消息
    }

    @Override
    public String toString() {
        return String.format("%s:%d", socket.getInetAddress(), socket.getPort());
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                    StandardCharsets.UTF_8)));
            initSchedule();
            String rec, snd;
            while (!socket.isClosed() && ((rec = reader.readLine()) != null)) {
                snd = handleMessage(rec);
                if (snd != null)
                    send(snd);
            }
        } catch (SocketException e) {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Main.logger().add(String.format("客户端%s套接字连接异常！", this),
                            ServerLogger.Type.WARN, Thread.currentThread());
                    Main.logger().add(ex, Thread.currentThread());
                }
            }
        } catch (IOException e) {
            if (socket.isClosed())
                return;
            Main.logger().add(String.format("客户端%s读取消息流I/O异常！", this), ServerLogger.Type.WARN, Thread.currentThread());
            Main.logger().add(e, Thread.currentThread());
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException ex) {
                Main.logger().add(String.format("客户端%s关闭消息流I/O异常！", this),
                        ServerLogger.Type.WARN, Thread.currentThread());
                Main.logger().add(ex, Thread.currentThread());
            }
            checker.shutdown();
            heartbeat.shutdown();
            listener.removeHandler(this);
            if (userId != null)
                UserService.getInstance().offlineUser(userId);
            Main.logger().add(String.format("客户端%s断开连接", this), ServerLogger.Type.INFO, Thread.currentThread());
        }
    }
}