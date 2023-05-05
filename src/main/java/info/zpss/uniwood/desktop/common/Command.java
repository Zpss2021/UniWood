package info.zpss.uniwood.desktop.common;

// 用于表示协议中的命令，enum类型
public enum Command {
    // TODO: 检查所有命令
    LOGIN("LOGIN"),
    LOGOUT("LOGOUT"),
    REGISTER("REGISTER"),
    POST("POST"),
    REPLY("REPLY"),
    LIST("LIST");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command parse(String command) throws IllegalArgumentException {
        for (Command c : Command.values())
            if (c.command.equals(command))
                return c;
        throw new IllegalArgumentException("Unknown command: " + command);
    }

    @Override
    public String toString() {
        return command;
    }
}
