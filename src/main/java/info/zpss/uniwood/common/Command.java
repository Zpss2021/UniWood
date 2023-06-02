package info.zpss.uniwood.common;

public enum Command {
    // TODO: 检查所有命令
    LOGIN("LOGIN"),
    LOGIN_SUCCESS("LOGIN_SUCCESS"),
    LOGIN_FAILED("LOGIN_FAILED"),
    LOGOUT("LOGOUT"),
    HEARTBEAT("HEARTBEAT"),
    REGISTER("REGISTER"),
    REGISTER_SUCCESS("REGISTER_SUCCESS"),
    REGISTER_FAILED("REGISTER_FAILED"),
    USER_INFO("USER_INFO"),
    UNIV_LIST("UNIV_LIST"),
    FOLW_LIST("FOLW_LIST"),
    FANS_LIST("FANS_LIST"),
    POST_LIST("POST_LIST"),
    EDIT_INFO("EDIT_INFO"),
    EDIT_SUCCESS("EDIT_SUCCESS"),
    EDIT_FAILED("EDIT_FAILED"),
    UNKNOWN("UNKNOWN");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Command parse(String command) throws IllegalArgumentException {
        for (Command c : Command.values())
            if (c.command.equals(command))
                return c;
        return null;
    }

    @Override
    public String toString() {
        return getCommand();
    }
}