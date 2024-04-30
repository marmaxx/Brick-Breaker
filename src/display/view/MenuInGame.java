package display.view;

import display.view.brickbreakerview.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import game.breakout.Breakout;



public class MenuInGame extends JPanel {
    public static final long serialVersionUID = 54L;

    private JButton resumeButton = createButton("Reprendre");
    private JButton BackToMenuButton = createButton("Retour Accueil");
    private JButton settings = createButton("Settings");
    private JPanel buttonContainer = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dessiner l'image de fond
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    };
    transient private BufferedImage backgroundImage; // background image 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Dimension MENU_ZONE = new Dimension(SCREEN_FULL_SIZE.width*4/5, SCREEN_FULL_SIZE.height*9/10);

    public MenuInGame(GameFrame frame, GamePanel pane){

        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "MenuInGame.jpg")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.resumeButton.addActionListener(e -> {
           frame.getGame().resume();
           pane.resumeGamePanel();
           pane.requestFocus();
        });
        this.resumeButton.addMouseListener(new MenuInGameListener(this.resumeButton));


        this.BackToMenuButton.addActionListener(e -> {
            frame.getGame().clearGameComponents();
            pane.getGameZone().removeAll();
            pane.getMenu().setVisible(false);
            pane.getGameZone().setVisible(true);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    frame.getCardlayout().show(frame.getPanelContainer(), "menuPanel");
                }
            });
        });
        this.BackToMenuButton.addMouseListener(new MenuInGameListener(this.BackToMenuButton));

        this.settings.addMouseListener(new MenuInGameListener(this.settings));


        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonContainer.add(Box.createVerticalGlue());
        buttonContainer.add(resumeButton);
        buttonContainer.add(Box.createVerticalStrut(40));
        buttonContainer.add(BackToMenuButton);
        buttonContainer.add(Box.createVerticalStrut(40));
        buttonContainer.add(settings);
        buttonContainer.add(Box.createVerticalGlue());

       


       this.setLayout(new BorderLayout());
       this.setPreferredSize(MENU_ZONE);
       this.add(buttonContainer, BorderLayout.CENTER);
    }


    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 22));
        button.setPreferredSize(new Dimension(400, 80));
        button.setMaximumSize(new Dimension(400, 80));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false); 
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        return button;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
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
        button.setFont(new Font("Ubuntu", Font.BOLD, 30));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Action à effectuer lorsque la souris sort de la zone du bouton
        button.setFont(new Font("Ubuntu", Font.BOLD, 22));
    }
}


