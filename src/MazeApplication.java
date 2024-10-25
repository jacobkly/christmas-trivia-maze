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


        MazeViewFrame view = new MazeViewFrame();
        view.setResizable(true);
        view.setVisible(true);

    }
}
