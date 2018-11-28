package Core;
import lib.StdDraw;
import lib.TileEngine.TETile;
import lib.TileEngine.TERenderer;
import lib.TileEngine.Tileset;

import java.util.Random;
import java.awt.*;

public class Game {
    private static final TETile playerBlock = Tileset.POS; // Tile to represent current position
    private static final TETile trace = Tileset.FLOOR; // Tile to represent the trace of automatic path finder
    private static final TETile path = Tileset.PATH; // Tile to recover the previous position (i.e. blank)
    private int width ;
    private  int height;
    private boolean finished;
    private Random rand;
    private RandomMaze MAZE;
    private int[] position;
    private int[] exit;
    private int[][] map;

    public Game(int w, int h, long seed) {
        this.rand = new Random(seed);
        this.width = w;
        this.height = h;
        this.finished = false;
        this.MAZE = new RandomMaze(width, height, rand);
        this.position = this.MAZE.getPOSITION();
        this.exit = this.MAZE.getExit();
        this.map = this.MAZE.getMap();
    }


    //TODO
    public void play() {
        TERenderer rend = new TERenderer();
        rend.initialize(width, height);
        TETile[][] world = this.MAZE.getTile();
        rend.renderFrame(world);
        while (!finished) {
            playerBlock.draw(position[0], position[1]);
            finished = position[0] == exit[0] && position[1] == exit[1];
            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'w' || key == 'W') {
                    moveUp(this.path);
                } else if (key == 's' || key == 'S') {
                    moveDown(this.path);
                } else if (key == 'a' || key == 'A') {
                    moveLeft(this.path);
                } else if (key == 'd' || key == 'D') {
                    moveRight(this.path);
                } else if (key == 'f' || key == 'F') {
                    findPath();
                }
            }
        }
    }



    private void findPath() {
        return;
    }

    private void moveUp(TETile cover) {
        int x = position[0];
        int y = position[1];
        if (y == height - 1 || map[x][y + 1] == 0) {
            System.out.println("blocked");
            return;
        } else {
            cover.draw(position[0], position[1]);
            position[1] = y + 1;
        }
    }

    private void moveDown(TETile cover) {
        int x = position[0];
        int y = position[1];
        if (y == 0 || map[x][y - 1] == 0) {
            System.out.println("blocked");
            return;
        } else {
            cover.draw(position[0], position[1]);
            position[1] = y - 1;
        }
    }

    private void moveLeft(TETile cover) {
        int x = position[0];
        int y = position[1];
        if (x == 0 || map[x - 1][y] == 0) {
            System.out.println("blocked");
            return;
        } else {
            cover.draw(position[0], position[1]);
            position[0] = x - 1;
        }
    }

    private void moveRight(TETile cover) {
        int x = position[0];
        int y = position[1];
        if (x == width - 1 || map[x + 1][y] == 0) {
            System.out.println("blocked");
            return;
        } else {
            cover.draw(position[0], position[1]);
            position[0] = x + 1;
        }
    }
}
