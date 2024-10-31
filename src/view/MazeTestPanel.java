package view;

import model.Room;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MazeTestPanel extends JFrame {
    public MazeTestPanel() {
        setTitle("MazeTestPanel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        Room room = new Room();

        try {
            // Create an ImageIcon
            ImageIcon icon = new ImageIcon(room.getRoomImage());

            // Create a JLabel and set the icon
            JLabel label = new JLabel(icon);

            // Add the label to the frame
            add(label);
        } catch (Exception ex) {
            System.out.println("Error loading image: " + ex.getMessage());
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MazeTestPanel();
        });
    }
}
