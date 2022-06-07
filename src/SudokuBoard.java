import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class SudokuBoard extends JFrame {
    private static final int MAX_SIZE = 9;
    private Box[][] grid = new Box[MAX_SIZE][MAX_SIZE];
    public SudokuGame s;
    public SudokuBoard() {
        super();
        s = new SudokuGame();
        super.setLayout(new GridLayout(9,9));
        //grid = new Box[MAX_SIZE][MAX_SIZE];
        for (int i = 0; i < 9; i ++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Box();
            }
        }
        // add a random number to the grid
        for (int i = 0; i < 2;) {
            int row = (int) (Math.random() * 3), col = (int) (Math.random() * 3);
            int randomNum = 1 + (int) (Math.random() * 9);
            if (s.valid(grid, row, col, randomNum)) {
                grid[row][col] = new Box(randomNum);
                i++;
            }
        }
        int randomNum = 1 + (int) (Math.random() * 9);
        while (!s.valid(grid, 0, 0, randomNum)) {
            randomNum = 1 + (int) (Math.random() * 9);
        }
        grid[0][0] = new Box(randomNum);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j].getNum()+"");
            }
            System.out.println();
        }
        System.out.println();
        // generate solution for it
        s.getSolution(grid, 0, 0);

        // white out majority of the boxes, leave some remaining
        int boxesKeptCount = 15+(int)(Math.random()*22);
        int[][] boxesKept = new int[boxesKeptCount][2];
        for (int i = 0; i < boxesKeptCount; i++) {
            int row = (int)(Math.random()*9), col = (int)(Math.random()*9);
            boxesKept[i][0] = row;
            boxesKept[i][1] = col;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean temp = true;
                for (int[] ints : boxesKept) {
                    if (i == ints[0] && j == ints[1]) {
                        temp = false;
                        break;
                    }
                }
                if (temp) {
                    grid[i][j] = new Box();
                    grid[i][j].addActionListener(new BoxListener());
                }
            }
        }

        for (Box[] boxes : grid) {
            for (Box b: boxes)  {
                super.add(b);
            }
        }
        super.setVisible(true);
    }



}
