package display.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;


public class ButtonMouseListener extends MouseAdapter implements Serializable{
    public static final long serialVersionUID = 106L;

    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static double ratio = Scale.getRatioForResolution(SCREEN_FULL_SIZE.getWidth(), SCREEN_FULL_SIZE.getHeight());
    int scale = (int) (SCREEN_FULL_SIZE.getWidth() / SCREEN_FULL_SIZE.getHeight() * ratio);

    private JButton button;
    public static final Dimension BUTTON_SIZE = new Dimension((int)(ratio * 23),(int)(ratio * 8)); 

    public ButtonMouseListener(JButton button) {
        this.button = button;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.setForeground(Color.BLACK);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setForeground(Color.WHITE);
    }
}
