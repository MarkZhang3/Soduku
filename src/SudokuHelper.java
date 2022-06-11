import java.awt.*;
/*
Sudoku Game is a helper class for Sudoku Board
 */
public class SudokuHelper {
    public SudokuHelper(){

    }
    public boolean getSolution(Box[][] grid, int row, int col) {
        // base case
        // if all the rows and cols have been filled, then the board is finished
        // * note that I set col to one higher than row, this is because of the next if statement
        if (col >= 9 && row >= 8) {
            return true;
        }

        // for comprehensive and typing efficiency
        // it'll be faster if I just automatically call col+1 for everything, then
        // check at the beginning if col > 9, I go to next row, set col to 0
        if (col > 8) {
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
                printGrid(grid);
                if (getSolution(grid, row, col+1)) {
                    return true;
                }
            }
        }
        grid[row][col].changeNum(0); // backtrack
        return false;
    }

    /*
    checks if the current number has been repeated in the row, column and 3x3 box it's in
     */
    public boolean valid(Box[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) { // checks current column
            if (grid[i][col].getNum() == num) { // if box.num has been repeated
                System.out.println('a');
                return false;
            }
        }
        for (int i = 0; i < 9; i++) { // check current row
            if (grid[row][i].getNum() == num) {
                System.out.println('b');
                return false;
            }
        }
        // 3x3 box check
        int startRow = row - row%3; // <- starting row of the 3x3 box the current row is in
        int startCol = col - col%3; // <- starting col of the 3x3 box the current col is in
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (grid[i][j].getNum() == num) {
                    System.out.println('c');
                    return false;
                }
            }
        }
        return true;
    }

    // prints the 2d array
    public void printGrid(Box[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j].getNum()+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void paintBox(Box[][] grid, int row, int col, int num)  {
        if (valid(grid, row, col, num)) {
            grid[row][col].setBackground(Color.GREEN);
            System.out.println('p');
        } else {
            grid[row][col].setBackground(Color.RED);
            System.out.println('l');
        }
    }
}
