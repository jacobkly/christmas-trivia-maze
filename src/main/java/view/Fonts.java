package view;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * A utility class for managing and providing pixel-style fonts.
 *
 * @author Mathew Miller
 * @version 1.0
 */
public final class Fonts {

    /** The base pixel font loaded from resources. */
    private static Font myBasePixelFont;

    /** Private constructor to prevent instantiation. */
    private Fonts() { /* do nothing */ }

    /**
     * Retrieves a pixel-style font with the specified size.
     * If the font is not already loaded, it is initialized from resources.
     *
     * @param theSize the desired font size
     * @return the pixel font at the specified size
     */
    public static Font getPixelFont(final int theSize) {
        if (myBasePixelFont == null) {
            try {
                myBasePixelFont = Font.createFont(
                        Font.TRUETYPE_FONT,
                        Objects.requireNonNull(Fonts.class
                                    .getResourceAsStream("/fonts/PressStart2P-vaV7.ttf")));
            } catch (final IOException | FontFormatException theException) {
                theException.printStackTrace();
            }
        }
        return myBasePixelFont.deriveFont(Font.PLAIN, theSize);
    }
}
