package game.breakout.entities;

import java.awt.Image;

import javax.swing.ImageIcon;

import display.engine.PhysicsEngine;
import display.engine.rules.PhysicalObject;
import display.engine.shapes.Circle;
import display.engine.utils.Vector2D;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Planet extends Entity {
	public static final long serialVersionUID = 1234L;
    transient public static final Image PLANET_IMAGE = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "etoileNoire.png").getImage();
    private int health=5;


    public Planet(
		Image image,
        int size,
		double mass, Vector2D position, boolean movable , int hp
    ) {
		super(mass,position,movable,new Circle(image, (int)position.getX(), (int)position.getY(), size, size));
        this.health = hp;
    }

    public void planetExplosion(){
		this.setActive(false);
		Image image = new ImageIcon(
			Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator+"gifExplosion.gif").getImage();
		this.getRepresentation().setImage(image);
        Planet planet =this;
		Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planet.destroy();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    
    @Override
    public void collided(PhysicalObject objectB) {
        this.health--;

        if (this.health<=0){
            Vector2D distance = objectB.getPosition().subtract(this.getPosition());
                objectB.applyForce(distance.normalize().multiply(50000));
                objectB.setAcceleration(new Vector2D(0, 0));
                this.planetExplosion();
        }
    }
    
}
