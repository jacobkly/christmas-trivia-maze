package view;

import controller.GameListener;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainMenuPanel extends JPanel {

    private final static String[] LORE = {
            "So I see you wish to see Santa Claus!",
            "What is your name?",
            "Choose your journey difficulty:",
            "Hahaha... Are you sure about this?"
    };

    private final GameListener myGameListener;

    public MainMenuPanel(GameListener myGameListener) {
        this.myGameListener = myGameListener;

        setMinimumSize(new Dimension(500,500));
        setBackground(Color.LIGHT_GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        addButtons(c);
    }

    private void addButtons(GridBagConstraints c) {
        JButton startButton = new JButton("Start");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 100, 0, 50);
        add(startButton, c);
        startButton.addActionListener(e -> {new PreparationPanel(myGameListener);});
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
        ImageIcon myIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/startScreenImage/Gemini_Generated_Image_e85ajqe85ajqe85a.jpg")));
        Image myImage = new ImageIcon(String.valueOf(myIcon)).getImage();
        super.paintComponent(g);

        if (myImage != null) {
            g.drawImage(myIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

}
