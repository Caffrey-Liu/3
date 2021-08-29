import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Caffrey-Liu
 */
public class PixelConversion extends Thread{

    /*static float [][] TemData = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,11,15,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,15,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,14,0,0,0,0,0,0,0,0,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,10,10,12,0,0,0,0,0,0,3,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,15,10,10,15,0,0,0,0,0,0,3,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,15,11,10,14,14,0,0,0,0,15,11,10,9,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,10,10,11,0,0,0,0,15,11,10,12,10,11,15,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,15,10,15,0,0,0,0,15,11,10,11,11,11,15,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,15,10,14,0,0,0,14,11,10,0,0,0,11,11,15,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,15,10,4,5,15,5,5,4,10,10,11,4,4,5,15,6,5,11,15,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,15,15,15,6,5,5,4,6,0,6,5,5,5,6,15,15,15,11,15,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,15,7,5,4,5,5,5,5,5,5,5,5,5,6,15,10,15,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,6,10,6,6,6,7,4,4,4,6,6,6,6,9,11,10,9,15,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,13,14,6,13,13,13,14,13,13,14,14,13,5,13,11,15,15,10,15,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,7,8,5,5,5,6,4,5,5,6,5,5,5,8,11,10,10,10,11,15,0,0,0,0},
            {0,0,0,0,0,0,0,0,11,5,14,9,5,5,8,8,9,5,5,9,14,15,1,11,11,13,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,15,8,5,15,15,15,5,8,15,0,0,0,15,15,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,15,12,0,0,0,12,15,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,15,0,0,0,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}

    };*/
    //插值次数
    static int expand = 4;

    private int[][] GetRGB(float[][] Temptable){
        int [][] Temp = new int[Temptable.length][Temptable[0].length];
        for (int i = 0; i < Temptable.length; i++)
            for (int j = 0; j < Temptable[i].length; j++)
            {
                Temp[i][j] = ChangeColor(Temptable[i][j]);
            }
        return Temp;
    }

    private int ChangeColor(float Tem){
        int Temp;
        int red = 0, green = 0, blue = 0;
        if ((Tem>=0) && (Tem<=63)) {
            blue = (int)(Tem / 64 * 255);
        }
        else if ((Tem>=64) && (Tem<=127)) {
            green = (int)((Tem - 64) / 64 * 255);
            blue = (int)((127 - Tem) / 64 * 255);
        }
        else if ((Tem>=128) && (Tem<=191)) {
            red = (int)((Tem - 128) / 64 * 255);
            green = 255;
            blue = 0;
        }
        else if ((Tem>=192) && (Tem<=255)) {
            red = 255;
            green = (int)((255 - Tem) / 64 * 255);
            blue = 0;
        }
        Temp = (red << 16) + (green <<8) + blue;
        return Temp;
    }

    public  void GetPicture(String Path, BufferedImage image, int[][] RGB){
        File file = new File(Path + "Picture" + GetDate() + ".jpg");
        for (int i = 0; i < RGB.length; i++) {
            for (int j = 0; j < RGB[i].length; j++) {
                image.setRGB(j,i,RGB[i][j]);
            }
        }
        try {
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String GetDate() {

        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式
        return df.format(date);
    }


    public  void DealWithData(float[][] TemData)
    {
        for (int i = 0; i < TemData.length; i++) {
            for (int j = 0; j < TemData[0].length; j++) {
                //TemData[i][j] *= 16;
                System.out.print(TemData[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < TemData.length; i++) {
            for (int j = 0; j < TemData[0].length; j++) {
                TemData[i][j] = (TemData[i][j] - 24) * 16;
                System.out.print(TemData[i][j] + " ");
            }
            System.out.println();
        }

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
        //float [][] Data_expand = Conversion(TemData);
        float [][] Data_expand = TemData;

        /*for (int i = 0; i < Data_expand.length; i++){
            for (int j = 0; j< Data_expand[i].length; j++){
                System.out.print(Data_expand[i][j] + " ");
            }
            System.out.println();
        }*/



        int [][] RGB = GetRGB(Data_expand);

        String Path = "D:\\GITHUB\\333-334\\LGL\\ESP32 data-processing\\src\\jpg\\";
        BufferedImage image = new BufferedImage(Data_expand[0].length,Data_expand.length,BufferedImage.TYPE_INT_RGB);

        GetPicture(Path,image,RGB);

    }

    //一维数组插值
    private  float[] insert(float[] array, int judge){
        float[] temp = new float[array.length * 2];

        int Index;
        //头部插值补位
        if (judge == 0)
        {
            temp[0] = array[0];
            temp[1] = array[0];
            Index = 1;
        }
        //尾部插值补位
        else
        {
            temp[array.length * 2 - 1] = array[array.length - 1];
            temp[0] = array[0];
            Index = 0;
        }
        for (int i = 1; i < array.length; i++){
            temp[Index + 1] = (temp[Index] + array[i]) / 2;
            Index++;
            temp[Index + 1] = array[i];
            Index++;
        }
        return temp;
    }

    private  float[][] Conversion(float[][] temData) {
        float[][] Temp_Data_expand = new  float[temData.length * expand * expand][temData[0].length];
        float[][] Data_expand = new float[temData.length * expand * expand][temData[0].length * expand * expand];

        //纵向插值
        for (int x = 0; x < temData[0].length; x++)
        {
            //获取每一列
            float []array = new float[temData.length];
            for (int y = 0; y < temData.length; y++){
                array[y] = temData[y][x];
            }
            //对每一列进行插值
            int judge = 0;
            for (int n = 0; n < expand; n++){
                array = insert(array, judge);
                judge = (judge + 1) % 2;
            }
            for (int y = 0; y < array.length; y++){
                Temp_Data_expand[y][x] = array[y];
            }
        }

        //横向插值
        for (int y = 0; y < Temp_Data_expand.length; y++)
        {
            //获取每一行
            float []array = new float[Temp_Data_expand[y].length];
            for (int x = 0; x < Temp_Data_expand[y].length; x++){
                array[x] = Temp_Data_expand[y][x];
            }
            //对每一行进行插值
            int judge = 0;
            for (int n = 0; n < expand; n++){
                array = insert(array, judge);
                judge = (judge + 1) % 2;
            }
            for (int x = 0; x < array.length; x++){
                Data_expand[y][x] = array[x];
            }
        }

        return Data_expand;
    }

    public void run(float[][] TemData){
        DealWithData(TemData);
    }
}
