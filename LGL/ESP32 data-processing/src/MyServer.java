
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
            float[][]TemData = new float[24][32];
            int x = 0;
            int y = 0;
            while(!line.equals("bye")){
                //System.out.println(line + " ");
                if (line.equals("end"))
                {
                    //处理温度数组
                    PixelConversion picture = new PixelConversion();
                    float[][] Complete_TemData;
                    Complete_TemData = TemData;
                    picture.run(Complete_TemData);
                    x = 0;
                    y = 0;
                }
                else if (line.equals("nan"))
                {
                    if (x == 0)
                        TemData[y][x] = 0;
                    else
                        TemData[y][x] = TemData[y][x-1];
                    x++;
                }
                else if (x == 32){
                    y++;
                    x = 0;
                    TemData[y][x] = Float.parseFloat(line);
                    x++;
                }

                else
                {
                    TemData[y][x] = Float.parseFloat(line);
                    x++;
                }
                //OutputStream os = socket.getOutputStream();
                //os.write((line+"\r\n").getBytes());
                //System.out.println("x= " + x + " y = " + y);
                line = in.readLine();
               /* try {

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
