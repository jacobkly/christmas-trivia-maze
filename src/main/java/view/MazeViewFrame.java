package view;

import controller.GameListener;
import model.Maze;

import javax.swing.*;
import java.awt.*;

public class MazeViewFrame extends JFrame {

    private GameListener myGameListener;
    private MainMenuPanel myMainMenuPanel;
    private PreparationPanel myPreparationPanel;
    private MazeScreenPanel myMazeScreenPanel;
    private ResultScreenPanel myResultScreenPanel;
    private KeyPanel myKeyPanel;
    private QuestionPanel myQuestionPanel;
    private GamePanel myGamePanel;
    private StatusBarPanel myStatusBarPanel;
    private Maze myMaze;


    public MazeViewFrame(GameListener theGameListener) {
        myGameListener = theGameListener;

        setTitle("Christmas Trivia Maze");
        setSize(1214, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);

        myGamePanel = new GamePanel(myGameListener);
        myMainMenuPanel = new MainMenuPanel(myGameListener);
        myPreparationPanel = new PreparationPanel(myGameListener);
        myStatusBarPanel = new StatusBarPanel(myGameListener, 5, 3);
        myMazeScreenPanel = new MazeScreenPanel(myGameListener);
        myQuestionPanel = new QuestionPanel(myGameListener);


        JMenuBar myMenuBar = new JMenuBar();
        JMenu myFileMenu = new JMenu("Help...");
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
        this.add(myPreparationPanel, BorderLayout.CENTER);
        this.add(myMainMenuPanel, BorderLayout.CENTER);
    }

    public void setPreparation() {
        myMainMenuPanel.setVisible(false);
        myMazeScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(true);
    }

    public void setMaze() {
        myMainMenuPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myMazeScreenPanel.setVisible(true);
    }

    public void setMainMenu() {
        myMazeScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myMainMenuPanel.setVisible(true);
    }
}