package view;

import controller.GameListener;
import model.BooleanQuestion;
import model.MultipleChoiceQuestion;
import model.Question;
import model.TextInputQuestion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class QuestionPanel extends JPanel {

    private final GameListener myGameListener;
    private final JTextArea myQuestionPrompt = new JTextArea();
    private JPanel myAnswerPanel;

    public QuestionPanel(GameListener myGameListener) {
        this.myGameListener = myGameListener;
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

    public void setQuestion(final Question theQuestion) {

        if (myAnswerPanel != null) {
            remove(myAnswerPanel);
        }

        if (theQuestion == null) {
            return;
        }

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

    private class MultipleChoiceQuestionPanel extends JPanel {
        public MultipleChoiceQuestionPanel(GameListener theGameListener, MultipleChoiceQuestion theQuestion) {
            setBackground(Color.BLACK);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            List<String> answers = new ArrayList<>();
            answers.add(theQuestion.getAnswer());
            answers.addAll(theQuestion.getWrongAnswers());
            Collections.shuffle(answers);

            ButtonGroup bg = new ButtonGroup();
            add(Box.createVerticalGlue());

            Box horizontalWrapper = Box.createHorizontalBox();
            Box wrapper = Box.createVerticalBox();
            for (String answer : answers) {
                JRadioButton radioButton = new JRadioButton(answer);
                radioButton.setBackground(Color.BLACK);
                radioButton.setForeground(Color.WHITE);
                radioButton.setFont(Fonts.getPixelFont(14));
                radioButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 30, 5));
                bg.add(radioButton);
                wrapper.add(radioButton);
            }

            horizontalWrapper.add(wrapper);
            horizontalWrapper.add(Box.createHorizontalGlue());
            add(horizontalWrapper);

            add(Box.createVerticalGlue());


            JButton confirmButton = new JButton("Confirm");
            confirmButton.setBackground(Color.BLACK);
            confirmButton.setForeground(Color.WHITE);
            confirmButton.setFont(Fonts.getPixelFont(12));
            confirmButton.setBorder(new RoundedBorder(20, new Insets(10, 0, 10, 0)));
            confirmButton.setPreferredSize(new Dimension(150, 100));
            confirmButton.addActionListener(e -> {
                var elements = bg.getElements();
                elements.asIterator().forEachRemaining(element -> {
                    JRadioButton button = (JRadioButton) element;
                    if (button.isSelected()) {
                        theGameListener.checkAnswer(button.getText());

                    }
                });
            });
            wrapper = Box.createHorizontalBox();
            wrapper.add(Box.createHorizontalGlue());
            wrapper.add(confirmButton);
            wrapper.add(Box.createHorizontalGlue());
            add(wrapper);

            add(Box.createVerticalGlue());


        }
    }

    private class BooleanQuestionPanel extends JPanel {

        public BooleanQuestionPanel(GameListener theGameListener, BooleanQuestion theQuestion) {

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

                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                button.setFont(Fonts.getPixelFont(14));
                button.setBorder(BorderFactory.createEmptyBorder(5, 5, 30, 5));
                bg.add(button);
                wrapper.add(button);

            }


            horizontalWrapper.add(wrapper);
            horizontalWrapper.add(Box.createHorizontalGlue());
            add(horizontalWrapper);

            add(Box.createVerticalGlue());


            JButton confirmButton = new JButton("Confirm");
            confirmButton.setBackground(Color.BLACK);
            confirmButton.setForeground(Color.WHITE);
            confirmButton.setFont(Fonts.getPixelFont(12));
            confirmButton.setBorder(new RoundedBorder(20, new Insets(10, 0, 10, 0)));
            confirmButton.setPreferredSize(new Dimension(150, 100));
            confirmButton.addActionListener(e -> {
                var elements = bg.getElements();
                elements.asIterator().forEachRemaining(element -> {
                    JRadioButton button = (JRadioButton) element;
                    if (button.isSelected()) {
                        theGameListener.checkAnswer(button.getText());

                    }
                });
            });
            wrapper = Box.createHorizontalBox();
            wrapper.add(Box.createHorizontalGlue());
            wrapper.add(confirmButton);
            wrapper.add(Box.createHorizontalGlue());
            add(wrapper);

            add(Box.createVerticalGlue());
        }
    }

    private class TextInputQuestionPanel extends JPanel {

        public TextInputQuestionPanel(GameListener theGameListener, TextInputQuestion theQuestion) {

            setBackground(Color.BLACK);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            Box wrapper = Box.createVerticalBox();
            Box horizontalWrapper = Box.createHorizontalBox();

            add(Box.createVerticalGlue());

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new FlowLayout());
            textPanel.setBackground(Color.BLACK);
            add(textPanel);

            JTextField input = new JTextField("Enter your answer...");
            input.setBackground(Color.BLACK);
            input.setForeground(Color.WHITE);
            input.setFont(Fonts.getPixelFont(14));
            input.setBorder(new RoundedBorder(20, new Insets(5, 5, 5, 5)));
            textPanel.add(input);



            JButton confirmButton = new JButton("Confirm");
            confirmButton.setBackground(Color.BLACK);
            confirmButton.setForeground(Color.WHITE);
            confirmButton.setFont(Fonts.getPixelFont(12));
            confirmButton.setBorder(new RoundedBorder(20, new Insets(10, 0, 10, 0)));
            confirmButton.setPreferredSize(new Dimension(150, 100));
            confirmButton.addActionListener(e -> {
                theGameListener.checkAnswer(input.getText());

            });

            wrapper = Box.createHorizontalBox();
            wrapper.add(Box.createHorizontalGlue());
            wrapper.add(confirmButton);
            wrapper.add(Box.createHorizontalGlue());
            add(wrapper);

            add(Box.createVerticalGlue());





        }
    }
}
