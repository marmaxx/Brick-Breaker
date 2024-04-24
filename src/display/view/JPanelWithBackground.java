package display.view;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelWithBackground extends JPanel{
    transient private ImageIcon background;

    public JPanelWithBackground(String image) {
        background = new ImageIcon(image);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
