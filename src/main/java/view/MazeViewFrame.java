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

    /** The listener responsible for handling game events. */
    private final GameListener myGameListener;

    /** The music controller used to control the background music. */
    private final MusicController myMusicController;

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
        myGameListener = theGameListener;
        myMusicController = theMusicController;

        initializeFrame();
        initializePanels();
        setJMenuBar(new MenuBar(myGameListener, myVolumeSliderPanel, this));
        addPanelsToFrame();
    }

    /**
     * Initializes the frame settings such as title, size, close operation, and layout.
     */
    private void initializeFrame() {
        setTitle("Christmas Trivia Maze");
        setSize(1214, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);
    }

    /**
     * Initializes the panels for the game, including the main menu, preparation, maze screen, and result screen.
     */
    private void initializePanels() {
        myVolumeSliderPanel = new VolumeSliderPanel(myMusicController);
        myMainMenuPanel = new MainMenuPanel(myGameListener, myVolumeSliderPanel);
        myPreparationPanel = new PreparationPanel(myGameListener);
        myMazeScreenPanel = new MazeScreenPanel(myGameListener);
        myResultScreenPanel = new ResultScreenPanel(myGameListener, myVolumeSliderPanel);
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
     * Sets the player object on the maze screen.
     *
     * @param thePlayer the player object to be displayed on the maze screen.
     */
    public void setPlayer(final Player thePlayer) {
        myMazeScreenPanel.setPlayer(thePlayer);
    }

    /**
     * Enables or disables the hint feature on the maze screen panel.
     *
     * @param theEnabled true to enable hints, false to disable them.
     */
    public void setHintEnabled(final boolean theEnabled){
        myMazeScreenPanel.setHintEnabled(theEnabled);
    }

    // TODO kinda looks like controller code
    /**
     * Plays a sound effect based on the result of an action.
     *
     * @param theResult true to play the "correct answer" sound effect, false to play the
     *                  "wrong answer" sound effect.
     * @throws UnsupportedAudioFileException if the audio file format is unsupported.
     * @throws IOException if an I/O error occurs during sound file reading.
     * @throws LineUnavailableException if the audio line cannot be opened.
     */
    public void playSoundEffect(final boolean theResult)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audio;

        if (theResult) {
             audio = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                     .getResourceAsStream("/soundEffects/RightAnswer.wav")));
        } else {
             audio = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                     .getResourceAsStream("/soundEffects/WrongAnswer.wav")));
        }

        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
    }
}

