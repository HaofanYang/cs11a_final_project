package Core;
import lib.StdDraw;
import lib.TileEngine.TETile;
import lib.TileEngine.TERenderer;
import java.util.Random;
import java.awt.*;

public class Game {
    private static final int width = 71;
    private static final int height = 41;
    private boolean hasFinished;
    private int round;
    private Random rand;

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
        StdDraw.text( width / 2,6 * height / 14, "New Game (N)");
        StdDraw.text( width / 2, 6 * height / 14 - 4, "Load Game(L)");
        StdDraw.text( width / 2, 6 * height / 14 - 8, "Quit (Q)");
        StdDraw.show();
        char t = '0';
        while(t != 'n' && t != 'N' && t != 'l' && t != 'T' && t != 'q' && t != 'Q') {
            if (StdDraw.hasNextKeyTyped()) {
                t = StdDraw.nextKeyTyped();
            }
            if (t == 'N' || t == 'n') {
                startNewGame();
                break;
            } else if (t == 'L' || t == 'l') {
                loadGame();
            } else if (t == 'Q' || t == 'q') {
                System.exit(0);
            }
        }
    }

    //TODO
    private static void startNewGame() {
        StdDraw.clear(Color.BLACK);
        TETile[][] world = new RandomMaze(width, height).getWorld();
        TERenderer rend = new TERenderer();
        rend.initialize(width, height);
        rend.renderFrame(world);
    }

    //TODO
    private static void loadGame() {
        return;
    }

}
