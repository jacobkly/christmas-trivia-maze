package view;

import controller.MusicController;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * A panel containing a volume slider to adjust the music volume. It allows users to visually
 * adjust the volume level and applies the changes to the music controller.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public final class VolumeSliderPanel extends JPanel{

    /** The music controller used to set and get the volume. */
    private final MusicController myMusicController;

    /** The current position of the volume slider (0 to 100). */
    private int mySliderPosition;

    /** The slider component that controls the volume. */
    private JSlider myVolumeSlider;

    /**
     * Constructs a VolumeSliderPanel.
     *
     * @param theMusicController the MusicController used to adjust the volume
     */
    public VolumeSliderPanel(final MusicController theMusicController) {
        myMusicController = theMusicController;
        // get default slider position according to music controllers volume gain range
        mySliderPosition = (int) (((myMusicController.getDefaultVolume() - myMusicController.getMinVolume()) /
                (myMusicController.getMaxVolume() - myMusicController.getMinVolume())) * 100);
        setupPanel();
    }

    /**
     * Sets up the panel with the volume slider and related components.
     */
    private void setupPanel() {
        myVolumeSlider = new JSlider(0, 100, mySliderPosition);
        myVolumeSlider.setMajorTickSpacing(20);
        myVolumeSlider.setMinorTickSpacing(5);
        myVolumeSlider.setPaintTicks(true);
        myVolumeSlider.setPaintLabels(true);

        add(new JLabel("Volume:"));
        add(myVolumeSlider);

        myVolumeSlider.addChangeListener(e -> mySliderPosition = myVolumeSlider.getValue());
    }

    /**
     * Displays a dialog with the volume slider and applies the volume change when confirmed.
     *
     * @param theParent the parent component to display the dialog
     * @param theTitle  the title of the dialog
     */
    public void showDialog(final Component theParent, final String theTitle) {
        int volumeOption = JOptionPane.showConfirmDialog(
                theParent,
                this,
                theTitle,
                JOptionPane.OK_CANCEL_OPTION);

        if (volumeOption == JOptionPane.OK_OPTION) {
            mySliderPosition = myVolumeSlider.getValue();
            float sliderVolumeLevel = ((float) mySliderPosition) / 100;
            float minVolume = myMusicController.getMinVolume();
            float maxVolume = myMusicController.getMaxVolume();
            // converts from [0, 100] scale to [minVolume, maxVolume]
            float newVolume = minVolume + ((maxVolume - minVolume) * sliderVolumeLevel);
            myMusicController.setVolume(newVolume);
            System.out.println("Vol: " + newVolume + ", Pos: " + mySliderPosition);
        }
    }
}
