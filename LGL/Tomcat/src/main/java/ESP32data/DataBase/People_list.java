package ESP32data.DataBase;

import java.sql.*;

public class People_list {
    Connection con;
    String url = "jdbc:mysql://127.0.0.1:3306/333_334?useSSL=false";
    String user = "root";
    String password = "123456";

    public People_list() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("加载驱动成功");
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动失败");
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("连接数据库成功");
        } catch (Exception e) {
            System.out.println("连接数据库失败");
            e.printStackTrace();
        }
    }

    public int Insert(String[] values){
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into people_list(address,name,tel,id_num,tem,date) values(?,?,?,?,?,?)";
            preparedStatement = con.prepareStatement(sql);
            for(int i=0;i<values.length;i++){
                preparedStatement.setString(i+1,values[i]);
            }
            System.out.println(preparedStatement.toString());
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void Deletebyid(int id) {
        PreparedStatement stmt = null;
        try {
            String sql = "delete from people_list where id=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            int num = stmt.executeUpdate();
            System.out.println(num);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet findAll() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = " select * from people_list ";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
