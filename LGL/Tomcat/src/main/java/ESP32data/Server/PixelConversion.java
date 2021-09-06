package ESP32data.Server;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Caffrey-Liu
 */
public class PixelConversion extends Thread{
    //插值次数
    static int expand = 4;
    //插值维数
    int times = 3;
    static String ColorType = "GCM_Pseudo2";  //伪彩2
    //static String ColorType = "GCM_Rainbow3";  //彩虹3
    float[][] TempData;
    String Picbase64;

    private int[][] GetRGB(float[][] Temptable) {
        int[][] Temp = new int[Temptable.length][Temptable[0].length];
        for (int i = 0; i < Temptable.length; i++)
            for (int j = 0; j < Temptable[i].length; j++) {
                Temp[i][j] = ChangeColor(Temptable[i][j], ColorType, j, i);
            }
        return Temp;
    }

    //把温度值转换为对应RGB值
    private int ChangeColor(float Tem, String Type, int x, int y) {
        int Temp;
        int red = 0, green = 0, blue = 0;
        if (Type.equals("GCM_Pseudo2")) {
            if ((Tem >= 0) && (Tem <= 64)) {
                blue = (int) (Tem / 64 * 255);
            } else if ((Tem > 64) && (Tem <= 128)) {
                green = (int) ((Tem - 64) / 64 * 255);
                blue = (int) ((127 - Tem) / 64 * 255);
            } else if ((Tem > 128) && (Tem <= 192)) {
                red = (int) ((Tem - 128) / 64 * 255);
                green = 255;
                blue = 0;
            } else if ((Tem > 192) && (Tem <= 255)) {
                red = 255;
                green = (int) ((255 - Tem) / 64 * 255);
                blue = 0;
            }
        } else if (Type.equals("GCM_Rainbow3")) {
            if (Tem == 0) {

            } else if ((Tem > 0) && (Tem <= 51)) {
                green = (int) Tem * 5;
                blue = 255;
            } else if ((Tem > 51) && (Tem <= 102)) {
                green = 255;
                blue = 255 - (int) (Tem - 51) * 5;
            } else if ((Tem > 102) && (Tem <= 153)) {
                red = (int) (Tem - 102) * 5;
                green = 255;
                blue = 0;
            } else if ((Tem > 153) && (Tem <= 204)) {
                red = 255;
                green = (int) (255 - 128 * (Tem - 153) / 51);
                blue = 0;
            } else if ((Tem > 204) && (Tem <= 255)) {
                red = 255;
                green = (int) (127 - 127 * (Tem - 204) / 51);
                blue = 0;
            }
        }
        Temp = (red << 16) + (green << 8) + blue;
        return Temp;
    }
    
    //将RGB数组转换为图
    public void GetPicture(BufferedImage image, int[][] RGB) {

        for (int i = 0; i < RGB.length; i++) {
            for (int j = 0; j < RGB[i].length; j++) {
                image.setRGB(j, i, RGB[i][j]);
            }
        }



        //从文件转Base64编码
        //SavePicture(image);
        //从image对象转Base64编码
        Picbase64 = GetImageBase64Code(image);
    }
    //保存图片到指定路径
    public void SavePicture (BufferedImage image){
        String Path = "D:\\GITHUB\\333-334\\LGL\\Tomcat\\src\\main\\java\\ESP32data\\jpg\\";
        String JPGPath = Path + "Picture" + GetDate() + ".jpg";
        File file = new File(JPGPath);
        try {
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //从文件转Base64编码
        //Picbase64 = GetImageStr(JPGPath);

    }


    // 通过image对象转换为流获得Base64编码
    public String GetImageBase64Code(BufferedImage image) {
        byte[] data = null;

        // 读取图片字节数组
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "JPG", os);
            InputStream in = new ByteArrayInputStream(os.toByteArray());
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(data);
    }

    //通过路径获得Base64编码
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


    private String GetDate() {

        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");//设置日期格式
        return df.format(date);
    }

