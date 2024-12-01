package controller;

import model.MusicFactory;

import javax.sound.sampled.*;

/**
 * A controller class for managing the playback of music, including volume control and handling
 * song transitions.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public class MusicController {

    /** Default music volume (70% of the range). */
    private static final float DEFAULT_VOLUME = 0.70f;

    /** Minimum music volume before muting. */
    private static final float MIN_VOLUME = 0.5f;

    /** Maximum music volume allowed. */
    private static final float MAX_VOLUME = 0.9f;

    /** Manages song loading and playlist operations. */
    private final MusicFactory myMusicFactory;

    /** Controls the audio playback volume if supported. */
    private FloatControl myVolumeControl;

    /**
     * Constructs a MusicController instance using the specified MusicFactory.
     *
     * @param theMusicFactory the factory responsible for loading and managing songs.
     */
    public MusicController(final MusicFactory theMusicFactory) { myMusicFactory = theMusicFactory; }

    /**
     * Starts playing the current song in the playlist. If the clip supports volume control
     * (MASTER_GAIN), sets the default volume. Also attaches a listener to handle transitions when
     * the song ends.
     */
    public void startPlaying() {
        try {
            Clip musicClip = myMusicFactory.loadCurrentSong();
            musicClip.start();

            if (musicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                myVolumeControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(DEFAULT_VOLUME);
            } else {
                System.out.println("Clip does NOT support MASTER_GAIN");
            }

            musicClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    handleSongEnd();
                }
            });
        } catch (final Exception theException) {
            theException.printStackTrace();
        }
    }

    /**
     * Handles the end of the current song by updating to the next song in the playlist and
     * restarting playback.
     */
    private void handleSongEnd() {
        myMusicFactory.updateToNextSong();
        startPlaying();
    }

    /**
     * Sets the volume for the music playback. The volume is scaled from a normalized range
     * [0.0f, 1.0f] to decibels. Parameter values outside this range are ignored.
     *
     * @param theNewVolume the desired volume, in the range [0.0f, 1.0f].
     */
    public void setVolume(final float theNewVolume) {
        float newVolume = theNewVolume;
        if (myVolumeControl != null && newVolume >= 0.0f && newVolume <= 1.0f) {
            // Volumes less than MIN_VOLUME are practically muted
            if (theNewVolume <= MIN_VOLUME) {
                newVolume = 0.0f;
            }

            // Convert the normalized volume [0.0f, 1.0f] to decibel range
            float min = myVolumeControl.getMinimum();
            float max = myVolumeControl.getMaximum();
            float valueInDecibels = min + (max - min) * newVolume;

            myVolumeControl.setValue(valueInDecibels);
        }
    }

    /**
     * Retrieves the current volume level from the volume control.
     *
     * @return the current volume level as a float.
     */
    public float getCurrentVolume() { return myVolumeControl.getValue(); }

    /**
     * Gets the default volume level.
     *
     * @return the default volume, as a normalized value between 0.0 and 1.0.
     */
    public float getDefaultVolume() { return DEFAULT_VOLUME; }

    /**
     * Gets the minimum volume level.
     *
     * @return the minimum volume, as a normalized value between 0.0 and 1.0.
     */
    public float getMinVolume() { return MIN_VOLUME; }

    /**
     * Gets the maximum volume level.
     *
     * @return the maximum volume, as a normalized value between 0.0 and 1.0.
     */
    public float getMaxVolume() { return MAX_VOLUME; }
}
