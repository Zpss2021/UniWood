package info.zpss.test.ProtoMsgTest;

public class ProtoMsg {
    public final Command cmd;
    public final String[] args;

    public ProtoMsg(Command cmd, String[] args) {
        this.cmd = cmd;
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

    public static void main(String[] args) {
        ProtoMsg msg = ProtoMsg.build(Command.parse("h"), "username", "password");
        System.out.println(msg);
        System.out.println(ProtoMsg.parse(msg.toString()));
    }
}
