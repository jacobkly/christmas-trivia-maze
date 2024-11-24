package view;

import controller.MusicController;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class VolumeSliderPanel extends JPanel{

    private final MusicController myMusicController;

    private int mySliderPosition;

    private JSlider myVolumeSlider;

    public VolumeSliderPanel(final MusicController theMusicController) {
        myMusicController = theMusicController;
        // get default slider position according to music controllers volume gain range
        mySliderPosition = (int) (((myMusicController.getDefaultVolume() - myMusicController.getMinVolume()) /
                (myMusicController.getMaxVolume() - myMusicController.getMinVolume())) * 100);

        setupPanel();
    }

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
