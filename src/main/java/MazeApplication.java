import controller.GameController;
import controller.QuestionFactory;
import model.BooleanQuestion;
import model.MultipleChoiceQuestion;
import model.Question;
import model.TextInputQuestion;
import view.MazeViewFrame;

import javax.swing.*;

public class MazeApplication {


    public static void main(final String[] theArgs) {
        try {

            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            System.out.println("Look and feel not supported");

        }

        var questionsFromDatabase = QuestionFactory.getQuestionsFromDatabase();


        GameController controller = new GameController();
        MazeViewFrame view = new MazeViewFrame(controller);
        controller.setView(view);
        view.setResizable(false);
        view.setVisible(true);

    }
}
