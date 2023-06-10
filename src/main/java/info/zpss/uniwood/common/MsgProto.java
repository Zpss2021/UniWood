package info.zpss.uniwood.common;

public class MsgProto {
    public final Command cmd;
    public final String[] args;

    private MsgProto(Command cmd, String[] args) {
        this.cmd = (cmd == null) ? Command.UNKNOWN : cmd;
        this.args = (args == null) ? new String[0] : args;
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
        if (args.length == 0)
            return cmd.toString();
        return String.format("%s|%s", cmd, String.join("|", args));
    }

    public static String linebreakToChar(String str) {
        StringBuilder builder = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '\\' && i + 1 < chars.length && chars[i + 1] == 'n') {
                builder.append('\n');
                i++;
            } else
                builder.append(chars[i]);
        }
        return builder.toString();
    }

    public static String charToLinebreak(MsgProto msg) {
        return charToLinebreak(msg.toString());
    }

    private static String charToLinebreak(String str) {
        StringBuilder builder = new StringBuilder();
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (c == '\n') {
                builder.append('\\');
                builder.append('n');
            } else
                builder.append(c);
        }
        return builder.toString();
    }
}
