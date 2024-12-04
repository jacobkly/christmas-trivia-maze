package view;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public final class Fonts {

    private static Font myBasePixelFont;

    private Fonts() { /* do nothing */ }

    public static Font getPixelFont(int size) {
        if (myBasePixelFont == null) {
            try {
                myBasePixelFont = Font.createFont(
                        Font.TRUETYPE_FONT,
                        Objects.requireNonNull(Fonts.class.getResourceAsStream("/fonts/PressStart2P-vaV7.ttf")));
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }
        }

        return myBasePixelFont.deriveFont(Font.PLAIN, size);
    }
}
