package game.brickbreaker.entities;

import display.engine.rules.GraphicalObject;
import display.engine.shapes.Rectangle;
import display.view.GameFrame;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Entity {

    protected static final int STEP_WIDTH = 10; // 10px
    

    public Player(
        int posX, int posY,
        int scaleX, int scaleY,
        float red, float blue, float green
    ) {
        super(posX, posY, scaleX, scaleY, red, blue, green);
        this.setRepresentation(new Rectangle(posX, posY, scaleX, scaleY, red, blue, green));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color color = new Color(red, blue, green);
        g.setColor(color);
        g.fillRect(0, 0, 120, 20); //Displays a rectangle representing the paddle
    }

    public void moveLeft() {
        int newX = getX() - STEP_WIDTH;
        if (newX < 0) {
            newX = 0; // Prevent the paddle from moving off the screen
        }
        setLocation(newX, posY);
    }

    public void moveRight() {
        Dimension SCREEN_SIZE = GameFrame.SCREEN_SIZE;
        int newX = getX() + STEP_WIDTH;
        if (newX > SCREEN_SIZE.width - scaleX) {
            newX = SCREEN_SIZE.width - scaleX; // Prevent the paddle from moving off the screen
        }
        setLocation(newX, posY);
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
