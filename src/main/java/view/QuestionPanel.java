package view;

import controller.GameListener;
import model.BooleanQuestion;
import model.MultipleChoiceQuestion;
import model.Question;
import model.TextInputQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The QuestionPanel class represents a panel that displays a question and its corresponding
 * answer options based on the type of question (e.g., multiple choice, boolean, text input).
 *
 * @author Mathew Miller
 * @author Jacob Klymenko (Javadoc and refactor)
 * @version 1.0
 */
public final class QuestionPanel extends JPanel {

    /** The button to confirm an answer. */
    private static JButton myConfirmButton;

    /** The listener for game-related events. */
    private final GameListener myGameListener;

    /** The text area to display the question prompt. */
    private final JTextArea myQuestionPrompt;

    /** The text area displaying the instructions. */
    private final JTextArea myInstructions;

    /** The panel containing the answer options. */
    private JPanel myAnswerPanel;

    /**
     * Constructs a QuestionPanel to display a question and its associated answer options.
     *
     * @param theGameListener the listener for game-related events
     */
    public QuestionPanel(final GameListener theGameListener) {
        myGameListener = theGameListener;
        myQuestionPrompt = new JTextArea();
        myInstructions = createInstructionText();

        setupQuestionPrompt();

        setSize(400, 400);
        setLayout(new BorderLayout());
        setBorder(new RoundedBorder(40));
        setBackground(Color.BLACK);
        setVisible(true);

        myQuestionPrompt.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(myQuestionPrompt, BorderLayout.NORTH);
    }

    /**
     * Configures the properties of the question prompt text area. This includes setting it as
     * non-editable, enabling line wrap, and applying a border, background color, font, and text color.
     */
    public void setupQuestionPrompt() {
        myQuestionPrompt.setEditable(false);
        myQuestionPrompt.setLineWrap(true);
        myQuestionPrompt.setWrapStyleWord(true);
        myQuestionPrompt.setBorder(new RoundedBorder(20, new Insets(10, 10, 10, 10)));
        myQuestionPrompt.setBackground(Color.BLACK);
        myQuestionPrompt.setFont(Fonts.getPixelFont(16));
        myQuestionPrompt.setForeground(Color.WHITE);
    }

    /**
     * The setQuestion method creates a new answer panel depending on the question attached to
     * the currently selected room. This method also displays the game instructions and a prompt
     * asking the user to select a room when there is no question currently displayed.
     *
     * @param theQuestion represents the question assigned to the currently selected room.
     */
    public void setQuestion(final Question theQuestion) {
        if (myInstructions != null) {
            remove(myInstructions);
        }
        if (myAnswerPanel != null) {
            remove(myAnswerPanel);
        }
        if (theQuestion == null) {
            myQuestionPrompt.setText("Please select a room");
            if (myInstructions != null) {
                add(myInstructions, BorderLayout.AFTER_LAST_LINE);
            }
        } else {
            myQuestionPrompt.setText(theQuestion.getPrompt());
            switch (theQuestion) {
                case MultipleChoiceQuestion m -> myAnswerPanel = new MultipleChoiceQuestionPanel(myGameListener, m);
                case BooleanQuestion ignored -> myAnswerPanel = new BooleanQuestionPanel(myGameListener);
                case TextInputQuestion ignored -> myAnswerPanel = new TextInputQuestionPanel(myGameListener);
                default -> throw new IllegalStateException("Unexpected question type");
            }
            myAnswerPanel.setPreferredSize(new Dimension(300, 300));
            this.add(myAnswerPanel);
        }
        repaint();
    }

    /**
     * Creates and formats the instructions text to be displayed on the question panel.
     *
     * @return returns the Instructions text as a JTextArea
     */
    private JTextArea createInstructionText() {
        JTextArea instruction = new JTextArea();
        instruction.setEditable(false);
        instruction.setLineWrap(true);
        instruction.setWrapStyleWord(true);
        instruction.setBorder(new RoundedBorder(20, new Insets(10, 10, 10, 10)));
        instruction.setBackground(Color.BLACK);
        instruction.setFont(Fonts.getPixelFont(14));
        instruction.setForeground(Color.WHITE);

        instruction.setText("""
                How to Play!
                
                Click on a square containing a lock
                
                A question will appear at the top of this square.
                
                If you answer correctly you can continue forward.
                
                If you answer incorrectly you can try again at the cost of one health.
                
                If you are completely stumped, use a gift.
                
                A gift will answer the question correctly for you, these are limited \
                so use them sparingly.
                
                Answer questions and move forward to find Santa!""");

        return instruction;
    }

