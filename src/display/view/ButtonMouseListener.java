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
        //button.setForeground(Color.BLACK);
        button.setFont(new Font("Ubuntu", Font.BOLD, 30));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setFont(new Font("Ubuntu", Font.BOLD, 22));
        //button.setForeground(Color.WHITE);
    }
}
