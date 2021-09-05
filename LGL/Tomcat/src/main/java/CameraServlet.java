import ESP32data.Server.MyServer;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/CameraServlet")
public class CameraServlet extends HttpServlet {

    MyServer Server = new MyServer();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        //resp.setContentType("image/jpeg");
        //ServletOutputStream out = resp.getOutputStream();
        //GetImage();
        //System.out.println(Server.bridge.count);
        String a = Server.PictureBase64Code;
        PrintWriter out = resp.getWriter();
        //System.out.println(a);
        out.println(a);
       /*JSONObject obj = new JSONObject();
        obj.put("String",a);
        out.println(obj);*/
        //ImageIO.write(image, "jpeg", out);
        out.flush();
        out.close();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("开始接收图像");
        Server.startListen(10001);
    }
}
