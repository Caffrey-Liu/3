import ESP32data.DataBase.Admin;
import ESP32data.DataBase.People_list;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/AdminServlet")
public class AdminServlet extends HttpServlet {
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
            Admin DB = new Admin();

            ResultSet rs = DB.findAll();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String account = rs.getString("account");
                String tel = rs.getString("tel");
                String email = rs.getString("email");
                out.println("ID:"+id);
                out.println(",姓名:"+name);
                out.println(",电话:"+tel);
                out.println(",账号:"+account);
                out.println(",电话"+tel);
                out.println(",邮箱"+email);
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
