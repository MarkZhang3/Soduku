import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
/**
Sources used:
 - Java API on JFrame, JPanel, GridLayout, ActionListener, JTextField, Line2D
 - StackOverflow used for the following:
        - adding thickness to Line2D: https://stackoverflow.com/questions/11030775/line2d-thickness-too-thick
        - repainting JFrame and JPanel: https://stackoverflow.com/questions/22072796/how-to-repaint-a-jpanel-every-x-seconds
        - resizing JLabels: https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
 - Links that I didn't use, but referenced:
        - swing timer: https://stackoverflow.com/questions/1006611/java-swing-timer
        - making a thread: https://stackoverflow.com/questions/12551514/create-threads-in-java-to-run-in-background
 - Special thanks to Mr.Benum and Adam Chen for helping with errors that occurred, ie. resizing the window to display lines on the JFrame
    as well as displaying "solving" animation to JFrame by using thread and swing timer
 - Sudoku Game, by Mark Zhang and Tom Wang
 - ICS4U, Mr. Benum
 - 2022/06/20
 */
public class SudokuGame extends JPanel implements Runnable{
    // fields
    private static final int SIZE = 9; // max length size
    public Box[][] grid = new Box[SIZE][SIZE]; // grid to store values of all Boxes on the 9x9 grid

    public SudokuGame() {
        super();

        super.setLayout(new GridLayout(9,9)); // make the JFrame a grid layout that is 9x9

        // temporarily fill it with boxes
        for (int i = 0; i < SIZE; i ++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Box(i, j);
            }
        }

        // add 2 random numbers to the grid
        // 2 numbers is a small amount -> it is guaranteed there will be a solution
        for (int i = 0; i < 2;) {
            int row = (int) (Math.random() * 3), col = (int) (Math.random() * 3);
            int randomNum = 1 + (int) (Math.random() * 9);
            if (valid(grid, row, col, randomNum)) {
                grid[row][col] = new Box(randomNum, row, col);
                i++;
            }
        }
        // change the first box to make things more random
        // the way the "solve" algorithm works is trial and error starting from 1 first
        // so if the first box isnt filled, the chances of the row being 1, 2, 3...
        // is very very high
        int randomNum = 1 + (int) (Math.random() * 9);
        while (!valid(grid, 0, 0, randomNum)) {
            randomNum = 1 + (int) (Math.random() * 9);
        }
        grid[0][0] = new Box(randomNum, 0, 0);

        // for checking
        printGrid(grid);
        // generate solution for it
        getSolution(grid, 0, 0);

