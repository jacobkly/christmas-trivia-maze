package controller;

import model.MusicFactory;

import javax.sound.sampled.*;

public class MusicController {

    private static final float DEFAULT_VOLUME = 0.70f;

    private static final float MIN_VOLUME = 0.5f;

    private static final float MAX_VOLUME = 0.9f;

    private final MusicFactory myMusicFactory;

    private FloatControl myVolumeControl;

    public MusicController(final MusicFactory theMusicFactory) { myMusicFactory = theMusicFactory; }

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

    private void handleSongEnd() {
        myMusicFactory.updateToNextSong();
        startPlaying();
    }

    public String getCurrentSongName() {  return myMusicFactory.getCurrentSongName(); }

    // Scale for MASTER_GAIN in Java's FloatControl is logarithmic due to representing gain in decibels.
    public void setVolume(final float theNewVolume) {
        float newVolume = theNewVolume;
        if (myVolumeControl != null && newVolume >= 0.0f && newVolume <= 1.0f) {
            // volumes less than MIN_VOLUME are practically muted
            if (theNewVolume <= MIN_VOLUME) {
                newVolume = 0.0f;
            }

            // convert the normalized volume [0.0f, 1.0f] to decibel range
            float min = myVolumeControl.getMinimum();
            float max = myVolumeControl.getMaximum();
            float valueInDecibels = min + (max - min) * newVolume;

            myVolumeControl.setValue(valueInDecibels);
        }
    }

    public float getVolume() {
        if (myVolumeControl != null) { // maps volume from decibel value (logarithmic) to [0.0f, 1.0f]
            float min = myVolumeControl.getMinimum();
            float max = myVolumeControl.getMaximum();
            float current = myVolumeControl.getValue();
            return (current - min) / (max - min);
        }
        return DEFAULT_VOLUME; // a mid audio level due to logarithmic scale
    }

    public float getDefaultVolume() { return DEFAULT_VOLUME; }

    public float getMinVolume() { return MIN_VOLUME; }

    public float getMaxVolume() { return MAX_VOLUME; }
}
