package info.zpss.uniwood.desktop.client.model.view;

public class LoginModel {
    private String username;
    private String password;
    private boolean rememberMe;
    private boolean autoLogin;

    public LoginModel() {
    }

    public LoginModel(String username, String password, boolean rememberMe, boolean autoLogin) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
        this.autoLogin = autoLogin;
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

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }
}