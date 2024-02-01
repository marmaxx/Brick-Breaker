import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import display.view.GameFrame;

public class Paddle extends JPanel implements KeyListener {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 20;
    private static final int PADDLE_STEP = 10; // How much the paddle moves with a given input. 
    private int x = 300;
    private int y = 200;
    private Color color = java.awt.Color.RED;

    public Paddle(int x, int y) {
        this.x=x;
        this.y=y;
        setBounds(x, y, WIDTH, HEIGHT); // Set the bounds of the Paddle 
        setOpaque(false); //makes it non transparent
        setFocusable(true); //allows for it to listen to key inputs using PaddleController
        addKeyListener(this); //allows for paddle to listen to key inputs using keyPressed and other methods
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

    public void moveLeft() {
        int newX = getX() - PADDLE_STEP;
        if (newX < 0) {
            newX = 0; // Prevent the paddle from moving off the screen
        }
        setLocation(newX, y);
    }

    public void moveRight() {
        Dimension SCREEN_SIZE = GameFrame.SCREEN_SIZE;
        int newX = getX() + PADDLE_STEP;
        if (newX > SCREEN_SIZE.width - WIDTH) {
            newX = SCREEN_SIZE.width - WIDTH; // Prevent the paddle from moving off the screen
        }
        setLocation(newX, y);
    }

    @Override
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