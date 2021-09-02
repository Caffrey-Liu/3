import java.io.File;

public class DeletePicture {
    static String Path1 = "D:\\GITHUB\\333-334\\LGL\\ESP32 data-processing\\src\\jpg\\";
    static String Path2 = "D:\\GITHUB\\333-334\\LGL\\ESP32 data-processing\\out\\production\\ESP32 data-processing\\jpg";

    public static void folderMethod(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) file2.delete();
            System.out.println("删除完毕");
        }
    }
    public static void main(String args[]){
        folderMethod(Path1);
        folderMethod(Path2);
    }
}
