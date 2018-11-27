package Core;
import lib.StdDraw;
import lib.TileEngine.TETile;
import lib.TileEngine.TERenderer;

import java.awt.*;

public class Game {
    private static final int width = 70;
    private static final int height = 40;
    private boolean hasFinished;
    private int round;
    /***
    public static void main(String[] args) {
        TETile[][] world = new RandomMaze(width, height).getWorld();
        TERenderer rend = new TERenderer();
        rend.initialize(width, height);
        rend.renderFrame(world);
    }
    **/
    public static void main(String[] args) {
        showMenu();
    }
    private static void showMenu() {
        StdDraw.setCanvasSize(1024, 512);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setYscale(0, height);
        StdDraw.setXscale(0, width);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 60));
        StdDraw.text( width / 2,4 * height / 5, "Random Maze");
        StdDraw.setFont(new Font("Arial", Font.ITALIC, 30));
        StdDraw.text( width / 2,8 * height / 14, "Start (s)");
        StdDraw.text( width / 2, 8 * height / 14 - 4, "Load (l)");
        StdDraw.text( width / 2, 8 * height / 14 - 8, "Quit (q)");
        StdDraw.show();

    }
}
