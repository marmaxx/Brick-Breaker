package game.brickbreaker.entities;

import display.engine.rules.GraphicalObject;
import javax.swing.JPanel;

public abstract class Entity extends JPanel {
    
    protected int posX, posY;
    protected int scaleX, scaleY;
    protected int red, blue, green;
    protected GraphicalObject representation;
    
    public Entity(
        int posX, int posY,
        int scaleX, int scaleY,
        int red, int blue, int green
    ) {
        this.posX = posX;
        this.posY = posY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public GraphicalObject getRepresentation() {
        return representation;
    }

    public void setRepresentation(GraphicalObject representation) {
        this.representation = representation;
    }
}
