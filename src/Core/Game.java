package Core;
import lib.TileEngine.TETile;
import lib.TileEngine.TERenderer;
import lib.TileEngine.Tileset;
public class Game {
    public static void main(String[] args) {
        TETile[][] world = new TETile[10][10];
        for (int i = 0; i < world.length; i++) {
            for (int k = 0; k < world[0].length; k++) {
                world[i][k] = Tileset.WATER;
            }
        }
        TERenderer rend = new TERenderer();
        rend.initialize(10,20);
        rend.renderFrame(world);
    }
}
