import ESP32data.Server.AcceptServer;
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

    AcceptServer Server = new AcceptServer();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        String a = Server.getServer().PictureBase64Code;
        PrintWriter out = resp.getWriter();
        out.println(a);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String a = Float.toString(Server.getServer().MaxTem);
        PrintWriter out = resp.getWriter();
        out.println(a);
        out.flush();
        out.close();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("开始接收图像");
        Server.port = 10001;
        Server.start();
    }
}
