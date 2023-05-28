package info.zpss.uniwood.desktop.client.model;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.client.util.interfaces.Model;
import info.zpss.uniwood.desktop.common.Command;
import info.zpss.uniwood.desktop.common.ProtoMsg;

import java.util.Objects;

public class RegisterModel implements Model {
    private String username;
    private String password;
    private String university;
    private String avatarBase64;
    private String[] universities;

    public RegisterModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getAvatarBase64() {
        // 在数据库中由触发器实现插入默认头像Base64
        return Objects.requireNonNullElse(avatarBase64, "DEFAULT");
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String[] getUniversities() throws InterruptedException {
        if (universities != null)
            return universities;
        new Thread(() -> Main.connection().send(ProtoMsg.build(Command.UNIV_LIST))).start();
        Thread.sleep(500);
        return getUniversities();
    }

    public void setUniversities(String[] universities) {
        this.universities = universities;
    }
}
