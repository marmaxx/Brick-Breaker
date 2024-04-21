package display.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ButtonMouseListener extends MouseAdapter {
    private JButton button;
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 

    public ButtonMouseListener(JButton button) {
        this.button = button;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Action à effectuer lorsque la souris entre dans la zone du bouton
        //button.setSize((int) (button.getWidth() * 1.1), (int) (button.getHeight() * 1.1));
        button.setForeground(Color.BLACK);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Action à effectuer lorsque la souris sort de la zone du bouton
        button.setSize(BUTTON_SIZE);
        button.setForeground(Color.WHITE);
    }
}
