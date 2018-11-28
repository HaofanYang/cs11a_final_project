package Core;
import lib.StdDraw;
import lib.TileEngine.TETile;
import lib.TileEngine.TERenderer;
import java.util.Random;
import java.awt.*;

public class Game {
    private static final int width = 71;
    private static final int height = 41;
    private boolean finished;
    private int round;
    private Random rand;
    private int X;
    private int Y;
    private RandomMaze MAZE;

    public Game(RandomMaze maze, int x, int y, int round, Random rand, boolean finished) {
        this.MAZE = maze;
        this.X = x;
        this.Y = y;
        this.round = round;
        this.rand = rand;
        this.finished = finished;
    }

    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {
        StdDraw.setCanvasSize(1024, 512);
        StdDraw.enableDoubleBuffering();
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


    private static long promptSeed() {
        StringBuilder seed = new StringBuilder();
        while (true) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setFont(new Font("Arial", Font.BOLD, 50));
            StdDraw.text(width / 2, 2 * height / 3, "Please provide a seed number");
            StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
            StdDraw.text(width / 2, height / 2 - height / 10, "hit S to submit and D to delete");
            StdDraw.setFont(new Font("Arial", Font.ITALIC, 40));
            StdDraw.text(width / 2, height / 2 - height / 5, seed.toString() + "|");
            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key - '0' >= 0 && key - '0' <= 9) {
                    seed.append(key);
                } else if (seed.length() > 0 && key == 'S' || key == 's' ) {
                    break;
                } else if (seed.length() > 0 && key == 'd' || key == 'D') {
                    seed.deleteCharAt(seed.length() - 1);
                }
            }
        }
        return Long.parseLong(seed.toString());
    }
    //TODO
    private static void startNewGame() {
        long seed = promptSeed();
        /***
        StdDraw.clear(Color.BLACK);
        TETile[][] world = new RandomMaze(width, height).getWorld();
        TERenderer rend = new TERenderer();
        rend.initialize(width, height);
        rend.renderFrame(world);
         **/
    }

    //TODO
    private static void loadGame() {
        return;
    }

}
