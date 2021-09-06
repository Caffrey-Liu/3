import ESP32data.Server.MyServer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/CameraServlet")
public class CameraServlet extends HttpServlet {

    MyServer Server = new MyServer();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        String a = Server.PictureBase64Code;
        PrintWriter out = resp.getWriter();
        out.println(a);
        out.flush();
        out.close();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("开始接收图像");
        Server.startListen(10001);
    }
}
