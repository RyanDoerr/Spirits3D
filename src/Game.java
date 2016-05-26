/**
 * Created by Ryan on 5/26/2016.
 */
/**
 * Original Boilerplate captured from http://www.instructables.com/id/Making-a-Basic-3D-Engine-in-Java/step5/The-Final-Code/ by sheeptheelectric on instructables
 * The boilerplate is for a 2.5D raycasting engine written in Java
 * This boilerplate was coded and working on 5/25/2016
 * I plan to add features like a config file, screen size updating, 21:9 support, etc.
 */
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;
import Graphics.Texture;
import Graphics.Screen;
import Camera.Camera;

public class Game extends JFrame implements Runnable {
    //this is java tradition, unique id's and all that
    private static final long serialVersionUID = 1L;


    public ArrayList<Texture> textures;
    public int mapWidth = 15;
    public int mapHeight = 15;
    public Camera camera;
    public Screen screen;

    private int screenWidth = 1920;
    private int screenHeight = 1080;
    private int refreshRate = 75;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;

    //0 represents empty space, other numbers represent a wall and it's texture
    public static int[][] map =
            {
                    {1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2},
                    {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
                    {1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 2},
                    {1, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 2},
                    {1, 0, 3, 0, 0, 0, 3, 0, 2, 2, 2, 0, 2, 2, 2},
                    {1, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 2},
                    {1, 0, 3, 3, 0, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2},
                    {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
                    {1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 0, 4, 4, 4},
                    {1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4},
                    {1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4},
                    {1, 0, 0, 2, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4},
                    {1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                    {1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4}
            };
    public Game(){
        thread = new Thread(this);
        image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        //load properties from config/config.properties

        textures = new ArrayList<Texture>();

        textures.add(Texture.wood);
        textures.add(Texture.brick);
        textures.add(Texture.bluestone);
        textures.add(Texture.stone);

        camera = new Camera( 4.5, 4.5, 1, 0, 0, -0.66);
        //System.out.print
        screen = new Screen(map, mapWidth, mapHeight, textures, screenWidth, screenHeight);

        addKeyListener(camera);



        setSize(screenWidth, screenHeight);
        setResizable(false);
        setTitle("Ryan's Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
        start();
    }

    private synchronized void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        bs.show();
    }
    public void run(){
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60;
        double delta = 0;
        requestFocus();
        while(running){
            long now = System.nanoTime();
            delta += now-lastTime / ns;
            lastTime = now;
            while (delta >= 1){
                screen.update(camera, pixels);
                camera.update(map);
                delta --;
            }
            render();
        }
    }

    public static void main(String [] args) {
        Game game = new Game();
    }

}

