package Core;

import java.util.Random;

public class Load implements java.io.Serializable {
    private Game currentGame;
    private Random rand;
    private int width;
    private int height;
    private int round;
    private static final double sizeIncreaseRatio = 1.15;
    private static final int totRound = 7;

    public Load(Random rand, int w, int h) {
        width = w;
        height = h;
        round = 1;
        this.rand = rand;
    }

    public boolean hasFinished() {
        return round > totRound;
    }

















    public void play() {
        currentGame = new Game(this.round, width, height, this.rand);
        currentGame.play();
        round += 1;
        width *= sizeIncreaseRatio;
        height *= sizeIncreaseRatio;
    }
}
