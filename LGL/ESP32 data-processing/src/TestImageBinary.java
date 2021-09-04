
import org.apache.commons.codec.binary.Base64;

import java.io.*;


public class TestImageBinary {
    public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        return Base64.encodeBase64String(data);// 返回Base64编码过的字节数组字符串
    }
  /*  public static void main(String[] args){
        String data = GetImageStr("D:\\GITHUB\\333-334\\LGL\\ESP32 data-processing\\src\\jpg\\Picture2021_09_03_00_25_33_720.jpg");
        System.out.println(data);
    }*/
} 