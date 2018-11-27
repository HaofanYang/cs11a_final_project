package Core;

import lib.RandomUtils;
import lib.TileEngine.TETile;
import lib.TileEngine.Tileset;
import java.util.Random;

public class RandomMaze {
    private final int height = 41;
    private final int width = 51;
    private TETile[][] world;
    int[][] map; // int representation of a map: 0 for wall and 1 for path
    Random rand;

    // Given a current level, initiate a random maze
    public RandomMaze() {
        rand = new Random();
        world = new TETile[height][width];
        map = new int[height][width];
        map[20][25] = 1;
        randomGenerate(20, 25);
        render();
    }

    public TETile[][] getWorld() {
        return world;
    }

    private void render() {
        for (int i = 0; i < world.length; i++) {
            for (int k = 0; k < world[0].length; k++) {
                if (map[i][k] == 0) {
                    world[i][k] = Tileset.WALL;
                } else {
                    world[i][k] = Tileset.PATH;
                }
            }
        }
    }

    private void randomGenerate(int i, int j) {
        if (RandomUtils.uniform(new Random(),4) != 0 && goUp(i, j)) {
            randomGenerate(i - 2, j);
        }
        if (RandomUtils.uniform(new Random(),4) != 0 && goDown(i, j)) {
            randomGenerate(i + 2, j);
        }

        if (RandomUtils.uniform(new Random(),4) != 0 && goLeft(i, j)) {
            randomGenerate(i, j - 2);
        }

        if (RandomUtils.uniform(new Random(),4) != 0 && goRight(i, j)) {
            randomGenerate(i, j + 2);
        }


    }

    private boolean goUp(int i, int j) {
        if (i > 1 && map[i - 1][j] == 0 && map[i - 2][j] == 0) {
            map[i - 1][j] = 1;
            map[i - 2][j] = 1;
            return true;
        }
        return false;
    }

    private boolean goDown(int i, int j) {
        if (i < height - 2 && map[i + 1][j] == 0 && map[i + 2][j] == 0) {
            map[i + 1][j] = 1;
            map[i + 2][j] = 1;
            return true;
        }
        return false;
    }

    private boolean goLeft(int i, int j) {
        if (j > 1 && map[i][j - 1] == 0 && map[i][j - 2] == 0) {
            map[i][j - 1] = 1;
            map[i][j - 2] = 1;
            return true;
        }
        return false;
    }

    private boolean goRight(int i, int j) {
        if (j < width - 2 && map[i][j + 1] == 0 && map[i][j + 2] == 0) {
            map[i][j + 1] = 1;
            map[i][j + 2] = 1;
            return true;
        }
        return false;
    }

}
