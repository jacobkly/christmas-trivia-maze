package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * The PreparationPanel class is a custom JPanel that provides the user interface for preparing to
 * start a game. It allows the user to input their name, choose a difficulty level, and either
 * proceed with the game or return to the main menu.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public class PreparationPanel extends JPanel {

    /** Array containing dialogue messages displayed to the user during the preparation phase. */
    private final static String[] DIALOGUE = {
            "So I see you wish to see Santa Claus!",
            "What is your name?",
            "Choose your journey difficulty below...",
            "Hahaha... Are you sure about this?"
    };

    /** Array containing difficulty level names that are presented as options to the user. */
    private final static String[] DIFFICULTY_NAMES = new String[] {
            " Frosty (Easy)", " Blizzard (Medium)", " Naughty List (Hard)"
    };

    /** The GameListener used to communicate events back to the game controller. */
    private final GameListener myGameListener;

    /** Inner panel that holds all the components of the preparation screen. */
    private final JPanel myInnerPanel;

    /** A ButtonGroup to manage the difficulty level radio buttons. */
    private ButtonGroup myDifficultyGroup;

    /** The "Yes" button used for confirming readiness to start the game. */
    private JButton myYesButton;

    /** The "No" button used for opting to go back or not proceed with the game. */
    private JButton myNoButton;

    /**
     * Constructs a PreparationPanel with the specified GameListener.
     *
     * @param theGameListener The GameListener to handle game start and main menu transitions.
     */
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

    /**
     * Sets up the various components within the inner panel using GridBagLayout.
     *
     * @param theConstraints The GridBagConstraints used for positioning components.
     */
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

        processUserDecision(nameField);
    }

    /**
     * Formats and returns an array of JLabel components containing the dialogue text.
     *
     * @return An array of JLabels with the formatted dialogue text.
     */
    private JLabel[] formatLoreLabels() {
        JLabel[] labels = new JLabel[DIALOGUE.length];

        for (int i = 0; i < DIALOGUE.length; i++) {
            labels[i] = new JLabel(DIALOGUE[i]);
            labels[i].setBackground(Color.BLACK);
            labels[i].setForeground(Color.WHITE);
            labels[i].setFont(Fonts.getPixelFont(12));
        }
        return labels;
    }

    /**
     * Creates and formats the name input field.
     *
     * @return The formatted JTextField for username input.
     */
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

    /**
     * Adds the name prompt label and name input field to the inner panel.
     *
     * @param theLabel The JLabel containing the prompt text.
     * @param theNameField The JTextField where the user will input their name.
     * @param theConstraints The GridBagConstraints used for positioning components.
     */
    private void addNamePrompt(final JLabel theLabel,
                               final JTextField theNameField,
                               final GridBagConstraints theConstraints) {
        JPanel namePromptPanel = new JPanel();
        namePromptPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        namePromptPanel.setBackground(Color.BLACK);

        namePromptPanel.add(theLabel, theConstraints);
        namePromptPanel.add(theNameField, theConstraints);

        myInnerPanel.add(namePromptPanel, theConstraints);
        theConstraints.gridy++;
    }

    /**
     * Adds difficulty selection buttons to the inner panel.
     *
     * @param theConstraints The GridBagConstraints used for positioning components.
     */
    private void addDifficultyButtons(final GridBagConstraints theConstraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK);

        myDifficultyGroup = new ButtonGroup();
        for (int i = 0; i < DIFFICULTY_NAMES.length; i++) {
            JRadioButton difficultyButton = new JRadioButton(DIFFICULTY_NAMES[i]);
            difficultyButton.setForeground(Color.WHITE);
            difficultyButton.setFont(Fonts.getPixelFont(10));
            difficultyButton.setOpaque(false);
            difficultyButton.setBackground(Color.DARK_GRAY);
            difficultyButton.setFocusable(false);
            myDifficultyGroup.add(difficultyButton);
            if (i == 0) {
                difficultyButton.setSelected(true);
            }
            buttonPanel.add(difficultyButton);
            if (i < DIFFICULTY_NAMES.length - 1) { // gaps in between the buttons
                buttonPanel.add(Box.createVerticalStrut(15));
            }
        }
        // center the buttons
        theConstraints.anchor = GridBagConstraints.CENTER;
        myInnerPanel.add(buttonPanel, theConstraints);
        theConstraints.gridy++;
        theConstraints.anchor = GridBagConstraints.NORTHWEST;
    }

    /**
     * Adds the "Yes" and "No" buttons to the inner panel.
     *
     * @param theConstraints The GridBagConstraints used for positioning components.
     */
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

    /**
     * Processes the user's decision based on the "Yes" or "No" button clicked.
     *
     * @param theNameField The JTextField containing the user's name input.
     */
    private void processUserDecision(final JTextField theNameField) {
        myYesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = theNameField.getText().trim();
                if (playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            PreparationPanel.this,
                            "Please enter a name to continue.",
                            "Missing Name", JOptionPane.WARNING_MESSAGE);
                } else {
                    int playerMaxHealth = getChosenDifficulty();
                    int playerMaxHints = 20;  // TODO to be changed based on difficulty
                    myGameListener.startGame(5, 7, playerName, playerMaxHealth, playerMaxHints );
                }
            }
        });

        myNoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        PreparationPanel.this,
                        "That's okay! Take your time.\nShall you be taken back to Main Menu?",
                        "Fear is Normal",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    myGameListener.startMainMenu();
                } else {
                    myGameListener.startPreparation();
                }
            }
        });
    }

    /**
     * Returns the difficulty level based on the selected radio button.
     *
     * @return The chosen difficulty level (8 for easy, 6 for medium, 4 for hard).
     */
    private int getChosenDifficulty() {
        int difficulty = 8;
        int buttonCount = 0;

        // iterate over each button
        for (Enumeration<AbstractButton> buttons = myDifficultyGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected() && button.getText().equals(DIFFICULTY_NAMES[buttonCount])) {
                difficulty = switch (buttonCount) {
                    case 0 -> 8;
                    case 1 -> 6;
                    case 2 -> 4;
                    default -> difficulty;
                };
            }
            buttonCount++;
        }
        return difficulty;
    }
}
