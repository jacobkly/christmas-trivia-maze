import controller.GameController;
import controller.GameListener;
import view.MazeViewFrame;

import javax.swing.*;

public class MazeApplication {



    public static void main(final String[] theArgs) {
        try {

            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            System.out.println("Look and feel not supported");

        }

        GameController controller = new GameController();
        MazeViewFrame view = new MazeViewFrame(controller);
        controller.setView(view);
        view.setResizable(false);
        view.setVisible(true);

    }
}
