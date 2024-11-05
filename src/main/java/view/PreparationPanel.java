package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreparationPanel extends JPanel { // Extend JPanel

    private final static String[] LORE = {
            "So I see you wish to see Santa Claus!",
            "What is your name?",
            "Choose your journey difficulty below...",
            "Hahaha... Are you sure about this?"
    };

    private final GameListener myGameListener;

    private final JPanel myInnerPanel;

    private JButton myYesButton;

    private JButton myNoButton;

    public PreparationPanel(final GameListener theGameListener) {
        myGameListener = theGameListener;

        setSize(1214, 760);
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        myInnerPanel = new JPanel();
        myInnerPanel.setBackground(Color.BLACK);
        myInnerPanel.setBorder(new RoundedBorder(40));
        myInnerPanel.setLayout(new GridBagLayout());
        myInnerPanel.setPreferredSize(new Dimension(600, 400));

        GridBagConstraints innerConstraints = new GridBagConstraints();
        innerConstraints.insets = new Insets(10, 10, 10, 10);
        innerConstraints.gridx = 0;
        innerConstraints.gridy = 0;

        setupPanel(innerConstraints);

        GridBagConstraints outerConstraints = new GridBagConstraints();
        outerConstraints.fill = GridBagConstraints.NONE;
        outerConstraints.anchor = GridBagConstraints.CENTER;
        outerConstraints.gridx = 0;
        outerConstraints.gridy = 0;

        add(myInnerPanel, outerConstraints);
    }

    private void setupPanel(final GridBagConstraints theConstraints) {
        JLabel[] labels = formatLoreLabels();
        int labelCount = 0;

        myInnerPanel.add(labels[labelCount++], theConstraints);
        theConstraints.gridy++;

        JTextField nameField = formatNameField();
        addNamePrompt(labels[labelCount++], nameField, theConstraints);

        myInnerPanel.add(labels[labelCount++], theConstraints);
        theConstraints.gridy++;

        addDifficultyButtons(theConstraints);

        theConstraints.anchor = GridBagConstraints.CENTER;
        myInnerPanel.add(labels[labelCount++], theConstraints);
        theConstraints.anchor = GridBagConstraints.NORTHWEST;
        theConstraints.gridy++;

        addYesNoButtons(theConstraints);

        boolean isUserReady = processUserDecision(nameField);
        if (isUserReady) {
            myGameListener.startGame(); // player name can be param since player object creation is not view's responsibility
        }
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

    private JTextField formatNameField() {
        JTextField nameField = new JTextField(15);

        nameField.setPreferredSize(new Dimension(125, 30));
        nameField.setBackground(Color.DARK_GRAY);
        nameField.setForeground(Color.WHITE);
        nameField.setFont(Fonts.getPixelFont(10));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setCaretColor(Color.WHITE);
        return nameField;
    }

    private void addNamePrompt(final JLabel theLabel, final JTextField theNameField,
                               final GridBagConstraints theConstraints) {
        JPanel namePromptPanel = new JPanel();
        namePromptPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        namePromptPanel.setBackground(Color.BLACK);

        namePromptPanel.add(theLabel, theConstraints);
        namePromptPanel.add(theNameField, theConstraints);

        myInnerPanel.add(namePromptPanel, theConstraints);
        theConstraints.gridy++;
    }

    private void addDifficultyButtons(final GridBagConstraints theConstraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK);

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
            buttonPanel.add(difficultyButton);
            if (i < difficulties.length - 1) { // gaps in between the buttons
                buttonPanel.add(Box.createVerticalStrut(15));
            }
        }
        // center the buttons
        theConstraints.anchor = GridBagConstraints.CENTER;
        myInnerPanel.add(buttonPanel, theConstraints);
        theConstraints.gridy++;
        theConstraints.anchor = GridBagConstraints.NORTHWEST;
    }

    private void addYesNoButtons(final GridBagConstraints theConstraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 75, 0));
        buttonPanel.setBackground(Color.BLACK);

        myYesButton = new JButton("Yes, I'm ready!");
        myNoButton = new JButton("No, I'm scared...");

        buttonPanel.add(myYesButton);
        buttonPanel.add(myNoButton);

        myInnerPanel.add(buttonPanel, theConstraints);
        theConstraints.gridy++;
    }

    private boolean processUserDecision(final JTextField theNameField) {
        final boolean[] result = {false}; // Use an array to hold the result, allowing mutation

        myYesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = theNameField.getText().trim();
                if (playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(PreparationPanel.this,
                            "Please enter a name to continue.",
                            "Missing Name", JOptionPane.WARNING_MESSAGE);
                } else {
                    result[0] = true;
                }
            }
        });

        myNoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PreparationPanel.this,
                        "That's okay! Take your time.",
                        "Fear is Normal", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return result[0]; // Return the result; will always be false at this point
    }

//    private boolean processUserDecision(final JTextField theNameField) {
//        boolean result = false;
//
//        String[] customButtons = {"Yes, I'm ready!", "No, I'm scared..."};
//        JOptionPane optionPane = new JOptionPane(this, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION,
//                null, customButtons, customButtons[0]);
//        JDialog dialog = optionPane.createDialog("Journey Preparation");
//        dialog.setLocationRelativeTo(null);
//        dialog.setVisible(true);
//
//        Object selectedButton = optionPane.getValue();
//        if (selectedButton != null && selectedButton.equals(customButtons[0])) {
//            String playerName = theNameField.getText().trim();
//            if (playerName.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Please enter a name to continue.",
//                        "Missing Name", JOptionPane.WARNING_MESSAGE);
//            } else {
//                result = true;
//            }
//        } else if (selectedButton != null && selectedButton.equals(customButtons[1])) {
//            JOptionPane.showMessageDialog(this, "That's okay! Take your time.",
//                    "Fear is Normal", JOptionPane.INFORMATION_MESSAGE);
//        }
//        return result;
//    }
}
