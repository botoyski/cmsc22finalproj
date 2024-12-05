import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

class RoundedButton extends JButton {
    private static final int ROUNDED_CORNER_RADIUS = 20; // Adjust the radius as needed

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Make the button transparent
        setFocusPainted(false); // Remove focus border
        setBorderPainted(false); // Remove border
        setForeground(Color.BLACK); // Set text color
        setFont(new Font("Garamond", Font.BOLD, 14)); // Set font
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ROUNDED_CORNER_RADIUS, ROUNDED_CORNER_RADIUS));
        super.paintComponent(g);
    }
}