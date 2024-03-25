package display.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.breakout.Breakout;
import game.rules.Game;

public class MenuPanel extends JPanel {
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    JButton QuickGame = createStyledButton("Quick Game"); //button to start quick game
    JButton Marathon  = createStyledButton("Marathon mode");
    private BufferedImage backgroundImage; // background image 

    public MenuPanel(GameFrame gameFrame){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.height, SCREEN_FULL_SIZE.width/2));

        try {
            backgroundImage = ImageIO.read(new File("src/Menu.jpg")); // Chemin vers votre image
        } catch (IOException e) {
            e.printStackTrace();
        }

        QuickGame.setPreferredSize(BUTTON_SIZE);
        Marathon.setPreferredSize(BUTTON_SIZE);
        //add actionListener to the Quickgame button
        QuickGame.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getContainer(), "gamePanel"); // switching the card layout
            gameFrame.getContainer().add(this, "MenuPanel");
            Game game = new Breakout(gameFrame,0); //creates an instance of QuickGame Breakout
			game.start(); //starting the game 
        });
        Marathon.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getContainer(), "gamePanel"); // switching the card layout
            gameFrame.getContainer().add(this, "MenuPanel");
            Game game = new Breakout(gameFrame,1); //creates an instance of the Marathon mode
			game.start(); //starting the game 
        });

        this.add(QuickGame);
        this.add(Marathon);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219)); // Bleu
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        return button;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