    /**
     * Creates a JButton for the user to interact with for the purposes of gameplay.
     *
     * @param theGameListener game related event handling
     * @param theBg the button group for the true/false and multiple choice questions
     * @return returns a JButton to confirm answer choice and check correctness
     */
    private static JButton createConfirmButton(
            final GameListener theGameListener,
            final ButtonGroup theBg
    ) {
        setConfirmButton();
        myConfirmButton.addActionListener(e -> {
            var elements = theBg.getElements();
            elements.asIterator().forEachRemaining(element -> {
                JRadioButton button = (JRadioButton) element;
                if (button.isSelected()) {
                    theGameListener.checkAnswer(button.getText());

                }
            });
        });
        return myConfirmButton;
    }

    /**
     * An alternative method for creating a confirm button for use with a text input type
     * question as there are no associated button groups or labels for this type of question.
     *
     * @param theGameListener the listener for game-related events
     * @param theInput A JTextField that represents a users response to a question.
     * @return returns a confirm button for use with text input type questions.
     */
    private static JButton createInputConfirmButton(final GameListener theGameListener,
                                                    final JTextField theInput) {
        setConfirmButton();
        myConfirmButton.addActionListener(e -> theGameListener.checkAnswer(theInput.getText()));
        return myConfirmButton;
    }

    /**
     *  Sets the visual properties of a confirm button,
     */
    private static void setConfirmButton() {
        myConfirmButton = new JButton("Confirm");
        myConfirmButton.setBackground(Color.BLACK);
        myConfirmButton.setForeground(Color.WHITE);
        myConfirmButton.setFont(Fonts.getPixelFont(12));
        myConfirmButton.setBorder(new RoundedBorder(20, new Insets(10, 0, 10, 0)));
        myConfirmButton.setPreferredSize(new Dimension(150, 100));
    }

    /* Inner Classes *//////////////////////////////////////////////////////////////////////////////

    /**
     * Represents a panel for displaying multiple choice type questions. This panel displays the
     * possible answer choices and allows the user to select one option. It sets up a user
     * interface with radio buttons for each answer choice.
     *
     * @author Mathew Miller
     * @version 1.0
     */
    private static class MultipleChoiceQuestionPanel extends JPanel {

        /**
         * Constructs a MultipleChoiceQuestionPanel to display the given multiple choice question.
         * It sets up the UI with radio buttons for each possible answer, and a confirm button to
         * submit the answer.
         *
         * @param theGameListener The game listener that handles the user's response to the question.
         * @param theQuestion The multiple choice question to be displayed.
         */
        public MultipleChoiceQuestionPanel(final GameListener theGameListener,
                                           final MultipleChoiceQuestion theQuestion) {
            setBackground(Color.BLACK);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            List<String> answers = theQuestion.getPossibleAnswers();
            Collections.shuffle(answers);

            ButtonGroup bg = new ButtonGroup();
            add(Box.createVerticalGlue());

            Box horizontalWrapper = Box.createHorizontalBox();
            Box wrapper = Box.createVerticalBox();
            for (String answer : answers) {
                JRadioButton radioButton = new JRadioButton(answer);
                setAnswerPanel(bg, wrapper, radioButton);
            }

            horizontalWrapper.add(wrapper);
            horizontalWrapper.add(Box.createHorizontalGlue());
            add(horizontalWrapper);

            add(Box.createVerticalGlue());

            JButton confirmButton = createConfirmButton(theGameListener, bg);
            wrapper = Box.createHorizontalBox();
            wrapper.add(Box.createHorizontalGlue());
            wrapper.add(confirmButton);
            wrapper.add(Box.createHorizontalGlue());
            add(wrapper);

            add(Box.createVerticalGlue());
        }

