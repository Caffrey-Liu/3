import ESP32data.Server.MyServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/Camera")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Camera</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Camera测试<h1>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public void init() throws ServletException {
        //super.init();
        MyServer Server = new MyServer();
        Server.startListen(10001);
    }
}
