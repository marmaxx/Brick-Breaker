import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Paddle extends JPanel {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 20;
    private int x = 300;
    private int y = 200;
    private Color color = java.awt.Color.RED;
    private PaddleController controller;

    public Paddle(int x, int y) {
        this.x=x;
        this.y=y;
        setBounds(x, y, WIDTH, HEIGHT); // Set the bounds of the Paddle 
        setOpaque(false); //makes it non transparent
        setFocusable(true); //allows for it to listen to key inputs using PaddleController
        
        controller=new PaddleController(this);
    }

    public Paddle(Color color, int x, int y) {
        this(x, y);
        this.color = color;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, WIDTH, HEIGHT); //Displays a rectangle representing the paddle
        
    }
    
    
    private class PaddleController implements KeyListener{
    	//TODO: Modify PADDLE_STEP according to window or screen size
        private static final int PADDLE_STEP = 10; // How much the paddle moves with a given input. 
        private Paddle paddle;

        
        public PaddleController(Paddle paddle) {
        	paddle.addKeyListener(this); //allows for paddle to listen to key inputs using keyPressed and other methods
            this.paddle = paddle;
        }

        public void moveLeft() {
            int newX = paddle.getX() - PADDLE_STEP;
            if (newX < 0) {
                newX = 0; // Prevent the paddle from moving off the screen
            }
            paddle.setLocation(newX, paddle.y);
        }

        public void moveRight() {
        	Dimension SCREEN_SIZE = View.SCREEN_SIZE;
            int newX = paddle.getX() + PADDLE_STEP;
            if (newX > SCREEN_SIZE.width - Paddle.WIDTH) {
                newX = SCREEN_SIZE.width - Paddle.WIDTH; // Prevent the paddle from moving off the screen
            }
            paddle.setLocation(newX, paddle.y);
        }
        
        
        
        
        
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moveLeft();
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                moveRight();
            }
            
        }

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		} 
    }
}
