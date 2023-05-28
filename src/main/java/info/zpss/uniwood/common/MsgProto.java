package info.zpss.uniwood.common;

// 协议消息，用于客户端和服务器之间的通信
public class MsgProto {
    public final Command cmd;
    public final String[] args;

    private MsgProto(Command cmd, String[] args) {
        this.cmd = (cmd == null) ? Command.UNKNOWN : cmd;
        this.args = args;
    }

    public static MsgProto build(Command command, String... args) {
        return new MsgProto(command, args);
    }

    public static MsgProto parse(String msg) {
        if (!msg.contains("|"))
            return new MsgProto(Command.parse(msg), null);
        String[] parts = msg.split("\\|");
        String cmd = parts[0];
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        return new MsgProto(Command.parse(cmd), args);
    }

    @Override
    public String toString() {
        if (args == null || args.length == 0)
            return cmd.toString();
        return String.format("%s|%s", cmd, String.join("|", args));
    }
}
