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
 *  The QuestionPanel class represents a panel that displays a question and a different
 *  answer type depending on the type of question being asked.
 */
public final class QuestionPanel extends JPanel {

    private final GameListener myGameListener;

    private final JTextArea myQuestionPrompt = new JTextArea();

    private JPanel myAnswerPanel;

    private static JButton confirmButton;

    private final JTextArea myInstructions = createInstructionText();

    public QuestionPanel(final GameListener theGameListener) {
        this.myGameListener = theGameListener;
        myQuestionPrompt.setEditable(false);
        myQuestionPrompt.setLineWrap(true);
        myQuestionPrompt.setWrapStyleWord(true);
        myQuestionPrompt.setBorder(new RoundedBorder(20, new Insets(10, 10, 10, 10)));
        myQuestionPrompt.setBackground(Color.BLACK);
        myQuestionPrompt.setFont(Fonts.getPixelFont(16));
        myQuestionPrompt.setForeground(Color.WHITE);

        setSize(400, 400);
        setLayout(new BorderLayout());
        setBorder(new RoundedBorder(40));
        setBackground(Color.BLACK);
        setVisible(true);

        myQuestionPrompt.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(myQuestionPrompt, BorderLayout.NORTH);
    }

    /**
     *  The setQuestion method creates a new answer panel depending on the question attached to
     *  the currently selected room. This method also displays the game instructions and a prompt
     *  asking the user to select a room when there is no question currently displayed.
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
                case BooleanQuestion bq -> myAnswerPanel = new BooleanQuestionPanel(myGameListener, bq);
                case TextInputQuestion tiq -> myAnswerPanel = new TextInputQuestionPanel(myGameListener, tiq);
                default -> throw new IllegalStateException("Unexpected question type");
            }

            myAnswerPanel.setPreferredSize(new Dimension(300, 300));
            this.add(myAnswerPanel);
        }
        repaint();
    }

    /**
     * Creates and formats the instructions text to be displayed on the question panel.
     * @return returns the Instructions text as a JTextArea
     */
    private JTextArea createInstructionText(){
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
     * @param theGameListener game related event handling
     * @param theBg the button group for the true/false and multiple choice questions
     * @return returns a JButton to confirm answer choice and check correctness
     */
    private static JButton createConfirmButton(
            final GameListener theGameListener,
            final ButtonGroup theBg
    ) {
        setConfirmButton();
        confirmButton.addActionListener(e -> {
            var elements = theBg.getElements();
            elements.asIterator().forEachRemaining(element -> {
                JRadioButton button = (JRadioButton) element;
                if (button.isSelected()) {
                    theGameListener.checkAnswer(button.getText());

                }
            });
        });
        return confirmButton;
    }

    /**
     * An alternative method for creating a confirm button for use with a text input type
     * question as there are no associated button groups or labels for this type of question.
     * @param theInput A JTextField that represents a users response to a question.
     * @return returns a confirm button for use with text input type questions.
     */
    private static JButton createInputConfirmButton(final GameListener theGameListener, final JTextField theInput) {
        setConfirmButton();
        confirmButton.addActionListener(e -> theGameListener.checkAnswer(theInput.getText()));
        return confirmButton;
    }

    /**
     *  Sets the visual properties of a confirm button,
     */
    private static void setConfirmButton() {
        confirmButton = new JButton("Confirm");
        confirmButton.setBackground(Color.BLACK);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(Fonts.getPixelFont(12));
        confirmButton.setBorder(new RoundedBorder(20, new Insets(10, 0, 10, 0)));
        confirmButton.setPreferredSize(new Dimension(150, 100));

    }

    /* Inner Classes *//////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *  This class represents a panel for displaying multiple choice type questions.
     */
    private static class MultipleChoiceQuestionPanel extends JPanel {
        public MultipleChoiceQuestionPanel(
                final GameListener theGameListener,
                final MultipleChoiceQuestion theQuestion)
        {
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
         *  Sets the visual properties of the answer panel.
         * @param theBg represents a button group
         * @param theWrapper represents a container for answer options
         * @param theRadioButton represents a JRadioButton whose label is an answer.
         */
        private static void setAnswerPanel(
                final ButtonGroup theBg,
                final Box theWrapper,
                final JRadioButton theRadioButton
        ) {
            theRadioButton.setBackground(Color.BLACK);
            theRadioButton.setForeground(Color.WHITE);
            theRadioButton.setFont(Fonts.getPixelFont(14));
            theRadioButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 30, 5));
            theBg.add(theRadioButton);
            theWrapper.add(theRadioButton);
        }
    }

    /**
     *  Represents a panel to contain the answer options of a boolean type question.
     */
    private static class BooleanQuestionPanel extends JPanel {

        public BooleanQuestionPanel(
                final GameListener theGameListener,
                final BooleanQuestion theQuestion
        ) {

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
     * Represents a panel for containing the input field for a text input
     * type question.
     */
    private static class TextInputQuestionPanel extends JPanel {

        public TextInputQuestionPanel(
                final GameListener theGameListener,
                final TextInputQuestion theQuestion
        ) {

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
