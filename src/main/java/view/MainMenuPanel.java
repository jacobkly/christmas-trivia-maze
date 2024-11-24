package view;

import controller.GameListener;
import controller.MusicController;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainMenuPanel extends JPanel {

    private final GameListener myGameListener;

    private final MusicController myMusicController;

    private final JPanel myInnerPanel;

    private final JButton[] myButtons = {
            new JButton("New Game"),
            new JButton("Load Game"),
            new JButton("Settings"),
            new JButton("Exit")
    };

    public MainMenuPanel(final GameListener theGameListener, final MusicController theMusicController,
                         final VolumeSliderPanel theVolumeSliderPanel) {
        myGameListener = theGameListener;
        myMusicController = theMusicController;

        setLayout(new BorderLayout());

        myInnerPanel = new JPanel();
        myInnerPanel.setBackground(new Color(0, 0, 0, 0));
        myInnerPanel.setLayout(new GridBagLayout());

        GridBagConstraints innerConstraints = new GridBagConstraints();
        innerConstraints.insets = new Insets(50, 10, 10, 10);
        innerConstraints.anchor = GridBagConstraints.WEST;
        innerConstraints.gridx = 0;
        innerConstraints.gridy = 0;
        setupMainPanel(innerConstraints, theVolumeSliderPanel);

        add(myInnerPanel, BorderLayout.WEST);
    }

    private void setupMainPanel(final GridBagConstraints theConstraints, final VolumeSliderPanel theVolumeSliderPanel) {
        addTitle(theConstraints);
        theConstraints.gridy++;

        formatButtons();
        addButtons(theConstraints, theVolumeSliderPanel);
    }

    private void addTitle(final GridBagConstraints theConstraints) {
        final String[] title = new String[] {"Christmas", "Trivia Maze"};
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.setBackground(new Color(0, 0, 0, 0));

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.insets = new Insets(10, 10, 10, 10);
        titleConstraints.anchor = GridBagConstraints.WEST;

        for (String s : title) {
            JLabel titleLabel = new JLabel(s);
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(Fonts.getPixelFont(55));
            titleLabel.setBackground(new Color(0, 0, 0, 0));
            titlePanel.add(titleLabel, titleConstraints);
            titleConstraints.gridx++;
        }
        myInnerPanel.add(titlePanel, theConstraints);
    }

    private void formatButtons() {
        for (JButton button : myButtons) {
            button.setBackground(new Color(241, 241, 241, 175));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            button.setForeground(Color.BLACK);
            button.setFont(Fonts.getPixelFont(15));
            button.setFocusable(false);
            button.setRolloverEnabled(false);
        }
    }

    private void addButtons(final GridBagConstraints theConstraints, final VolumeSliderPanel theVolumeSliderPanel) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.anchor = GridBagConstraints.WEST;
        buttonConstraints.insets = new Insets(3, 10, 3, 10);
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;

        for (int i = 0; i < myButtons.length; i++) {
            myButtons[i].setPreferredSize(new Dimension(400, 50));
            buttonPanel.add(myButtons[i], buttonConstraints);
            buttonConstraints.gridy++;

            // repaint() is used to bring back transparency to button after a user click
            if (i == 0) {
                myButtons[i].addActionListener(e -> { myGameListener.startPreparation(); repaint(); });
            } else if (i == 1) {
                myButtons[i].addActionListener(e -> { { myGameListener.resumeGame();} repaint(); });
            } else if (i == 2) {
                myButtons[i].addActionListener(e -> {
                    theVolumeSliderPanel.showDialog(this, "Settings");
                    repaint();
                });
            } else if (i == 3) {
                myButtons[i].addActionListener(e -> System.exit(0));
            }
        }
        myInnerPanel.add(buttonPanel, theConstraints);
    }

    @Override
    protected void paintComponent(final Graphics theGraphics) {
        ImageIcon myIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/menuScreens/mainMenu_96x54.png")));
        Image myImage = new ImageIcon(String.valueOf(myIcon)).getImage();
        super.paintComponent(theGraphics);

        if (myImage != null) {
            theGraphics.drawImage(myIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
