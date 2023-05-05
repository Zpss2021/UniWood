package info.zpss.test.ProtoMsgTest;

public enum Command {
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
