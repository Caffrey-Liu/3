package ESP32data.Server;

import java.awt.image.BufferedImage;

public class Bridge {
    BufferedImage image;
    public int count = 0;
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        count++;
    }
}
