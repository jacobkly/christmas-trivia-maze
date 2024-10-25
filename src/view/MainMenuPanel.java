package view;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private final ImageIcon myIcon;
    private final Image myImage;

    public MainMenuPanel() {

        setMinimumSize(new Dimension(500,500));
        setBackground(Color.LIGHT_GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        myIcon = new ImageIcon("C:\\Users\\mathe\\IdeaProjects\\" +
                "tcss360-group-project\\QuiltedSnowflakes-HQ-01.jpg");
        myImage = new ImageIcon(String.valueOf(myIcon)).getImage();

        JButton startButton = new JButton("Start");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 100, 0, 50);
        add(startButton, c);
        startButton.setVisible(true);


        JButton exitButton = new JButton("Exit");
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 50, 0, 100);
        add(exitButton, c);
        exitButton.setVisible(true);
        exitButton.addActionListener(e -> System.exit(0));


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (myImage != null) {
            g.drawImage(myIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

}
