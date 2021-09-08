import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import ESP32data.DataBase.*;

@WebServlet(urlPatterns = "/DatainsertServlet")
public class DatainsertServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String []data = new String[6];
        data[0] = req.getParameter("address");
        data[1] = req.getParameter("name");
        data[2] = req.getParameter("tel");
        data[3] = req.getParameter("idnum");
        data[4] = req.getParameter("tem");
        data[5] = req.getParameter("date");
        People_list DB = new People_list();
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        if (DB.Insert(data) != 0)
            out.println("数据录入成功");
        else out.println("数据录入失败");
        out.flush();
        out.close();
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//    }

}
