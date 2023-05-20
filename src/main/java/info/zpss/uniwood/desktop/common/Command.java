package info.zpss.uniwood.desktop.common;

// 命令枚举，用于标识消息类型
public enum Command {
    // TODO: 检查所有命令
    LOGIN("LOGIN"),
    LOGIN_SUCCESS("LOGIN_SUCCESS"),
    LOGIN_FAILED("LOGIN_FAILED"),
    LOGOUT("LOGOUT"),
    REGISTER("REGISTER"),
    POST("POST"),
    REPLY("REPLY"),
    LIST("LIST"),
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
//            throw new IllegalArgumentException("Unknown command: " + command);
        return null;
    }

    @Override
    public String toString() {
        return getCommand();
    }
}