
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class MyServer extends Thread{
    int port;
    public void startListen (int port)
    {
        this.port = port;
        this.start();
    }
    public void run()
    {
        try {
            ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = in.readLine();
            while(!line.equals("bye")) {
                System.out.println(line);
                //socket = server.accept();
               // in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                line = in.readLine();

               /* try {
                    OutputStream os = socket.getOutputStream();
                    os.write((line+"\r\n").getBytes());
                    line = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
