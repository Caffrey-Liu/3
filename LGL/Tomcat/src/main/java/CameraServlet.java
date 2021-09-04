import ESP32data.Server.MyServer;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/CameraServlet")
public class CameraServlet extends HttpServlet {
    public BufferedImage image;
    MyServer Server = new MyServer();

    public void GetImage(){
        this.image = Server.bridge.getImage();
    }

    /*{
        try {
            image = ImageIO.read(new FileInputStream("D:\\GITHUB\\333-334\\LGL\\Tomcat\\src\\main\\java\\ESP32data\\example\\Picture.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setContentType("text/html;charset=utf-8");
        resp.setContentType("image/jpeg");
        ServletOutputStream out = resp.getOutputStream();
        GetImage();
        System.out.println(Server.bridge.count);
        //PrintWriter out = resp.getWriter();
        ImageIO.write(image, "jpeg", out);
        out.close();
        //System.out.println("4");

    }

    @Override
    public void init() throws ServletException {
        Server.startListen(10001);
    }
}
