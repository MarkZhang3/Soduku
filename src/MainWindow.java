import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainWindow implements ActionListener {
    public SudokuGame game;
    public MainWindow()  {
// Deceleration of components
        JFrame Frame = new JFrame();
        JPanel gamePanel = new JPanel();
        JPanel actionPanel = new JPanel();
        JLabel Label = new JLabel("/t/t/t/t/t/tWelcome to Game");
        JButton NewGame =  new JButton("New Game");
        JButton Rule =  new JButton("Rule");
        JButton Solve =  new JButton("Solve");
        game = new SudokuGame();


// Frame
        Frame.setTitle("Suduku Game");
        Frame.setSize(700,750);
        Frame.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
        Frame.setLocationRelativeTo(null);
        game.setSize(800, 800);




// Panel
        //ActionPanel

        actionPanel.setLayout(new GridLayout(1,3));
        actionPanel.setBackground(Color.GRAY);

        //GamePanel

        gamePanel.setLayout(new GridLayout(10,5));
        gamePanel.setBackground(Color.DARK_GRAY);


// Button
        NewGame.setFont(new Font("Arial", Font.BOLD, 18)); //NewGame

        Solve.setFont(new Font("Arial", Font.BOLD, 18));   //Solve

        Rule.setFont(new Font("Arial", Font.BOLD, 18));    //Rule

        Rule.addActionListener(this);

        NewGame.addActionListener(this);

        Solve.addActionListener(this);



// Adding components


        actionPanel.add(NewGame);
        actionPanel.add(Rule);
        actionPanel.add(Solve);
        Frame.add(actionPanel,BorderLayout.SOUTH);
        Frame.add(gamePanel, BorderLayout.CENTER);
        Frame.add(game);
        Frame.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton)e.getSource();
//        switch(clickedButton.getText()) {
//            case "Solve":
//
//        }
        if (clickedButton.getText() == "Solve") {
            Timer timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.repaint();
                }
            });
            game.solve(game.getGrid(), 0, 0, timer);
        }

        //Rule rule = new Rule();

    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }



}


