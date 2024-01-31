import javax.swing.*;
import java.awt.*;

public class Paddle extends JPanel {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 20;
    private int x = 300;
    private int y = 200;
    private Color color = java.awt.Color.RED;

    public Paddle(int x, int y) {
        this.x=x;
        this.y=y;
        setBounds(x, y, WIDTH, HEIGHT); // Set the bounds of the JPanel
        setOpaque(false); // Make the JPanel transparent
    }

    public Paddle(Color color, int x, int y) {
        this(x, y);
        this.color = color;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(1, 1, WIDTH, HEIGHT); // Draw the rectangle at (0, 0)
        
    }
}
