package display.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import java.awt.event.*;

public class MenuInGame extends JPanel{

    private JButton resumeButton = createButton("Reprendre");
    private JButton BackToMenuButton = createButton("Retour Accueille");
    public JPanel buttonContainer = new JPanel();

    public MenuInGame(GameFrame frame, GamePanel pane){

        
        this.resumeButton.addActionListener(e -> {
           frame.getGame().resume();
           pane.resumeGamePanel();
           pane.requestFocus();
        });
        this.resumeButton.addMouseListener(new MenuInGameListener(this.resumeButton));


        this.BackToMenuButton.addActionListener(e -> {
            frame.dispose();
            frame.getGame().clearGameComponents();
            pane.removeAll();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GameFrame gameFrame = new GameFrame();
                    gameFrame.addMenu(new MenuPanel(gameFrame));
                    gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
                }
            });
            this.removeAll();
        });
        this.BackToMenuButton.addMouseListener(new MenuInGameListener(this.BackToMenuButton));


        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonContainer.add(Box.createVerticalGlue());
        buttonContainer.add(resumeButton);
        buttonContainer.add(Box.createVerticalStrut(40));
        buttonContainer.add(BackToMenuButton);
        buttonContainer.add(Box.createVerticalGlue());


       this.setLayout(new BorderLayout());
       this.setBackground(Color.WHITE);
       this.setOpaque(false);

       this.add(buttonContainer, BorderLayout.CENTER);
    }


    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 22));
        button.setPreferredSize(new Dimension(500, 80));
        button.setMaximumSize(new Dimension(500, 80));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false); 
        button.setBorder(this.createBorder());
        return button;
    }

    private Border createBorder(){
        Color color = new Color(102, 0, 102); 
        int borderWidth = 8; 
        Border border = BorderFactory.createLineBorder(color, borderWidth);

        return border;
    }
}


class MenuInGameListener extends MouseAdapter {
    private JButton button;
    public static final Dimension BUTTON_SIZE = new Dimension(500,80); 

    public MenuInGameListener(JButton button) {
        this.button = button;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Action à effectuer lorsque la souris entre dans la zone du bouton
        button.setSize((int) (button.getWidth() * 1.1), (int) (button.getHeight() * 1.1));
        button.setForeground(new Color(102, 0, 102));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Action à effectuer lorsque la souris sort de la zone du bouton
        button.setSize(BUTTON_SIZE);
        button.setForeground(Color.BLACK);
    }
}


