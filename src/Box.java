import javax.swing.*;
import java.awt.*;

public class Box extends JTextArea {
    // fields
    public int row, col;
    public int num;
    /*
    param: number will be the number displayed on the box
    in this constructor, the box will not be an input field
     */
    public Box(int number) {
        super(number+"");
        num = number;
        super.setEditable(false);
        super.setBackground(Color.WHITE);
        super.setFont(new Font("Serif", Font.BOLD, 60));
        super.setBorder(BorderFactory.createLineBorder(Color.black));
        super.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//        this.row = row;
//        this.col = col;
    }

    /*
    param: none
    in this constructor, the box will be an input field for the user to try numbers
     */
    public Box() {
        super();
        super.setBackground(Color.WHITE);
        num = 0; // temporarily set this as 0
        // since 0 is not 1-9, if num is 0, then i know this box is currently unassigned
//        this.row = row;
//        this.col = col;
    }

    /*
    param: number user wants to input
    return: none
    this method will display the number the user wants to temporarily hold
     */
    public void temporary(int number) {
        this.num = number;
        this.setFont(new Font("SansSerif", Font.BOLD, 60));
    }

    /*
    param: number user wants to input
    return: none
    this method will display the number the user wants to fill in permanently
     */
    public void fill(int number) {

    }

    public void changeNum(int number) {
        this.num = number;
        this.setText(number+"");
        this.setBackground(Color.WHITE);
        this.setFont(new Font("Serif", Font.BOLD, 60));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public int getNum() {
        return this.num;
    }
}
