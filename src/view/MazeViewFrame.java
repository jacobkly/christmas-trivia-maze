package view;


import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class MazeViewFrame extends JFrame {

    private GameListener myGameListener;
    private MainMenuPanel myMainMenuPanel;
    private MazeScreenPanel myMazeScreenPanel;
    private HintPanel myHintPanel;
    private ResultScreenPanel myResultScreenPanel;
    private KeyPanel myKeyPanel;
    private QuestionPanel myQuestionPanel;


    public MazeViewFrame(GameListener myGameListener) {

        this.myGameListener = myGameListener;

        setTitle("Christmas Trivia Maze");
        setSize(1214, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);

        myMainMenuPanel = new MainMenuPanel(myGameListener);
        myHintPanel = new HintPanel(myGameListener);
        myMazeScreenPanel = new MazeScreenPanel(myGameListener);


        JMenuBar myMenuBar = new JMenuBar();
        JMenu myFileMenu = new JMenu("Help...");

        add(myMenuBar, BorderLayout.NORTH);
        setJMenuBar(myMenuBar);
        myMenuBar.add(myFileMenu);
        JMenuItem helpMenuItem = new JMenuItem("How to Play");
        myFileMenu.add(helpMenuItem);
        helpMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "This is where we can put information" +
                        "on how to play the game\nand let the user know how" +
                        "the different features work"));
        JMenuItem aboutMenuItem = new JMenuItem("About");
        myFileMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Version - developing\nMathew Miller" +
                        "\nJacob Klymenko\nCai Spidel"));

        JCheckBoxMenuItem debugMenuItem = new JCheckBoxMenuItem("Debug");
        debugMenuItem.setSelected(false);
        myFileMenu.add(debugMenuItem);
        debugMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "When this is selected debug mode is enabled"));


        this.add(myMazeScreenPanel, BorderLayout.CENTER);
        this.add(myMainMenuPanel, BorderLayout.CENTER);




    }

    public void setMaze(){
      myMainMenuPanel.setVisible(false);
      myMazeScreenPanel.setVisible(true);
    }


}