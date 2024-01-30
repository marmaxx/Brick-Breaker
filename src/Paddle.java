import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;


public class Paddle extends JComponent {
    private static final int WIDTH = 100; //refactor it to lowercase if we implement a powerup that modifies width
    private static final int HEIGHT = 20; 
    private Rectangle paddleRectangle;


    public Paddle() {
        paddleRectangle = new Rectangle(200, 300, WIDTH, HEIGHT);
        setOpaque(false); 
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(paddleRectangle.x, paddleRectangle.y, paddleRectangle.width, paddleRectangle.height);
    }


	    
}
