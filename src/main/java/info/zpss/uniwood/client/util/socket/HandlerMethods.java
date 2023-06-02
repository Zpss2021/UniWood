package info.zpss.uniwood.client.util.socket;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.controller.EditController;
import info.zpss.uniwood.client.controller.LoginController;
import info.zpss.uniwood.client.controller.RegisterController;
import info.zpss.uniwood.client.controller.UserCenterController;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.util.ClientLogger;
import info.zpss.uniwood.client.util.builders.UserBuilder;
import info.zpss.uniwood.common.MsgProto;

public class HandlerMethods {
    public static void loginSuccess(MsgProto msg) {
        Main.logger().add(String.format("用户%s登录成功！", msg.args[1]),
                ClientLogger.Type.INFO, Thread.currentThread());
        User loginUser = new User();
        loginUser.update(msg);
        LoginController.getInstance().loginSuccess(loginUser);
    }

    public static void loginFailed(MsgProto msg) {
        Main.logger().add(String.format("用户登录失败：%s", msg.args[0]),
                ClientLogger.Type.INFO, Thread.currentThread());
        LoginController.getInstance().loginFailed(msg.args[0]);
    }

    public static void registerSuccess(MsgProto msg) {
        User registerUser = new User();
        registerUser.update(msg);
        RegisterController.getInstance().registerSuccess(registerUser);
    }

    public static void registerFailed(MsgProto msg) {
        RegisterController.getInstance().registerFailed(msg.args[0]);
    }

    public static void universityList(MsgProto msg) {
        RegisterController.getInstance().getModel().setUniversities(msg.args);
    }

    public static void userInfo(MsgProto msg) {
        User user = new User();
        user.update(msg);
        UserBuilder.getInstance().add(user);
    }

    public static void userCenterUpdate(MsgProto msg) {
        UserCenterController.getInstance().getModel().update(msg);
    }

    public static void editSuccess() {
        EditController.getInstance().editSuccess();
    }

    public static void editFailed(MsgProto msg) {
        EditController.getInstance().editFailed(msg.args[0]);
    }
}
