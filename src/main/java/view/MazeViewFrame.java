package view;

import controller.GameListener;
import controller.MusicController;
import model.Maze;
import model.Player;
import model.Room;

import javax.swing.*;
import java.awt.*;

public class MazeViewFrame extends JFrame {

    private final MainMenuPanel myMainMenuPanel;

    private final PreparationPanel myPreparationPanel;

    private final MazeScreenPanel myMazeScreenPanel;

    private final ResultScreenPanel myResultScreenPanel;

    public MazeViewFrame(final GameListener theGameListener, final MusicController theMusicController) {
        setTitle("Christmas Trivia Maze");
        setSize(1214, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);

        myMainMenuPanel = new MainMenuPanel(theGameListener, theMusicController);
        myPreparationPanel = new PreparationPanel(theGameListener);
        myMazeScreenPanel = new MazeScreenPanel(theGameListener);
        myResultScreenPanel = new ResultScreenPanel(theGameListener);

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

        this.add(myResultScreenPanel, BorderLayout.CENTER);
        this.add(myMazeScreenPanel, BorderLayout.CENTER);
        this.add(myPreparationPanel, BorderLayout.CENTER);
        this.add(myMainMenuPanel, BorderLayout.CENTER);
    }

    public void setMaze(Maze theMaze) {
        myMainMenuPanel.setVisible(false);
        myResultScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myMazeScreenPanel.setVisible(true);
        myMazeScreenPanel.setMaze(theMaze);
    }

    public void setPreparation() {
        myMainMenuPanel.setVisible(false);
        myMazeScreenPanel.setVisible(false);
        myResultScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(true);
    }

    public void setMainMenu() {
        myMazeScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myResultScreenPanel.setVisible(false);
        myMainMenuPanel.setVisible(true);
    }

    public void setResultScreen() {
        myMainMenuPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myMazeScreenPanel.setVisible(false);
        myResultScreenPanel.setVisible(true);
    }

    public void updatePlayerResult(final boolean theResult) {
        myResultScreenPanel.updatePanel(theResult);
    }


    public void setPlayer(Player thePlayer) {
        myMazeScreenPanel.setPlayer(thePlayer);

    }

}

