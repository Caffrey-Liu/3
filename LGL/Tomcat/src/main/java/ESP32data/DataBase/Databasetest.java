package ESP32data.DataBase;

import java.sql.*;
public class Databasetest {
    public static void main(String args[]){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("加载驱动成功");
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动失败");
            e.printStackTrace();
        }
        Connection con;
        String url = "jdbc:mysql://127.0.0.1:3306/test1?useSSL=false";
        String user = "root";
        String password = "z87849848";
        try {
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("连接数据库成功");

            Statement statement = con.createStatement();
            String sql = "select * from user";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                System.out.print(resultSet.getString("id"));
                System.out.print(" ");
                System.out.println(resultSet.getString("name"));
            }

//            sql = "INSERT INTO student VALUES (\"郑睿恺\", \"3019244342\");";
//            int a = statement.executeUpdate(sql);
//            System.out.println(a);
//
//            sql = "select * from student";
//            resultSet = statement.executeQuery(sql);
//            while (resultSet.next()){
//                System.out.print(resultSet.getString("name"));
//                System.out.print(" ");
//                System.out.println(resultSet.getString("ID"));
//            }

        } catch (Exception e) {
            System.out.println("连接数据库失败");
            e.printStackTrace();
        }
    }

}
