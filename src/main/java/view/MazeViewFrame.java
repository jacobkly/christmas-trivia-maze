package view;

import controller.GameListener;
import controller.MusicController;
import model.Maze;
import model.Player;
import model.Room;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
public final class MazeViewFrame extends JFrame {

    /** The listener responsible for handling game events. */
    private final GameListener myGameListener;

    /** The music controller used to control the background music. */
    private final MusicController myMusicController;

    /** The menu bar for the application. */
    private final MenuBar myMenuBar;

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
        // must initialize panels before setting menu bar
        myMenuBar = new MenuBar(myGameListener, myVolumeSliderPanel, this);
        setJMenuBar(myMenuBar);
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
        myMenuBar.updateMenuBarUsability(true);
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
        myMenuBar.updateMenuBarUsability(false);
        myMainMenuPanel.setVisible(false);
        myMazeScreenPanel.setVisible(false);
        myResultScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(true);
    }

    /**
     * Sets the main menu screen visible and hides other panels.
     */
    public void setMainMenu() {
        myMenuBar.updateMenuBarUsability(false);
        myMazeScreenPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myResultScreenPanel.setVisible(false);
        myMainMenuPanel.setVisible(true);
    }

    /**
     * Shows the results UI for a win / loss and associated player statistics.
     *
     * @param theResult Indicates if this was a win or a loss.
     * @param thePlayerStatistics Strings describing various statistics about the players game session.
     */
    public void setResult(final boolean theResult, final String[] thePlayerStatistics) {
        myMenuBar.updateMenuBarUsability(false);
        myMainMenuPanel.setVisible(false);
        myPreparationPanel.setVisible(false);
        myMazeScreenPanel.setVisible(false);
        myResultScreenPanel.setResult(theResult, thePlayerStatistics);
        myResultScreenPanel.setVisible(true);

    }

    /**
     * Updates the maze screen with the player's current health and hints.
     *
     * @param thePlayer Current player information for display in the UI.
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

    /**
     * Plays a sound effect corresponding to the outcome of an action.
     *
     * @param theResult true to play the "correct answer" sound effect; false to play the
     *                  "wrong answer" sound effect.
     * @throws UnsupportedAudioFileException if the audio file format is unsupported.
     * @throws IOException if an I/O error occurs during audio file processing.
     * @throws LineUnavailableException if the audio line cannot be opened for playback.
     */
    public void playSoundEffect(final boolean theResult)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream inputStream = Objects.requireNonNull(getClass()
                .getResourceAsStream(theResult ? "/soundEffects/RightAnswer.wav" : "/soundEffects/WrongAnswer.wav"));

        try (BufferedInputStream bufferedStream = new BufferedInputStream(inputStream)) {
            AudioInputStream audio = AudioSystem.getAudioInputStream(bufferedStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(myMusicController.getDefaultVolume());

                float currentVolume = myMusicController.getCurrentVolume();
                // right answer sound is sharper than wrong answer
                volumeControl.setValue(currentVolume + (theResult ? 4 : 9));
            } else {
                System.out.println("Sound Effects: Clip does NOT support MASTER_GAIN");
            }
            clip.start();
        }
    }
}
