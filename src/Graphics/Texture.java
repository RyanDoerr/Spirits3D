package Graphics;

/**
 * Created by Ryan on 5/25/2016.
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Texture {

    private int x, y;
    public int[] pixels;
    private String loc;
    public final int SIZE;
    private TextureSheet sheet;
    private int textureID;


    public static Texture stone = new Texture(64, 0, 0, TextureSheet.tiles, 1);
    public static Texture stone1 = new Texture(64, 0, 0, TextureSheet.tiles, 2);
    public static Texture stone2 = new Texture(64, 0, 0, TextureSheet.tiles, 3);
    public static Texture stone3 = new Texture(64, 0, 0, TextureSheet.tiles, 4);

    ArrayList<Texture> textureSet;
    public Texture(){

    }
    public Texture(int size, int x, int y, TextureSheet sheet, int textureID) {
        SIZE = size;
        pixels = new int[SIZE*SIZE];
        this.x = x * size;
        this.y = y * size;
        //this.textureID = textureID;
        textureSet.add(this);
        load();
    }
    public int getTextureID() {
        return textureID;
    }

    public void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++){
                pixels[x+y*SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
            }
        }
    }

}