        /**
         * Sets the visual properties of the answer panel.
         *
         * @param theBg the button group for radio buttons
         * @param theWrapper the container for the answer options
         * @param theRadioButton the radio button representing an answer choice
         */
        private static void setAnswerPanel(final ButtonGroup theBg,
                                           final Box theWrapper,
                                           final JRadioButton theRadioButton) {
            theRadioButton.setBackground(Color.BLACK);
            theRadioButton.setForeground(Color.WHITE);
            theRadioButton.setFont(Fonts.getPixelFont(14));
            theRadioButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 30, 5));
            theBg.add(theRadioButton);
            theWrapper.add(theRadioButton);
        }
    }

    /**
     * Represents a panel to contain the answer options of a boolean type question. This panel
     * displays two options, "True" and "False", allowing the user to select one. It sets up a user
     * interface with radio buttons for the boolean answers.
     *
     * @author Mathew Miller
     * @version 1.0
     */
    private static class BooleanQuestionPanel extends JPanel {

        /**
         * Constructs a BooleanQuestionPanel to display the given boolean question.
         * It sets up the UI with two radio buttons, one for "True" and one for "False", and a
         * confirm button.
         *
         * @param theGameListener The game listener that handles the user's response to the question.
         */
        public BooleanQuestionPanel(final GameListener theGameListener) {

            setBackground(Color.BLACK);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            ButtonGroup bg = new ButtonGroup();
            add(Box.createVerticalGlue());

            Box horizontalWrapper = Box.createHorizontalBox();
            Box wrapper = Box.createVerticalBox();

            List<JRadioButton> buttons = new ArrayList<>();

            JRadioButton t = new JRadioButton("True");
            buttons.add(t);
            JRadioButton f = new JRadioButton("False");
            buttons.add(f);

            for (JRadioButton button : buttons) {
                MultipleChoiceQuestionPanel.setAnswerPanel(bg, wrapper, button);
            }

            horizontalWrapper.add(wrapper);
            horizontalWrapper.add(Box.createHorizontalGlue());
            add(horizontalWrapper);

            add(Box.createVerticalGlue());

            JButton confirmButton = createConfirmButton(theGameListener, bg);
            wrapper = Box.createHorizontalBox();
            wrapper.add(Box.createHorizontalGlue());
            wrapper.add(confirmButton);
            wrapper.add(Box.createHorizontalGlue());
            add(wrapper);

            add(Box.createVerticalGlue());
        }
    }

    /**
     * Represents a panel for containing the input field for a text input type question.
     * This panel allows the user to enter their answer as text in a text field.
     */
    private static class TextInputQuestionPanel extends JPanel {

        /**
         * Constructs a TextInputQuestionPanel to display the given text input question.
         * It sets up the UI with a text field for the user to type their answer, and a confirm button.
         *
         * @param theGameListener The game listener that handles the user's response to the question.
         */
        public TextInputQuestionPanel(final GameListener theGameListener) {
            setBackground(Color.BLACK);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            Box.createVerticalBox();
            Box wrapper;
            Box.createHorizontalBox();

            add(Box.createVerticalGlue());

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new FlowLayout());
            textPanel.setBackground(Color.BLACK);
            add(textPanel);

            JTextField input = new JTextField("Enter your answer here.");
            input.setBackground(Color.BLACK);
            input.setForeground(Color.WHITE);
            input.setFont(Fonts.getPixelFont(14));
            input.setBorder(new RoundedBorder(20, new Insets(5, 5, 5, 5)));
            input.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (input.getText().equals("Enter your answer here.")) {
                        input.setText("");
                    }
                    input.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    input.setCaretColor(Color.white);
                }
            });
            textPanel.add(input);

            JButton confirmButton = createInputConfirmButton(theGameListener, input);

            wrapper = Box.createHorizontalBox();
            wrapper.add(Box.createHorizontalGlue());
            wrapper.add(confirmButton);
            wrapper.add(Box.createHorizontalGlue());
            add(wrapper);

            add(Box.createVerticalGlue());
        }
    }
}
