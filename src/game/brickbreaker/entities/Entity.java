package game.brickbreaker.entities;

import display.engine.rules.GraphicalObject;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Entity extends JPanel implements KeyListener {
    
    protected int posX, posY;
    protected int scaleX, scaleY;
    protected float red, blue, green;
    protected GraphicalObject representation;
    
    public Entity(
        int posX, int posY,
        int scaleX, int scaleY,
        float red, float blue, float green
    ) {
        this.posX = posX;
        this.posY = posY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.red = red;
        this.blue = blue;
        this.green = green;

        setBounds(posX,posY,scaleY,scaleX);
        setOpaque(false);
        setFocusable(true);
        addKeyListener(this);
        
    }

    public GraphicalObject getRepresentation() {
        return representation;
    }

    public void setRepresentation(GraphicalObject representation) {
        this.representation = representation;
    }
}
