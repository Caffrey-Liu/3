
package ESP32data.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyServer extends Thread {
    int port;
    public String PictureBase64Code = "";

    public void startListen(int port) {
        this.port = port;
        this.start();
    }

    private String GetDate() {

        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");//设置日期格式
        return df.format(date);
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = in.readLine();
            float[][] TemData = new float[24][32];
            long l1 = 0,l2;
            while (!line.equals("bye")) {
                l2 = System.currentTimeMillis();
                System.out.println((l2 - l1) + " ms");
                l1 = l2;
                // System.out.println(line + " ");
                if (line.length() == 3073) {
                    int start = 1;
                    int end = 5;
                    for (int y = 0; y < 24; y++){
                        for (int x = 0; x < 32; x++) {
                            TemData[y][x] = (float) (Integer.parseInt(line.substring(start,end))/100.0);
                            start += 4;
                            end += 4;
                        }
                    }
                    PixelConversion picture = new PixelConversion();
                    float[][] Complete_TemData;
                    Complete_TemData = TemData;
                    PictureBase64Code = picture.drawing(Complete_TemData);
                }
                line = in.readLine();
            }
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}