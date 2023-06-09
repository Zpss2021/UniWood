package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.util.interfaces.Model;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.concurrent.TimeoutException;

public class RegisterModel implements Model {
    private String username;
    private String password;
    private String university;
    private String avatarBase64;
    private String[] universities;

    public RegisterModel() {
        init();
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
        if(avatarBase64 != null)
            return avatarBase64;
        return "DEFAULT";
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String[] getUniversities(int count) throws InterruptedException, TimeoutException {
        if (universities != null)
            return universities;
        if(count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.UNIV_LIST))).start();
        if(count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return getUniversities(count + 1);
    }

    public void setUniversities(String[] universities) {
        this.universities = universities;
    }

    @Override
    public void init() {
        username = null;
        password = null;
        university = null;
        avatarBase64 = null;
        universities = null;
    }
}
