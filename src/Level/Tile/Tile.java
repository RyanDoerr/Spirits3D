package Level.Tile;

/**
 * Created by Ryan on 5/26/2016.
 */
import Graphics.Screen;
import Graphics.Texture;
public class Tile {
    public int x, y;
    public Texture texture;

    public static Tile stone = new StoneTile(Texture.stone);
}