    //处理原始温度数据
    public void DealWithData(float[][] TemData) {
       /* for (int i = 0; i < TemData.length; i++) {
            for (int j = 0; j < TemData[0].length; j++) {
                System.out.print((int) TemData[i][j] + " ");
            }
            System.out.println();
        }*/
        //System.out.println();
       // System.out.println();\
      /*  System.out.println(GetDate());*/
        //这里是温度数据区间转换
        for (int i = 0; i < TemData.length; i++) {
            for (int j = 0; j < TemData[0].length; j++) {
                /*if (TemData[i][j] <= 15) TemData[i][j] = 0;
                else if (TemData[i][j] > 15 && TemData[i][j] <= 20) TemData[i][j] = (TemData[i][j] * 8) - 112;
                else if (TemData[i][j] > 20 && TemData[i][j] <= 26) TemData[i][j] = (float) ((TemData[i][j] * 5.3) - 34.7);
                else if (TemData[i][j] > 26 && TemData[i][j] <= 32) TemData[i][j] = (float) ((TemData[i][j] * 16/3) -8/3);
                else if (TemData[i][j] > 32 && TemData[i][j] <= 36) TemData[i][j] = (float) ((TemData[i][j] * 6) + 16);
                else if (TemData[i][j] > 36 && TemData[i][j] <= 40) TemData[i][j] = (float) ((TemData[i][j] * 4) + 96);
                else if (TemData[i][j] > 40) TemData[i][j] = 255;*/
                if (TemData[i][j] <= 15) TemData[i][j] = 0;
                else if (TemData[i][j] > 15 && TemData[i][j] <= 20) TemData[i][j] = (float)(TemData[i][j] * 4.8) - 40;
                else if (TemData[i][j] > 20 && TemData[i][j] <= 26) TemData[i][j] = (float) ((TemData[i][j] * 13.33) -210.67);
                else if (TemData[i][j] > 26 && TemData[i][j] <= 32) TemData[i][j] = (float) ((TemData[i][j] * 10.67) -141.33);
                else if (TemData[i][j] > 32 && TemData[i][j] <= 36) TemData[i][j] = (float) ((TemData[i][j] * 8) - 56);
                else if (TemData[i][j] > 36 && TemData[i][j] <= 40) TemData[i][j] = (float) ((TemData[i][j] * 5.75) + 25);
                else if (TemData[i][j] > 40) TemData[i][j] = 254;
                //System.out.print((int)TemData[i][j] + " ");
            }
            //System.out.println();
        }

        //System.out.println("-----------------以上为一组数据--------------------");

        /*
        检验一维像素插值正确性
        float[] array = {1,3,7,8,1,5,6,9,4,5,1,7,4,8,5,4};
        float[] array2 = insert(array, 0);
        for (int i = 0; i < array2.length; i++)
            System.out.print(array2[i] + " ");
        System.out.println();
        float[] array3 = insert(array2, 1);
        for (int i = 0; i < array3.length; i++)
            System.out.print(array3[i] + " ");
        System.out.println();
        */


        //此处进行像素插值算法拓展分辨率
        float[][] Data_expand = Conversion(TemData);
        //float [][] Data_expand = TemData;

        /*for (int i = 0; i < Data_expand.length; i++){
            for (int j = 0; j< Data_expand[i].length; j++){
                System.out.print(Data_expand[i][j] + " ");
            }
            System.out.println();
        }*/


        int[][] RGB = GetRGB(Data_expand);

        BufferedImage image = new BufferedImage(Data_expand[0].length, Data_expand.length, BufferedImage.TYPE_INT_RGB);

        GetPicture(image, RGB);


    }


    //一维数组插值
    private float[] insert(float[] array, int judge) {
        float[] temp = new float[array.length * 2];

        int Index;
        //头部插值补位
        if (judge == 0) {
            temp[0] = array[0];
            temp[1] = array[0];
            Index = 1;
        }
        //尾部插值补位
        else {
            temp[array.length * 2 - 1] = array[array.length - 1];
            temp[0] = array[0];
            Index = 0;
        }
        for (int i = 1; i < array.length; i++) {
            temp[Index + 1] = (temp[Index] + array[i]) / 2;
            Index++;
            temp[Index + 1] = array[i];
            Index++;
        }
        return temp;
    }

    //插值算法
    private float[][] Conversion(float[][] temData) {
        float[][] Temp_Data_expand = new float[temData.length * expand * expand][temData[0].length];
        float[][] Data_expand = new float[temData.length * expand * expand][temData[0].length * expand * expand];

        //纵向插值
        for (int x = 0; x < temData[0].length; x++) {
            //获取每一列
            float[] array = new float[temData.length];
            for (int y = 0; y < temData.length; y++) {
                array[y] = temData[y][x];
            }
            //对每一列进行插值
            int judge = 0;
            for (int n = 0; n < 2; n++) {
                //array = insert(array, judge);
                array = Insertdata.insertdata(array, judge,times);
                judge = (judge + 1) % 2;
            }
            for (int y = 0; y < array.length; y++) {
                Temp_Data_expand[y][x] = array[y];
            }
        }

        //横向插值
        for (int y = 0; y < Temp_Data_expand.length; y++) {
            //获取每一行
            float[] array = new float[Temp_Data_expand[y].length];
            for (int x = 0; x < Temp_Data_expand[y].length; x++) {
                array[x] = Temp_Data_expand[y][x];
            }
            //对每一行进行插值
            int judge = 0;
            for (int n = 0; n < 2; n++) {
                //array = insert(array, judge);
                array = Insertdata.insertdata(array, judge,times);
                judge = (judge + 1) % 2;
            }
            for (int x = 0; x < array.length; x++) {
                Data_expand[y][x] = array[x];
            }
        }

        return Data_expand;
    }

    public String drawing(float[][] TemData) {
        this.TempData = TemData;
        this.start();
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Picbase64;
    }

    public void run(){
        DealWithData(TempData);
    }

}
