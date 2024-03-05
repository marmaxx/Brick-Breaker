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
    JButton QuickGame = new JButton("Quick Game"); //button to start quick game
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

        //add actionListener to the Quickgame button
        QuickGame.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getContainer(), "gamePanel"); // switching the card layout
            Game game = new Breakout(gameFrame); //created instance of Breakout
			game.start(); //starting the game 
        });

        this.add(QuickGame);
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

