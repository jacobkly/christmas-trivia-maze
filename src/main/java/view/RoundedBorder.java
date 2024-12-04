package view;

import javax.swing.border.Border;
import java.awt.*;

/**
 * A custom border with rounded corners. Implements the Border interface to provide a rounded
 * rectangle border.
 *
 * @author Mathew Miller
 * @author Jacob Klymenko (Javadoc)
 * @version 1.0
 */
public final class RoundedBorder implements Border {

    /** The radius of the rounded corners. */
    private final int myRadius;

    /** Additional margin for the border. */
    private Insets myAdditionalMargin = new Insets(0, 0, 0, 0);

    /**
     * Creates a rounded border with the specified radius.
     *
     * @param theRadius the radius of the rounded corners.
     */
    public RoundedBorder(final int theRadius) {
        myRadius = theRadius;
    }

    /**
     * Creates a rounded border with the specified radius and additional margin.
     *
     * @param theRadius the radius of the rounded corners.
     * @param theAdditionalMargin    the additional margin for the border.
     */
    public RoundedBorder(final int theRadius, final Insets theAdditionalMargin) {
        myRadius = theRadius;
        myAdditionalMargin = theAdditionalMargin;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(6));
        g2.drawRoundRect(x + 3, y + 3, width - 6, height - 6, myRadius, myRadius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.myRadius / 2 + myAdditionalMargin.top,
                this.myRadius / 2 + myAdditionalMargin.left,
                this.myRadius / 2 + myAdditionalMargin.bottom,
                this.myRadius / 2 + myAdditionalMargin.right);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}

