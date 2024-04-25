package display.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;


public class ButtonMouseListener extends MouseAdapter implements Serializable{
    public static final long serialVersionUID = 106L;

    private JButton button;
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 

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
