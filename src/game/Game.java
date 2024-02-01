package game;

import javax.swing.*;

public abstract class Game extends JPanel{
    protected JFrame frame;

    public Game(JFrame frame) {
        this.frame = frame;
    }

    public abstract void init();
    public abstract void update();
    public abstract void render();
}