package ESP32data.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptServer extends Thread {
    public int port;
    MyServer currentServer;

    public MyServer getServer() {
        return currentServer;
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();
            MyServer myserver = new MyServer();
            currentServer = myserver;
            myserver.socket = socket;
            myserver.start();
            while (true) {
                Socket socket1 = server.accept();
                myserver.stop();
                socket.close();
                myserver = new MyServer();
                currentServer = myserver;
                myserver.socket = socket1;
                myserver.start();


                socket = server.accept();
                myserver.stop();
                socket1.close();
                myserver = new MyServer();
                currentServer = myserver;
                myserver.socket = socket;
                myserver.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
