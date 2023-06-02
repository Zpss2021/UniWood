package info.zpss.uniwood.common;

public enum Command {
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
    ZONE_LIST("ZONE_LIST"),
    EDIT_INFO("EDIT_INFO"),
    EDIT_SUCCESS("EDIT_SUCCESS"),
    EDIT_FAILED("EDIT_FAILED"),
    POST_INFO("POST_INFO"),
    ZONE_INFO("ZONE_INFO"),
    FLOR_INFO("FLOR_INFO"),
    ZONE_POST("ZONE_POST"),
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