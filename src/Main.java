import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws InterruptedException{
        SudokuGame game = new SudokuGame();
        game.setSize(800, 800);
        TimeUnit.SECONDS.sleep(5);
        //game.solve(game.getGrid(), 0, 0);
        //game.printGrid(game.getGrid());
    }
}