        // white out majority of the boxes, leave some remaining
        int boxesKeptCount = 20+(int)(Math.random()*25);
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
                    grid[i][j] = new Box(i, j);
                    grid[i][j].addActionListener(new BoxListener());
                }
            }
        }

        // add the boxes to the JPanel
        for (Box[] boxes : grid) {
            for (Box b : boxes) {
                super.add(b);
            }
        }

        //printGrid(grid);
        super.setVisible(true);
    }

    /**
    paints component using super()
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
    @param : none
    @return : 2d box array
    accessor method to return the grid
     */
    public Box[][] getGrid() {
        return grid;
    }

    /**
    separate thread to repaint the JPanel so that the solving animation updates
     @param : none
     @return : none
     @throws : none, but catches InterruptedException
     */
    @Override
    public void run() {
        try{
            while (true) {
                this.repaint();
                Thread.sleep(1);
            }
        } catch(InterruptedException e) {

        }
    }


    /**
     BoxListener is an action listener for each box
     */
    public class BoxListener implements ActionListener {
        /**
        @param : an action event (enter key)
        @return : none
        @throws : if string was entered in the box (I used Integer.parseInt, so strings are not handled)
        gets the box that was clicked on and the current text inside the box
        if statement handles if the user took back their number (no text inside box)
        or otherwise take in number in the box and paint the box based on if its valid
         */
        public void actionPerformed(ActionEvent e) {
            Box sourceBox = (Box)e.getSource(); // gets the box that was clicked on
            int r = sourceBox.row;
            int c = sourceBox.col;
            if (sourceBox.getText().equals("")) {
                grid[r][c].changeNum(0);
                grid[r][c].setBackground(Color.WHITE); // reset
            } else {
                int numEntered = Integer.parseInt(sourceBox.getText()); // gets integer
                paintBox(getGrid(), r, c, numEntered); // change the box accordingly
                grid[r][c].changeNum(numEntered); // update the grid with made changes
            }
        }
    }

    /**
    @param : 2d box array, current row and col (int)
    @return : a true or false based on whether or not a solution has been found
    getSolution will fill the grid such that each row and col of the array will hold a number
    that is a valid sudoku solution and return a true or false on whether a solution has been completed
     */
    public boolean getSolution(Box[][] grid, int row, int col) {
        // base case
        // if all the rows and cols have been filled, then the board is finished
        // * note that I set col to one higher than row, this is because of the next if statement
        if (col >= SIZE && row >= SIZE-1) {
            return true;
        }

        // for comprehensive and typing efficiency
        // it'll be faster if I just automatically call col+1 for everything, then
        // check at the beginning if col > 9, I go to next row, set col to 0
        if (col > SIZE-1) {
            col = 0;
            row ++;
        }

        // alternative case where if the current box has already been filled in
        if (grid[row][col].getNum() != 0) { // recall that in Box.java, 0 denotes an unfilled box
            return getSolution(grid, row, col+1);
        }

        for (int trialNum = 1; trialNum <= 9; trialNum++) {
            if (valid(grid, row, col, trialNum)) {
                // temporarily change the current grid[row][col]
                // and check if a solution can be found
                grid[row][col].changeNum(trialNum);
                if (getSolution(grid, row, col+1)) {
                    return true;
                }
            }
        }
        grid[row][col].changeNum(0); // backtrack
        return false;
    }

    /**
    @param : a 2d array of Box, the current row and col and number that the user wants to check
    @return : a true or false based on whether or not the number entered for the col and row in the grid is valid or not
    checks if the current number has been repeated in the row, column and 3x3 box it's in
     */
    public boolean valid(Box[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) { // checks current column
            if (grid[i][col].getNum() == num) { // if box.num has been repeated
                //System.out.println('a');
                return false;
            }
        }
        for (int i = 0; i < 9; i++) { // check current row
            if (grid[row][i].getNum() == num) {
                //System.out.println('b');
                return false;
            }
        }
        // 3x3 box check
        int startRow = row - row%3; // <- starting row of the 3x3 box the current row is in
        int startCol = col - col%3; // <- starting col of the 3x3 box the current col is in
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (grid[i][j].getNum() == num) {
                    //System.out.println('c');
                    return false;
                }
            }
        }
        return true;
    }

    /**
    @param grid that needs to be printed
    @return none
    prints the 2d array
    this method is mostly for debugging purposes
    */
    public void printGrid(Box[][] grid) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j].getNum()+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
    @param : takes the 2d array storing all the boxes, the row, col that needs to be painted, and the number in the row&col
    @return : none
    paints the boxes that have been filled out with corresponding colours:
    RED for a wrong guess
    GREEN for a guess that works based on the CURRENT state of the board
     */
    public void paintBox(Box[][] grid, int row, int col, int num) {
        //grid = this.grid;
        if (valid(grid, row, col, num)) {
            grid[row][col].setBackground(Color.GREEN);
            //System.out.println('p');
        } else {
            grid[row][col].setBackground(Color.RED);
            //System.out.println('l');
        }
    }
    /**
    @param : 2d array that stores current boxes, the current row and col
    @return : true or false based on whether or not a solution has been found
    throws: none, but catches InterruptedException error
    Same as method getSolution, except this one is for visual
    I added a time sleeper and called paintBox for this method so that the
    user can see the algorithm in action
     */
    public boolean solve(Box[][] grid, int row, int col){
        try {
            if (col >= SIZE && row >= SIZE-1) {
                return true;
            }

            if (col > SIZE-1) {
                col = 0;
                row++;
            }

            if (grid[row][col].getNum() != 0) {
                return solve(grid, row, col + 1);
            }

            for (int trialNum = 1; trialNum <= 9; trialNum++) {
                paintBox(grid, row, col, trialNum);
                Thread.sleep(1);
                if (valid(grid, row, col, trialNum)) {
                    grid[row][col].changeNum(trialNum);
                    if (solve(grid, row, col + 1)) {
                        return true;
                    }
                }
            }
            grid[row][col].changeNum(0);
            paintBox(grid, row, col, 0);
            return false;
        } catch (InterruptedException e) {

        }
        return false;
    }
}
