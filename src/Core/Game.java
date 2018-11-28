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

    public void play() {
        TERenderer rend = new TERenderer();
        rend.initialize(width, height);
        TETile[][] world = this.MAZE.getTile();
        rend.renderFrame(world);
        playerBlock.draw(position[0], position[1]);
        StdDraw.show();
        while (!finished) {
            finished = position[0] == exit[0] && position[1] == exit[1];
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
                    findPath(new int[width][height]);
                }
            }
        }
    }

    private void findPath(int[][] visited) {
        DFS(position[0], position[1], visited);
    }

    private void DFS(int x, int y, int[][] visited) {
        finished = (x == exit[0] && y == exit[1]);
        // Base Case
        if (finished || visited[x][y] == 1) {
            return;
        }
        visited[x][y] = 1;
        if (!finished && canMoveUp() && visited[x][y + 1] == 0) {
            moveUp(this.trace);
            StdDraw.pause(10);
            DFS(x, y + 1, visited);
            if (finished) {
                return;
            }
        }
        moveBack(x, y);
        if (!finished && canMoveLeft() && visited[x - 1][y] == 0) {
            moveLeft(this.trace);
            StdDraw.pause(10);
            DFS(x - 1, y, visited);
            if (finished) {
                return;
            }
        }
        moveBack(x, y);
        if (!finished && canMoveDown() && visited[x][y - 1] == 0) {
            moveDown(this.trace);
            StdDraw.pause(10);
            DFS(x, y - 1, visited);
            if (finished) {
                return;
            }
        }
        moveBack(x, y);
        if (!finished && canMoveRight() && visited[x + 1][y] == 0) {
            moveRight(this.trace);
            StdDraw.pause(10);
            DFS(x + 1, y, visited);
            if (finished) {
                return;
            }
        }
        moveBack(x, y);
    }

    // A helper function for findPath.
    // 1. mark the current position blank
    // 2. reset the position to x and y
    private void moveBack(int x, int y) {
        if (x == position[0] && y == position[1]) {
            return;
        }
        this.path.draw(position[0], position[1]);
        this.playerBlock.draw(x, y);
        position[0] = x;
        position[1] = y;
        StdDraw.show();
        StdDraw.pause(10);
    }

    private void moveUp(TETile cover) {
        if (!canMoveUp()) {
            return;
        } else {
            cover.draw(position[0], position[1]);
            position[1] += 1;
            playerBlock.draw(position[0], position[1]);
            StdDraw.show();
        }
    }

    private void moveDown(TETile cover) {
        if (!canMoveDown()) {
            System.out.println("blocked");
            return;
        } else {
            cover.draw(position[0], position[1]);
            position[1] -= 1;
            playerBlock.draw(position[0], position[1]);
            StdDraw.show();
        }
    }

    private void moveLeft(TETile cover) {
        if (!canMoveLeft()) {
            System.out.println("blocked");
            return;
        } else {
            cover.draw(position[0], position[1]);
            position[0] -= 1;
            playerBlock.draw(position[0], position[1]);
            StdDraw.show();
        }
    }

    private void moveRight(TETile cover) {
        if (!canMoveRight()) {
            System.out.println("blocked");
            return;
        } else {
            cover.draw(position[0], position[1]);
            position[0] += 1;
            playerBlock.draw(position[0], position[1]);
            StdDraw.show();
        }
    }

    private boolean canMoveUp() {
        int x = position[0];
        int y = position[1];
        if (y == height - 1 || map[x][y + 1] == 0) {
            return false;
        }
        return true;
    }

    private boolean canMoveDown() {
        int x = position[0];
        int y = position[1];
        if (y == 0 || map[x][y - 1] == 0) {
            System.out.println("blocked");
            return false;
        }
        return true;
    }

    private boolean canMoveLeft() {
        int x = position[0];
        int y = position[1];
        if (x == 0 || map[x - 1][y] == 0) {
            System.out.println("blocked");
            return false;
        }
        return true;
    }

    private boolean canMoveRight() {
        int x = position[0];
        int y = position[1];
        if (x == width - 1 || map[x + 1][y] == 0) {
            System.out.println("blocked");
            return false;
        }
        return true;
    }
}
