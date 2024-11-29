package view;

import controller.GameListener;
import controller.MusicController;
import model.Maze;
import model.Player;
import model.Room;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * The MazeViewFrame class represents the main game frame for the "Christmas Trivia Maze" game.
 * It handles the layout, panels, and menu items, such as the main menu, preparation screen,
 * maze screen, result screen, and volume control. This frame also manages interactions like
 * displaying help, about, debug options, and volume settings.
 *
 * @author Mathew Miller
 * @author Jacob Klymenko
 * @version 1.0
 */
public class MazeViewFrame extends JFrame {

    /** The main menu panel displayed when the game starts. */
    private MainMenuPanel myMainMenuPanel;

    /** The preparation panel displayed before the game starts. */
    private PreparationPanel myPreparationPanel;

    /** The maze screen panel where the gameplay happens. */
    private MazeScreenPanel myMazeScreenPanel;

    /** The result screen panel displayed after the game ends. */
    private ResultScreenPanel myResultScreenPanel;

    /** The volume slider panel for controlling game sound. */
    private VolumeSliderPanel myVolumeSliderPanel;

    /**
     * Constructs a MazeViewFrame object, setting up the game frame with necessary panels and menus.
     *
     * @param theGameListener the listener responsible for game-related events.
     * @param theMusicController the controller for managing the game's music settings.
     */
    public MazeViewFrame(final GameListener theGameListener, final MusicController theMusicController) {
        initializeFrame();
        initializePanels(theGameListener, theMusicController);
        initializeMenuBar();
        addPanelsToFrame();
    }

    /**
     * Initializes the frame settings such as title, size, close operation, and layout.
     */
    private void initializeFrame() {
        setTitle("Christmas Trivia Maze");
        int width = 1214;
        int height = 760;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);
    }

    /**
     * Initializes the panels for the game, including the main menu, preparation, maze screen, and result screen.
     *
     * @param theGameListener the listener responsible for game-related events.
     * @param theMusicController the controller for managing the game's music settings.
     */
    private void initializePanels(final GameListener theGameListener,
                                  final MusicController theMusicController) {
        myVolumeSliderPanel = new VolumeSliderPanel(theMusicController);
        myMainMenuPanel = new MainMenuPanel(theGameListener, myVolumeSliderPanel);
        myPreparationPanel = new PreparationPanel(theGameListener);
        myMazeScreenPanel = new MazeScreenPanel(theGameListener);
        myResultScreenPanel = new ResultScreenPanel(theGameListener, myVolumeSliderPanel);
    }

    /**
     * Initializes the menu bar and its items, including help, volume, about, and debug options.
     */
    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Help...");
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);

        addHelpMenuItem(fileMenu);
        addVolumeMenuItem(fileMenu);
        addAboutMenuItem(fileMenu);
        addDebugMenuItem(fileMenu);
    }

    /**
     * Adds the "How to Play" menu item to the file menu.
     *
     * @param theFileMenu the file menu to which the item will be added.
     */
    private void addHelpMenuItem(final JMenu theFileMenu) {
        JMenuItem helpMenuItem = new JMenuItem("How to Play");
        theFileMenu.add(helpMenuItem);
        helpMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "This is where we can put information" +
                            "on how to play the game\nand let the user know how" +
                            "the different features work");
            repaint();
        });
    }

    /**
     * Adds the "Volume" menu item to the file menu.
     *
     * @param theFileMenu the file menu to which the item will be added.
     */
    private void addVolumeMenuItem(final JMenu theFileMenu) {
        JMenuItem volumeMenuItem = new JMenuItem("Volume");
        theFileMenu.add(volumeMenuItem);
        volumeMenuItem.addActionListener(e -> {
            myVolumeSliderPanel.showDialog(this, "Volume");
            repaint();
        });
    }

    /**
     * Adds the "About" menu item to the file menu.
     *
     * @param theFileMenu the file menu to which the item will be added.
     */
    private void addAboutMenuItem(final JMenu theFileMenu) {
        JMenuItem aboutMenuItem = new JMenuItem("About");
        theFileMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Version - developing\nMathew Miller" +
                            "\nJacob Klymenko\nCai Spidel");
            repaint();
        });
    }

    /**
     * Adds the "Debug" menu item to the file menu.
     *
     * @param theFileMenu the file menu to which the item will be added.
     */
    private void addDebugMenuItem(final JMenu theFileMenu) {
        JCheckBoxMenuItem debugMenuItem = new JCheckBoxMenuItem("Debug");
        debugMenuItem.setSelected(false);
        theFileMenu.add(debugMenuItem);
        debugMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "When this is selected debug mode is enabled");
            repaint();
        });
    }

    /**
     * Adds the various panels (result, maze, preparation, and main menu) to the frame.
     */
    private void addPanelsToFrame() {
        this.add(myResultScreenPanel, BorderLayout.CENTER);
        this.add(myMazeScreenPanel, BorderLayout.CENTER);
        this.add(myPreparationPanel, BorderLayout.CENTER);
        this.add(myMainMenuPanel, BorderLayout.CENTER);
    }

    /**
     * Sets the maze screen visible and hides other panels.
     *
     * @param theMaze the maze object to be displayed.
     */
    public void setMaze(final Maze theMaze) {
        myMainMenuPanel.setVisible(false);
        myResultScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myMazeScreenPanel.setVisible(true);
        myMazeScreenPanel.setMaze(theMaze);
    }

    /**
     * Sets the preparation screen visible and hides other panels.
     */
    public void setPreparation() {
        myMainMenuPanel.setVisible(false);
        myMazeScreenPanel.setVisible(false);
        myResultScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(true);
    }

    /**
     * Sets the main menu screen visible and hides other panels.
     */
    public void setMainMenu() {
        myMazeScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myResultScreenPanel.setVisible(false);
        myMainMenuPanel.setVisible(true);
    }

    /**
     * Sets the result screen visible and hides other panels.
     */
    public void setResultScreen() {
        myMainMenuPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myMazeScreenPanel.setVisible(false);
        myResultScreenPanel.setVisible(true);
    }

    /**
     * Updates the player's result on the result screen.
     *
     * @param theResult the result to be displayed (true for victory, false for defeat).
     */
    public void updatePlayerResult(final boolean theResult) {
        myResultScreenPanel.updatePanel(theResult);
    }

    /**
     * Updates the maze screen with the player's current health and hints.
     *
     * @param theHealth the player's current health to display on the maze screen.
     * @param theHints  the player's remaining hints to display on the maze screen.
     */
    public void setPlayer(final int theHealth, final int theHints) {
        myMazeScreenPanel.setPlayer(theHealth, theHints);
    }

    public void setHintEnabled(boolean enabled){
        myMazeScreenPanel.setHintEnabled(enabled);
    }

    public void playSoundEffect(boolean result) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audio;
        if (result){
             audio = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/soundEffects/RightAnswer.wav")));
        }else{
             audio = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/soundEffects/WrongAnswer.wav")));
        }

        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();

    }

}

