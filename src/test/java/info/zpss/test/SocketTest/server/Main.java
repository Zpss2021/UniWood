package info.zpss.test.SocketTest.server;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocketListener listener = new ServerSocketListener(60196, 10);
            listener.start();
            System.out.println("Server started.");
            listener.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}