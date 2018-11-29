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
    private static final int edgeHeight = 5;
    private static final int edgeWidth = 5;
    private int width;
    private  int height;
    private boolean finished;
    private Random rand;
    private RandomMaze MAZE;
    private int[] position;
    private int[] exit;
    private int[][] map;
    private int round;
    private int moves;

    public Game(int round, int w, int h, Random rand) {
        this.round = round;
        this.moves = 0;
        this.rand = rand;
        this.width = w;
        this.height = h;
        this.finished = false;
        this.MAZE = new RandomMaze(width, height, this.rand);
        this.position = this.MAZE.getPOSITION();
        this.exit = this.MAZE.getExit();
        this.map = this.MAZE.getMap();
    }

    public void play() {
        TERenderer rend = new TERenderer();
        rend.initialize(width + 2 * edgeWidth, height + 2 * edgeHeight, edgeWidth , edgeHeight);
        TETile[][] world = this.MAZE.getTile();
        rend.renderFrame(world);
        playerBlock.draw(position[0] + edgeWidth, position[1] + edgeHeight);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 15));
        StdDraw.text(edgeWidth + width / 12,  height + 1.7 * edgeHeight, "MoveUp: W");
        StdDraw.text(edgeWidth + 4 * width / 12,  height + 1.7 * edgeHeight, "MoveDown: S");
        StdDraw.text(edgeWidth + 7 * width / 12,  height + 1.7 * edgeHeight, "MoveLeft: A");
        StdDraw.text(edgeWidth + 10 * width / 12,  height + 1.7 * edgeHeight, "MoveRight: D");
        StdDraw.text(edgeWidth + 2 * width / 3,  height +  1.2 * edgeHeight, "Cheat: F");
        StdDraw.text(edgeWidth + width / 3,  height + 1.2 * edgeHeight, "Quit and Save: Q");
        StdDraw.setFont(new Font("Arial", Font.BOLD, 25));
        StdDraw.text(3 * width / 10, 0.5 * edgeHeight , "Round: " + round + "/7");
        StdDraw.text(8 * width / 10, 0.5 * edgeHeight , "Moves: 0");
        StdDraw.show();
        while (!finished) {
            finished = position[0] == exit[0] && position[1] == exit[1];
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'w' || key == 'W') {
                    moveUp(this.path);
                    moves++;
                } else if (key == 's' || key == 'S') {
                    moveDown(this.path);
                    moves++;
                } else if (key == 'a' || key == 'A') {
                    moveLeft(this.path);
                    moves++;
                } else if (key == 'd' || key == 'D') {
                    moveRight(this.path);
                    moves++;
                } else if (key == 'f' || key == 'F') {
                    findPath(new int[width][height]);
                }
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.filledRectangle(8 * width / 10, 0.5 * edgeHeight, 4, 1);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(8 * width / 10, 0.5 * edgeHeight , "Moves: " + moves);
                StdDraw.show();
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
            if (finished) {
                StdDraw.pause(1000);
            }
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
        this.path.draw(position[0] + edgeWidth, position[1] + edgeHeight);
        this.playerBlock.draw(x + edgeWidth, y + edgeHeight);
        position[0] = x;
        position[1] = y;
        StdDraw.show();
        StdDraw.pause(10);
    }

    private void moveUp(TETile cover) {
        if (!canMoveUp()) {
            return;
        } else {
            cover.draw(position[0] + edgeWidth, position[1] + edgeHeight);
            position[1] += 1;
            playerBlock.draw(position[0] + edgeWidth, position[1] + edgeHeight);
            StdDraw.show();
        }
    }

    private void moveDown(TETile cover) {
        if (!canMoveDown()) {
            return;
        } else {
            cover.draw(position[0] + edgeWidth, position[1] + edgeHeight);
            position[1] -= 1;
            playerBlock.draw(position[0] + edgeWidth, position[1] + edgeHeight);
            StdDraw.show();
        }
    }

    private void moveLeft(TETile cover) {
        if (!canMoveLeft()) {
            return;
        } else {
            cover.draw(position[0] + edgeWidth, position[1] + edgeHeight);
            position[0] -= 1;
            playerBlock.draw(position[0] + edgeWidth, position[1] + edgeHeight);
            StdDraw.show();
        }
    }

    private void moveRight(TETile cover) {
        if (!canMoveRight()) {
            return;
        } else {
            cover.draw(position[0] + edgeWidth, position[1] + edgeHeight);
            position[0] += 1;
            playerBlock.draw(position[0] + edgeWidth, position[1] + edgeHeight);
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
            return false;
        }
        return true;
    }

    private boolean canMoveLeft() {
        int x = position[0];
        int y = position[1];
        if (x == 0 || map[x - 1][y] == 0) {
            return false;
        }
        return true;
    }

    private boolean canMoveRight() {
        int x = position[0];
        int y = position[1];
        if (x == width - 1 || map[x + 1][y] == 0) {
            return false;
        }
        return true;
    }
}
