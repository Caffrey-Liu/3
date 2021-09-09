import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import ESP32data.DataBase.*;

@WebServlet(urlPatterns = "/DatainsertServlet")
public class DatainsertServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] data = new String[6];
        boolean isnull = false;
        data[0] = req.getParameter("address");
        data[1] = req.getParameter("name");
        data[2] = req.getParameter("tel");
        data[3] = req.getParameter("idnum");
        data[4] = req.getParameter("tem");
        data[5] = req.getParameter("date");
        for (String i : data) if (i.equals("")) isnull = true;
        PrintWriter out = resp.getWriter();
        if (!isnull) {
            People_list DB = new People_list();
            resp.setContentType("text/html;charset=utf-8");
            if (DB.Insert(data) != 0) {
                System.out.println(123456);
                out.println("数据录入成功");
            } else out.println("数据录入失败");
        } else out.println("数据录入失败");
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        out.println( "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>" + "<title>" + "查询结果" + "</title>" + "<meta charset=\"UTF-8\" />"+
                "</head>\n"+
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + "查询结果" + "</h1>\n" +
                "<div align=\"left\">");
        try{
        People_list DB = new People_list();

        ResultSet rs = DB.findAll();
        while(rs.next()){
            int id = rs.getInt("id");
            String address = rs.getString("address");
            String name = rs.getString("name");
            String tel = rs.getString("tel");
            String idnum = rs.getString("id_num");
            String tem = rs.getString("tem");
            String date = rs.getString("date");
            out.println("ID:"+id);
            out.println(",地址:"+address);
            out.println(",姓名:"+name);
            out.println(",电话:"+tel);
            out.println(",身份证:"+idnum);
            out.println(",体温"+tem);
            out.println(",时间:"+date);
            out.println("<br />");
            out.println("<p>--------------------------------------------------------------------------------------------</p>");
            out.println();
        }
        out.println("</div>");
            out.println("</body></html>");

    }
        catch (Exception e){
            e.printStackTrace();
        }

        }
}
