package Core;
import lib.TileEngine.TETile;
import lib.TileEngine.TERenderer;
import lib.TileEngine.Tileset;
public class Game {
    public static void main(String[] args) {
        TETile[][] world = new RandomMaze().getWorld();
        TERenderer rend = new TERenderer();
        rend.initialize(41,51);
        rend.renderFrame(world);
    }
}
