package info.zpss.uniwood.server.util.socket;

import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;
import info.zpss.uniwood.server.Main;
import info.zpss.uniwood.server.entity.Floor;
import info.zpss.uniwood.server.entity.Post;
import info.zpss.uniwood.server.entity.User;
import info.zpss.uniwood.server.entity.Zone;
import info.zpss.uniwood.server.service.FloorService;
import info.zpss.uniwood.server.service.PostService;
import info.zpss.uniwood.server.service.UserService;
import info.zpss.uniwood.server.service.ZoneService;
import info.zpss.uniwood.server.util.ServerLogger;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketHandler extends Thread {
    private final Socket socket;
    private final SocketListener listener;
    private final ScheduledExecutorService beater;    // 定时修改在线状态为离线
    private final ScheduledExecutorService kicker;   // 若客户端状态为离线超过一定时间，则断开连接
    private Integer userId;
    private boolean onConn;
    private BufferedReader reader;
    private PrintWriter writer;

    public SocketHandler(Socket socket, SocketListener listener) {
        this.socket = socket;
        this.listener = listener;
        this.beater = Executors.newSingleThreadScheduledExecutor();
        this.kicker = Executors.newSingleThreadScheduledExecutor();
        this.userId = null;
        this.onConn = true;
    }

    public void send(MsgProto msg) {
        String snd = MsgProto.charToLinebreak(msg);
        if (Main.debug())
            Main.logger().add(String.format("向客户端%s发送消息：%s", this,
                    (snd.length() > 64) ? snd.substring(0, 61) + "..." : snd), Thread.currentThread());
        writer.println(snd);
        writer.flush();
    }

    private void initSchedule() {
        beater.scheduleAtFixedRate(() -> onConn = false, 0, 3, TimeUnit.SECONDS);
        kicker.scheduleAtFixedRate(() -> {
            try {
                if (!onConn) {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(500);
                        if (onConn) return;
                    }
                    Main.logger().add(String.format("客户端%s超时未响应，断开连接！", this),
                            ServerLogger.Type.WARN, Thread.currentThread());
                    userId = null;
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

    private MsgProto handleMessage(String message) {
        if (Main.debug())
            Main.logger().add(String.format("收到客户端%s消息：%s", this,
                    message.length() > 64 ? message.substring(0, 61) + "..." : message), Thread.currentThread());
        MsgProto msgProto = MsgProto.parse(message);
        if (msgProto.cmd == null) {
            Main.logger().add(String.format("客户端%s消息解析失败！", this), ServerLogger.Type.WARN, Thread.currentThread());
            Main.logger().add(String.format("未知信息：%s", message), ServerLogger.Type.WARN, Thread.currentThread());
            return MsgProto.build(Command.UNKNOWN);
        }
        switch (msgProto.cmd) {
            case LOGIN:
                User loginUser = UserService.getInstance().login(msgProto.args[0], msgProto.args[1]);
                if (loginUser != null) {
                    if (loginUser.getStatus().equals("DISABLED"))
                        return MsgProto.build(Command.LOGIN_FAILED, "该用户已被禁用！");
                    Main.logger().add(String.format("用户%s登录成功！", loginUser.getUsername()),
                            ServerLogger.Type.INFO, Thread.currentThread());
                    userId = loginUser.getId();
                    return MsgProto.build(Command.LOGIN_SUCCESS,
                            loginUser.getId().toString(),
                            loginUser.getUsername(),
                            loginUser.getUniversity(),
                            loginUser.getAvatar()
                    );
                }
                return MsgProto.build(Command.LOGIN_FAILED, "登录失败，请检查用户名和密码后重试！");
            case LOGOUT:
                Main.logger().add(String.format("用户%s已登出！", userId), Thread.currentThread());
                UserService.getInstance().offlineUser(userId);
                userId = null;
                break;
            case HEARTBEAT:
                onConn = true;
                if (msgProto.args.length > 0)
                    if (userId == null || userId.equals(Integer.valueOf(msgProto.args[0]))) {
                        userId = Integer.valueOf(msgProto.args[0]);
                        UserService.getInstance().onlineUser(userId);
                    }
                break;
            case REGISTER:
                User registerUser = UserService.getInstance().register(msgProto.args[0], msgProto.args[1],
                        msgProto.args[2], msgProto.args[3]);
                if (registerUser != null) {
                    Main.logger().add(String.format("用户%s注册成功！", registerUser.getUsername()),
                            ServerLogger.Type.INFO, Thread.currentThread());
                    registerUser = UserService.getInstance().login(registerUser.getUsername(), registerUser.getPassword());
                    userId = registerUser.getId();
                    return MsgProto.build(Command.REGISTER_SUCCESS,
                            registerUser.getId().toString(),
                            registerUser.getUsername(),
                            registerUser.getUniversity(),
                            registerUser.getAvatar()
                    );
                }
                return MsgProto.build(Command.REGISTER_FAILED, "注册失败，请检查用户名和密码后重试！");
            case UNIV_LIST:
                String[] universities = ZoneService.getInstance().getUniversities();
                return MsgProto.build(Command.UNIV_LIST, universities);
            case USER_INFO:
                User user = UserService.getInstance().getUser(Integer.valueOf(msgProto.args[0]));
                return MsgProto.build(Command.USER_INFO,
                        user.getId().toString(),
                        user.getUsername(),
                        user.getUniversity(),
                        user.getAvatar()
                );
            case FOLW_LIST:
                List<User> followList = UserService.getInstance().getFollowings(Integer.valueOf(msgProto.args[0]));
                String[] followListStr = followList
                        .stream()
                        .map(item -> item.getId().toString())
                        .toArray(String[]::new);
                return MsgProto.build(Command.FOLW_LIST, followListStr);
            case FANS_LIST:
                List<User> fansList = UserService.getInstance().getFollowers(Integer.valueOf(msgProto.args[0]));
                String[] fansListStr = fansList
                        .stream()
                        .map(item -> item.getId().toString())
                        .toArray(String[]::new);
                return MsgProto.build(Command.FANS_LIST, fansListStr);
            case POST_LIST:
                List<Post> postList = PostService.getInstance().getPostsByUserId(Integer.valueOf(msgProto.args[0]));
                String[] postListStr = postList
                        .stream()
                        .map(item -> item.getId().toString())
                        .toArray(String[]::new);
                return MsgProto.build(Command.POST_LIST, postListStr);
            case ZONE_LIST:
                List<Zone> zoneList = ZoneService.getInstance().getZonesByUserId(Integer.valueOf(msgProto.args[0]));
                String[] zoneListStr = zoneList
                        .stream()
                        .map(item -> item.getId().toString())
                        .toArray(String[]::new);
                return MsgProto.build(Command.ZONE_LIST, zoneListStr);
            case FAVOR_LIST:
                List<Post> favorList = PostService.getInstance().getFavorsByUserId(Integer.valueOf(msgProto.args[0]));
                String[] favorListStr = favorList
                        .stream()
                        .map(item -> item.getId().toString())
                        .toArray(String[]::new);
                return MsgProto.build(Command.FAVOR_LIST, favorListStr);
            case EDIT_INFO:
                if (!msgProto.args[0].equals(userId.toString()) || msgProto.args.length != 5)
                    break;
                User editUser = UserService.getInstance().editUser(Integer.valueOf(msgProto.args[0]), msgProto.args[1],
                        msgProto.args[2], msgProto.args[3], msgProto.args[4]);
                if (editUser != null) {
                    Main.logger().add(String.format("用户%s修改信息成功！", editUser.getUsername()),
                            ServerLogger.Type.INFO, Thread.currentThread());
                    return MsgProto.build(Command.EDIT_SUCCESS,
                            editUser.getId().toString(),
                            editUser.getUsername(),
                            editUser.getUniversity(),
                            editUser.getAvatar()
                    );
                }
                return MsgProto.build(Command.EDIT_FAILED, "修改信息失败，请检查后重试！");
            case POST_INFO:
                Post post = PostService.getInstance().getPost(Integer.valueOf(msgProto.args[0]));
                Floor firstFloor = FloorService.getInstance().getFloor(1, post.getId());
                Integer floorCount = PostService.getInstance().getFloorCount(post.getId());
                return MsgProto.build(Command.POST_INFO,
                        post.getId().toString(),
                        post.getZoneId().toString(),
                        firstFloor.getAuthorId().toString(),
                        String.valueOf(firstFloor.getCreateTime().getTime()),
                        floorCount.toString()
                );
            case ZONE_INFO:
                Zone zone = ZoneService.getInstance().getZone(Integer.valueOf(msgProto.args[0]));
                return MsgProto.build(Command.ZONE_INFO,
                        zone.getId().toString(),
                        zone.getName(),
                        zone.getDescription(),
                        zone.getIcon()
                );
            case FLOR_INFO:
                Floor floor = FloorService.getInstance().getFloor(Integer.valueOf(msgProto.args[0]),
                        Integer.valueOf(msgProto.args[1]));
                return MsgProto.build(Command.FLOR_INFO,
                        floor.getId().toString(),
                        msgProto.args[1],
                        floor.getAuthorId().toString(),
                        String.valueOf(floor.getCreateTime().getTime()),
                        floor.getContent()
                );
            case ZONE_POST:
                List<Post> zonePostList = PostService
                        .getInstance()
                        .getLimitPostsByZoneId(
                                Integer.valueOf(msgProto.args[0]),
                                Integer.valueOf(msgProto.args[1]),
                                Integer.valueOf(msgProto.args[2]));
                String[] zonePostListStr = zonePostList
                        .stream()
                        .map(item -> item.getId().toString())
                        .toArray(String[]::new);
                return MsgProto.build(Command.ZONE_POST, zonePostListStr);
            case PUBLISH:
                PostService.getInstance().addPost(
                        Integer.valueOf(msgProto.args[0]),
                        Integer.valueOf(msgProto.args[1]),
                        MsgProto.linebreakToChar(msgProto.args[2])
                );
                break;
            case FAVOR:
                UserService.getInstance().addFavor(Integer.valueOf(msgProto.args[0]),
                        Integer.valueOf(msgProto.args[1]));
                break;
            case UNFAVOR:
                UserService.getInstance().removeFavor(Integer.valueOf(msgProto.args[0]),
                        Integer.valueOf(msgProto.args[1]));
                break;
            case DEL_POST:
                PostService.getInstance().delPost(Integer.valueOf(msgProto.args[0]));
                break;
            case REPLY:
                FloorService.getInstance().addFloor(
                        Integer.valueOf(msgProto.args[0]),
                        Integer.valueOf(msgProto.args[1]),
                        MsgProto.linebreakToChar(msgProto.args[2])
                );
                break;
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
            String rec;
            while (!socket.isClosed() && ((rec = reader.readLine()) != null)) {
                MsgProto snd = handleMessage(rec);
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
            beater.shutdown();
            kicker.shutdown();
            listener.removeHandler(this);
            if (userId != null)
                UserService.getInstance().offlineUser(userId);
            Main.logger().add(String.format("客户端%s断开连接", this), ServerLogger.Type.INFO, Thread.currentThread());
        }
    }
}