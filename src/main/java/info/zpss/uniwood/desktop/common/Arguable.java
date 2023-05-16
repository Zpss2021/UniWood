package info.zpss.uniwood.desktop.common;

public interface Arguable {
    static boolean paramInArgs(String[] args, String paraA, String paraB) {
        for (String arg : args)
            if (arg.equals(paraA) || arg.equals(paraB))
                return true;
        return false;
    }

    static String stringInArgs(String[] args, String paraA, String paraB) {
        for (int i = 0; i < args.length; i++)
            if (args[i].equals(paraA) || args[i].equals(paraB))
                if (i + 1 < args.length)
                    return args[i + 1];
        return null;
    }

    void init(String[] args) throws Exception;
}