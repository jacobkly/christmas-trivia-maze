package view;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    private final int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(6));
        g2.drawRoundRect(x + 3, y + 3, width -6, height - 6, radius, radius);
//        g2.drawRoundRect(x - 10, y - 10, width + 20, height+20, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius / 2, this.radius /2 , this.radius /2 , this.radius / 2);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}

