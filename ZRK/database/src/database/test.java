package database;

import com.mysql.cj.xdevapi.Result;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class test {
    public static void main(String args[]){
        Admin admin = new Admin();
        String[] te1= {"a","b","c","d","e"};
        admin.Insert(te1);
        admin.Deletebyid(1);
        ResultSet rs = admin.findAll();
        try {
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String gender = rs.getString(3);
                System.out.println("id:"+id+" 姓名："+name+" 性别："+gender);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
