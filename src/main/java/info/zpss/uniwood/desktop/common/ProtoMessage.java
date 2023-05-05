package info.zpss.uniwood.desktop.common;

// 协议消息，用于客户端和服务器之间的通信
public class ProtoMessage {
    public final Command cmd;
    public final String[] args;

    public ProtoMessage(Command cmd, String[] args) {
        this.cmd = cmd;
        this.args = args;
    }

    public static ProtoMessage build(Command command, String... args) {
        return new ProtoMessage(command, args);
    }

    public static ProtoMessage parse(String msg) {
        String[] parts = msg.split("\\|");
        String cmd = parts[0];
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        return new ProtoMessage(Command.parse(cmd), args);
    }

    @Override
    public String toString() {
        if (args.length == 0)
            return cmd.toString();
        return String.format("%s|%s", cmd, String.join("|", args));
    }
}
