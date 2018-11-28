package Core;
import lib.StdDraw;
import lib.TileEngine.TETile;
import lib.TileEngine.TERenderer;
import java.util.Random;
import java.awt.*;

public class Game {
    private int width ;
    private  int height;
    private boolean finished;
    private Random rand;
    private RandomMaze MAZE;

    public Game(int w, int h, long seed) {
        this.rand = new Random(seed);
        this.width = w;
        this.height = h;
        this.finished = false;
        this.MAZE = new RandomMaze(width, height, rand);
    }


    //TODO
    public void play() {
        StdDraw.clear(Color.GRAY);
        TERenderer rend = new TERenderer();
        rend.initialize(width, height);
        while (!finished) {
            TETile[][] world = this.MAZE.getTile();
            rend.renderFrame(world);
            int[] position = this.MAZE.getPOSITION();
            int[] exit = this.MAZE.getExit();
            finished = position[0] == exit[0] && position[1] == exit[1];
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'w' || key == 'W') {
                    this.MAZE.moveUp();
                } else if (key == 's' || key == 'S') {
                    this.MAZE.moveDown();
                } else if (key == 'a' || key == 'A') {
                    this.MAZE.moveLeft();
                } else if (key == 'd' || key == 'D') {
                    this.MAZE.moveRight();
                }
            }
        }
    }
}
