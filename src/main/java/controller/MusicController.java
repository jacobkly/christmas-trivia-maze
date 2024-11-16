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

            if (musicClip.isControlSupported(FloatControl.Type.VOLUME)) {
                myVolumeControl = (FloatControl) musicClip.getControl(FloatControl.Type.VOLUME);
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

    public void setVolume(float theNewVolume) {
        if (myVolumeControl != null) {
            theNewVolume = Math.max(0.0f, Math.min(theNewVolume, 1.0f));
            myVolumeControl.setValue(theNewVolume);
        }
    }

    public float getVolume() {
        if (myVolumeControl != null) {
            return myVolumeControl.getValue();
        }
        return 0.5f;
    }
}
