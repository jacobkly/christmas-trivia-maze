package view;

import controller.MusicController;

import javax.swing.*;

public class VolumeSliderPanel extends JPanel{

    private final MusicController myMusicController;

    private int mySliderPosition;

    public VolumeSliderPanel(final MusicController theMusicController) {
        myMusicController = theMusicController;
        // get default slider position according to music controllers volume gain range
        mySliderPosition = (int) (((myMusicController.getDefaultVolume() - myMusicController.getMinVolume()) /
                (myMusicController.getMaxVolume() - myMusicController.getMinVolume())) * 100);

        setupPanel();
    }

    private void setupPanel() {
        JSlider volumeSlider = new JSlider(0, 100, mySliderPosition);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        add(new JLabel("Volume:"));
        add(volumeSlider);

        int volumeOption = JOptionPane.showConfirmDialog(
                VolumeSliderPanel.this,
                this,
                "Settings",
                JOptionPane.OK_CANCEL_OPTION);

        if (volumeOption == JOptionPane.OK_OPTION) {
            mySliderPosition = volumeSlider.getValue();
            float sliderVolumeLevel = ((float) mySliderPosition) / 100;
            float minVolume = myMusicController.getMinVolume();
            float maxVolume = myMusicController.getMaxVolume();
            // converts from [0, 100] scale to [minVolume, maxVolume]
            float newVolume = minVolume + ((maxVolume - minVolume) * sliderVolumeLevel);
            myMusicController.setVolume(newVolume);
            System.out.println("New Volume: " + newVolume);
        }
    }
}
