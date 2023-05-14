package info.zpss.uniwood.desktop.common;

// 协议消息，用于客户端和服务器之间的通信
public class ProtoMsg {

    // 命令枚举，用于标识消息类型
    public enum Command {
        // TODO: 检查所有命令
        LOGIN("LOGIN"),
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

    public final Command cmd;
    public final String[] args;

    public ProtoMsg(Command cmd, String[] args) {
        this.cmd = (cmd == null) ? Command.UNKNOWN : cmd;
        this.args = args;
    }

    public static ProtoMsg build(Command command, String... args) {
        return new ProtoMsg(command, args);
    }

    public static ProtoMsg parse(String msg) {
        String[] parts = msg.split("\\|");
        String cmd = parts[0];
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        return new ProtoMsg(Command.parse(cmd), args);
    }

    @Override
    public String toString() {
        if (args.length == 0)
            return cmd.toString();
        return String.format("%s|%s", cmd, String.join("|", args));
    }
}
