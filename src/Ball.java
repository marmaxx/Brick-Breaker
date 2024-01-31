import java.awt.*;
import javax.swing.JComponent;

public class Ball extends JComponent{
    private Coordinates position;
    private Coordinates speed;
    private Rectangle ballRectangle;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    public Ball (Coordinates pos){
        this.position=pos;
        ballRectangle = new Rectangle((int)position.getX(), (int)position.getY(), WIDTH, HEIGHT);
    }

    public Coordinates getPosition(){ return this.position;}
    public Coordinates getSpeed(){ return this.speed;}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(ballRectangle.x, ballRectangle.y, ballRectangle.width, ballRectangle.height);
    }        
}
