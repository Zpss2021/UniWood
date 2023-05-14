package info.zpss.test.SocketTest.client;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 60196);
        SocketHandler handler = new SocketHandler(socket);
        handler.start();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                handler.send("Hello, World!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        handler.join();
    }
}
