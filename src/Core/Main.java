package Core;

import lib.StdDraw;

import java.io.*;
import java.util.Random;
import java.awt.*;

public class Main{
    private static int width = 40;
    private static int height = 18;
    public static void main(String[] args) throws Exception{
        showMenu();
    }

    private static void showMenu() throws Exception {
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
        StdDraw.text( width / 2, 6 * height / 14 - 4 * height / 30, "Load Game(L)");
        StdDraw.text( width / 2, 6 * height / 14 - 8 * height / 30, "Quit (Q)");
        StdDraw.show();
        char t = '0';
        while(t != 'n' && t != 'N' && t != 'l' && t != 'T' && t != 'q' && t != 'Q') {
            if (StdDraw.hasNextKeyTyped()) {
                t = StdDraw.nextKeyTyped();
            }
            if (t == 'N' || t == 'n') {
                startNewGame();
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
                } else if (seed.length() > 0 && (key == 'S' || key == 's') ) {
                    break;
                } else if (seed.length() > 0 && (key == 'd' || key == 'D') ) {
                    seed.deleteCharAt(seed.length() - 1);
                }
            }
        }
        return Long.parseLong(seed.toString());
    }






    private static void startNewGame() throws Exception{
        int round = 1;
        long seed = promptSeed();
        Random rand = new Random(seed);

        while(round < 7){
            Game currentGame = new Game(round, rand);
            currentGame.play();
            if(currentGame.terminated()){
                File f = new File("load.txt");
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(currentGame);
                showMenu();
                return;
            }
            round++;
        }

        victory();

    }

    //TODO
    private static void loadGame() throws Exception {
        String source = "load.txt";

        File dir = new File(".");

        source = dir.getCanonicalPath() + File.separator + source;

        File fin = new File(source);

        FileInputStream fis = new FileInputStream(fin);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Game result = (Game)ois.readObject();

        startNewGame(result);

    }

    private static void startNewGame(Game result) throws Exception {
        int round = result.getRound();
        Random rand = result.getRand();
        Game cur = result;
        while(round < 7) {
            cur.play();
            if(cur.terminated()){
                File f = new File("load.txt");
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(cur);
                showMenu();
                return;
            }
            round++;
            cur = new Game(round, rand);
        }

        victory();
    }

    private static void victory() throws Exception {
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setYscale(0, height);
        StdDraw.setXscale(0, width);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 60));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text( width / 2, height / 2, "Victory!!!");
        StdDraw.show();
        StdDraw.pause(1000);
        showMenu();
    }

}
