package game.brickbreaker.entities;

import display.engine.shapes.Rectangle;
//import javax.swing.JPanel;

public abstract class Brick extends Entity {
    
    protected boolean isDestroyed, dropBonus;
    protected int lifespan;

    public Brick(
        int posX, int posY,
        int scaleX, int scaleY,
        int red, int blue, int green,
        int lifespan, boolean dropBonus
    ) {
        super(posX, posY, scaleX, scaleY, red, blue, green);
        this.isDestroyed = false;
        this.lifespan = lifespan;
        this.dropBonus = dropBonus;
        this.setRepresentation(new Rectangle(posX, posY, scaleX, scaleY, red, blue, green));
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        // not below 0, and not over 3
        if (lifespan < 0) {
            this.lifespan = 0;
        } else if (lifespan > 3) {
            this.lifespan = 3;
        } else {
            this.lifespan = lifespan;
        }
    }

    public boolean isDropBonus() {
        return dropBonus;
    }

    public void setDropBonus(boolean dropBonus) {
        this.dropBonus = dropBonus;
    }
}
