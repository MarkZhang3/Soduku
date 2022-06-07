import java.awt.*;
import java.awt.event.*;

public class BoxListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        // gets the box that was clicked on
        Box sourceBox = (Box)e.getSource();
        String text = sourceBox.getText();

    }
}
