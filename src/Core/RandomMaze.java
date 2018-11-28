package Core;

import lib.RandomUtils;
import lib.TileEngine.TETile;
import lib.TileEngine.Tileset;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class RandomMaze {
    private final int height;
    private final int width;
    private TETile[][] graphicMap; // Graphic representation of a map
    int[][] map; // int representation of a map: 0 for wall, 1 for path, 2 for exit and 3 for current position
    Random rand;
    List<int[]> walkables = new ArrayList<>();
    int[] EXIT; // Randomly selected
    int[] POSITION;

    // Given a current level, initiate a random maze
    public RandomMaze(int w, int h, Random rand) {
        this.height = h;
        this.width = w;
        this.rand = rand;

        // This loop is to prevent the situation where the maze is too small
        while (walkables.size() <= height * width / 4) {
            this.graphicMap = new TETile[width][height];
            this.map = new int[width][height];
            this.walkables = new ArrayList<>();

            map[0][0] = 1; // Traversing from node (0,0)
            walkables.add(new int[]{0, 0});
            randomGenerate(0, 0);
        }
        setExit();
        setEntrance();
        render();

    }

    // return the 2D TETile matrix
    public TETile[][] getTile() {
        return graphicMap;
    }

    public int[][] getMap() {
        return map;
    }
    
    public int[] getExit() {
        return EXIT;
    }
    
    public int[] getPOSITION() {
        return POSITION;
    }




    /**  ===================== Private method below =================== */

    // Randomly select a position from this.walkable as the exit of the maze
    private void setExit() {
        int pos = RandomUtils.uniform(this.rand, walkables.size());
        EXIT = walkables.remove(pos);
        map[EXIT[0]][EXIT[1]] = 2;

    }

    // Randomly select a postion from this.walkable as the initial position
    private void setEntrance() {
        // To prevent the situation where the entrance and the exit are too close
        int pos = RandomUtils.uniform(this.rand, walkables.size());
        int[] temp = walkables.get(pos);
        int xDis = Math.abs(temp[0] - EXIT[0]);
        int yDis = Math.abs(temp[1] - EXIT[1]);
        if (xDis < width / 4 || yDis < height / 4) {
            setEntrance();
            return;
        }
        POSITION = temp;
        walkables.remove(pos);
    }

    // Construct the 2D TETile matrix according to this.map
    private void render() {
        for (int i = 0; i < graphicMap.length; i++) {
            for (int k = 0; k < graphicMap[0].length; k++) {
                if (map[i][k] == 0) {
                    graphicMap[i][k] = Tileset.WALL;
                } else if (map[i][k] == 1) {
                    graphicMap[i][k] = Tileset.PATH;
                } else if (map[i][k] == 2) {
                    graphicMap[i][k] = Tileset.EXIT;
                }
            }
        }
    }

    // DFS approach to randomly generate a maze
    // Each direction (up, down, left and right) has 80% probability to be traversed
    private void randomGenerate(int i, int j) {
        if (RandomUtils.uniform(new Random(), 5) != 0 && goUp(i, j)) {
            randomGenerate(i, j + 2);
        }
        if (RandomUtils.uniform(new Random(), 5) != 0 && goDown(i, j)) {
            randomGenerate(i, j - 2);
        }

        if (RandomUtils.uniform(new Random(), 5) != 0 && goLeft(i, j)) {
            randomGenerate(i - 2, j);
        }

        if (RandomUtils.uniform(new Random(), 5) != 0 && goRight(i, j)) {
            randomGenerate(i + 2, j);
        }
    }

    // Helper function of DFS
    private boolean goLeft(int i, int j) {
        if (i > 1 && map[i - 1][j] == 0 && map[i - 2][j] == 0) {
            map[i - 1][j] = 1;
            map[i - 2][j] = 1;
            walkables.add(new int[]{i - 1, j});
            walkables.add(new int[]{i - 2, j});
            return true;
        }
        return false;
    }

    // Helper function of DFS
    private boolean goRight(int i, int j) {
        if (i < width - 2 && map[i + 1][j] == 0 && map[i + 2][j] == 0) {
            map[i + 1][j] = 1;
            map[i + 2][j] = 1;
            walkables.add(new int[]{i + 1, j});
            walkables.add(new int[]{i + 2, j});
            return true;
        }
        return false;
    }

    // Helper function of DFS
    private boolean goDown(int i, int j) {
        if (j > 1 && map[i][j - 1] == 0 && map[i][j - 2] == 0) {
            map[i][j - 1] = 1;
            map[i][j - 2] = 1;
            walkables.add(new int[]{i, j - 1});
            walkables.add(new int[]{i, j - 2});
            return true;
        }
        return false;
    }

    // Helper function of DFS
    private boolean goUp(int i, int j) {
        if (j < height - 2 && map[i][j + 1] == 0 && map[i][j + 2] == 0) {
            map[i][j + 1] = 1;
            map[i][j + 2] = 1;
            walkables.add(new int[]{i, j + 1});
            walkables.add(new int[]{i, j + 2});
            return true;
        }
        return false;
    }

}
