package view;

import controller.GameListener;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

/**
 * The PreparationPanel class is a custom JPanel that provides the user interface for preparing to
 * start a game. It allows the user to input their name, choose a difficulty level, and either
 * proceed with the game or return to the main menu.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public final class PreparationPanel extends JPanel {

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

        myInnerPanel = createInnerPanel();
        setupPanel();

        GridBagConstraints outerConstraints = new GridBagConstraints();
        outerConstraints.fill = GridBagConstraints.NONE;
        outerConstraints.anchor = GridBagConstraints.CENTER;
        outerConstraints.gridx = 0;
        outerConstraints.gridy = 0;

        add(myInnerPanel, outerConstraints);
    }

    /**
     * Creates and returns the inner panel for holding components.
     *
     * @return A JPanel configured as the inner panel.
     */
    private JPanel createInnerPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBorder(new RoundedBorder(40));
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(600, 400));
        return panel;
    }

    /**
     * Sets up the components within the inner panel.
     */
    private void setupPanel() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;

        addLoreLabels(constraints);
        JTextField nameField = addNameField(constraints);
        addDifficultyButtons(constraints);
        addConfirmationButtons(constraints);
        setupActionListeners(nameField);
    }

    /**
     * Adds the lore labels to the inner panel.
     *
     * @param theConstraints The GridBagConstraints used for positioning.
     */
    private void addLoreLabels(final GridBagConstraints theConstraints) {
        JLabel[] labels = formatLoreLabels();
        for (int i = 0; i < labels.length - 1; i++) {
            myInnerPanel.add(labels[i], theConstraints);
            theConstraints.gridy++;
        }
    }

    /**
     * Adds the name input field to the inner panel.
     *
     * @param theConstraints The GridBagConstraints used for positioning.
     * @return The created JTextField for user input.
     */
    private JTextField addNameField(final GridBagConstraints theConstraints) {
        JLabel namePrompt = new JLabel(DIALOGUE[1]);
        namePrompt.setForeground(Color.WHITE);
        namePrompt.setFont(Fonts.getPixelFont(12));

        JTextField nameField = formatNameField();

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        namePanel.setBackground(Color.BLACK);
        namePanel.add(namePrompt);
        namePanel.add(nameField);

        myInnerPanel.add(namePanel, theConstraints);
        theConstraints.gridy++;

        return nameField;
    }

    /**
     * Adds difficulty selection buttons to the inner panel.
     *
     * @param theConstraints The GridBagConstraints used for positioning.
     */
    private void addDifficultyButtons(final GridBagConstraints theConstraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK);

        myDifficultyGroup = new ButtonGroup();
        for (int i = 0; i < DIFFICULTY_NAMES.length; i++) {
            JRadioButton button = createDifficultyButton(DIFFICULTY_NAMES[i], i == 0);
            myDifficultyGroup.add(button);
            buttonPanel.add(button);
            if (i < DIFFICULTY_NAMES.length - 1) {
                buttonPanel.add(Box.createVerticalStrut(15));
            }
        }

        theConstraints.anchor = GridBagConstraints.CENTER;
        myInnerPanel.add(buttonPanel, theConstraints);
        theConstraints.gridy++;
        theConstraints.anchor = GridBagConstraints.NORTHWEST;
    }

    /**
     * Adds the "Yes" and "No" buttons to the inner panel.
     *
     * @param theConstraints The GridBagConstraints used for positioning.
     */
    private void addConfirmationButtons(final GridBagConstraints theConstraints) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 75, 0));
        buttonPanel.setBackground(Color.BLACK);

        myYesButton = new JButton("Yes, I'm ready!");
        myNoButton = new JButton("No, I'm scared...");

        buttonPanel.add(myYesButton);
        buttonPanel.add(myNoButton);

        myInnerPanel.add(buttonPanel, theConstraints);
        theConstraints.gridy++;
    }

    /**
     * Sets up action listeners for the buttons.
     *
     * @param theNameField The JTextField containing the user's name.
     */
    private void setupActionListeners(final JTextField theNameField) {
        myYesButton.addActionListener(e -> handleYesButton(theNameField));
        myNoButton.addActionListener(e -> handleNoButton());
    }

    /**
     * Handles the action for the "Yes" button.
     *
     * @param theNameField The JTextField containing the user's name.
     */
    private void handleYesButton(final JTextField theNameField) {
        String playerName = theNameField.getText().trim();
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a name to continue.",
                    "Missing Name",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            int[] difficulty = getChosenDifficulty();
            myGameListener.startGame(5, 7, playerName,
                                        difficulty[0], difficulty[1]);
        }
    }

    /**
     * Handles the action for the "No" button.
     */
    private void handleNoButton() {
        int choice = JOptionPane.showConfirmDialog(
                this,
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

    /**
     * Returns the chosen difficulty level.
     *
     * @return An array with max health and hints based on the selected difficulty.
     */
    private int[] getChosenDifficulty() {
        int index = 0;
        for (Enumeration<AbstractButton> buttons = myDifficultyGroup.getElements();
             buttons.hasMoreElements();
             index++) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return switch (index) {
                    case 0 -> new int[]{8, 6};
                    case 1 -> new int[]{6, 4};
                    case 2 -> new int[]{4, 2};
                    default -> new int[]{8, 5};
                };
            }
        }
        return new int[]{8, 5};
    }

    /**
     * Creates and formats a difficulty selection button.
     *
     * @param theText The button's label text.
     * @param theIsSelected Whether the button is selected by default.
     * @return The formatted JRadioButton.
     */
    private JRadioButton createDifficultyButton(final String theText, final boolean theIsSelected) {
        JRadioButton button = new JRadioButton(theText);
        button.setForeground(Color.WHITE);
        button.setFont(Fonts.getPixelFont(10));
        button.setOpaque(false);
        button.setBackground(Color.DARK_GRAY);
        button.setFocusable(false);
        button.setSelected(theIsSelected);
        return button;
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
}
