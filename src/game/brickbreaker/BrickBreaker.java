package game.brickbreaker;

import game.Game;
import javax.swing.JFrame;
import java.awt.*;

import display.engine.shapes.Rectangle;

public class BrickBreaker extends Game {
    private Rectangle brick;

    public BrickBreaker(JFrame frame) {
        super(frame);
        brick = new Rectangle();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void init() {
        frame.setTitle("Breakout");
    }

    @Override
    public void update() {
        frame.repaint();
    }

    @Override
    public void render() {
        // Utilisez 'frame' ici pour rendre le jeu
        frame.repaint();

    }

    public void draw(Graphics g) {
        int x = (int) brick.getPosX();
        int y = (int) brick.getPosY();
        int width = (int) brick.getScaleX();
        int height = (int) brick.getScaleY();
        
        // Définir la couleur de remplissage
        g.setColor(new Color(brick.getRed(), brick.getGreen(), brick.getBlue()));
        
        // Dessiner le rectangle rempli
        g.fillRect(x, y, width, height);

        System.out.println("x: " + x + ", y: " + y + ", width: " + width + ", height: " + height);
    }
}