package ESP32data.Server;
import javax.swing.*;
import java.awt.*;

public class Draw extends JFrame {
    Image image;
    public Draw(){
        this.setSize(512,384);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("MLX90640图像");
        setVisible(true);
    }
    public void PutImage(Image image){
        this.image = image;
        add(new CanvasPanel());
        pack();
        setSize(512,384);
        //setVisible(false);
        //setVisible(true);
    }
    class CanvasPanel extends Canvas{
        public void paint(Graphics g){
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(image,0,0,this);
        }
    }
}
