package Graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Ryan on 5/26/2016.
 */
public class TextureSheet {
    private String path;    // path to tilesheet
    public final int SIZE;  //size of spritesheet
    public int[] pixels;    //rgb array

    public static TextureSheet tiles = new TextureSheet("tilesheet.png", 1024);

    public TextureSheet (String path, int size){
        this.path = path;
        SIZE = size;
        pixels = new int[SIZE*SIZE];
        load();
    }

    private void load(){
        try {
            BufferedImage image = ImageIO.read(TextureSheet.class.getResource(path));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0,0,w,h,pixels,0,w);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
