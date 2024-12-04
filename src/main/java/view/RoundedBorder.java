package view;

import javax.swing.border.Border;
import java.awt.*;

public final class RoundedBorder implements Border {

    private final int myRadius;

    private Insets myAdditionalMargin = new Insets(0, 0, 0, 0);

    public RoundedBorder(int radius) {
        this.myRadius = radius;
    }

    public RoundedBorder(int radius, Insets additionalMargin) {
        this(radius);
        myAdditionalMargin = additionalMargin;
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

