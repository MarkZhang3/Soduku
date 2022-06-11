import javax.swing.*;

public class Test extends JFrame {
    public Test() {
        super();
        SudokuGame g = new SudokuGame();
        super.add(g);
        super.setVisible(true);
        //g.solve(g.getGrid(), 0, 0);
    }
    public static void main(String[] args) {
        Test t = new Test();
        t.setBounds(300, 300, 400, 400);
        t.setDefaultCloseOperation(EXIT_ON_CLOSE);
        t.setVisible(true);
    }
}
