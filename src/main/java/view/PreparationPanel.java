package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;

public class PreparationPanel extends JPanel { // Extend JPanel

    private final static String[] LORE = {
            "So I see you wish to see Santa Claus!",
            "What is your name?",
            "Choose your journey difficulty below...",
            "Hahaha... Are you sure about this?"
    };

    private final GameListener myGameListener;

    public PreparationPanel(final GameListener theGameListener) {
        myGameListener = theGameListener;

        setBackground(Color.BLACK);
        setBorder(new RoundedBorder(40));
        setLayout(new GridBagLayout());
        setVisible(true);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        setupPanel(constraints);
    }

    private void setupPanel(final GridBagConstraints theConstraints) {
        JLabel[] labels = formatLoreLabels();
        int labelCount = 0;

        add(labels[labelCount++], theConstraints);
        theConstraints.gridy++;

        JTextField nameField = new JTextField(15);
        nameField.setPreferredSize(new Dimension(125, 30));
        nameField.setBackground(Color.DARK_GRAY);
        nameField.setForeground(Color.WHITE);
        nameField.setFont(Fonts.getPixelFont(10));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setCaretColor(Color.WHITE);
        add(labels[labelCount++], theConstraints);
        theConstraints.gridy++;
        add(nameField, theConstraints);
        theConstraints.gridy++;

        add(labels[labelCount++], theConstraints);
        theConstraints.gridy++;

        setupDifficultyButtons(theConstraints);

        add(labels[labelCount++], theConstraints);
        theConstraints.gridy++;

        processUserDecision(nameField);
    }

    private JLabel[] formatLoreLabels() {
        JLabel[] labels = new JLabel[LORE.length];

        for (int i = 0; i < LORE.length; i++) {
            labels[i] = new JLabel(LORE[i]);
            labels[i].setBackground(Color.BLACK);
            labels[i].setForeground(Color.WHITE);
            labels[i].setFont(Fonts.getPixelFont(12));
        }
        return labels;
    }

    private void setupDifficultyButtons(final GridBagConstraints theConstraints) {
        String[] difficulties = {" Frosty (Easy)", " Blizzard (Medium)", " Naughty List (Hard)"};
        ButtonGroup difficultyGroup = new ButtonGroup();
        for (int i = 0; i < difficulties.length; i++) {
            JRadioButton difficultyButton = new JRadioButton(difficulties[i]);
            difficultyButton.setForeground(Color.WHITE);
            difficultyButton.setFont(Fonts.getPixelFont(10));
            difficultyButton.setOpaque(false);
            difficultyButton.setBackground(Color.DARK_GRAY);
            difficultyButton.setFocusable(false);
            difficultyGroup.add(difficultyButton);
            if (i == 0) {
                difficultyButton.setSelected(true);
            }
            add(difficultyButton, theConstraints);
            theConstraints.gridy++;
        }
    }

    private void processUserDecision(final JTextField theNameField) {
        String[] customButtons = {"Yes, I'm ready!", "No, I'm scared..."};
        JOptionPane optionPane = new JOptionPane(this, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION,
                null, customButtons, customButtons[0]);
        JDialog dialog = optionPane.createDialog("Journey Preparation");
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        Object selectedButton = optionPane.getValue();
        if (selectedButton == null) { // to avoid null error when user exits panel by top-right corner
            return;
        } else if (selectedButton.equals(customButtons[0])) {
            String playerName = theNameField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name to continue.",
                        "Missing Name", JOptionPane.WARNING_MESSAGE);
                return;
            }
            myGameListener.startGame(); // player name can be param since player object creation is not view's responsibility
        } else {
            JOptionPane.showMessageDialog(this, "That's okay! Take your time.",
                    "Fear is Normal", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
