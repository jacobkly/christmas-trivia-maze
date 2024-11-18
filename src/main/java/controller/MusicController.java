package controller;

import model.MusicFactory;

import javax.sound.sampled.*;

public class MusicController {

    private final MusicFactory myMusicFactory;

    private FloatControl myVolumeControl;

    public MusicController(final MusicFactory theMusicFactory) { myMusicFactory = theMusicFactory; }

    public void startPlaying() {
        try {
            Clip musicClip = myMusicFactory.loadCurrentSong();
            musicClip.start();

            if (musicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                myVolumeControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(0.85f);
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
    public void setVolume(float theNewVolume) {
        if (myVolumeControl != null && theNewVolume >= 0.0f && theNewVolume <= 1.0f) {
            // volumes less than 0.75 are essentially muted
            if (theNewVolume < 0.75f) {
                myVolumeControl.setValue(0.0f);
            } else {
                // convert the normalized volume [0.0f, 1.0f] to decibel range
                float min = myVolumeControl.getMinimum();
                float max = myVolumeControl.getMaximum();
                float valueInDecibels = min + (max - min) * theNewVolume;

                myVolumeControl.setValue(valueInDecibels);
            }
        }
    }

    public float getVolume() {
        if (myVolumeControl != null) { // maps volume from decibel value to [0.0f, 1.0f]
            float min = myVolumeControl.getMinimum();
            float max = myVolumeControl.getMaximum();
            float current = myVolumeControl.getValue();
            return (current - min) / (max - min);
        }
        return 0.875f; // a mid audio level due to logarithmic scale
    }
}
