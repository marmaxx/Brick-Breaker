package display.view;

import javax.swing.*;

import game.breakout.Breakout;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.Serializable;


public class MenuPanel extends JPanel implements Serializable{
    public static final long serialVersionUID = 56L;
	
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    JButton QuickGame = createStyledButton("Quick Game"); //button to start quick game
    JButton Level = createStyledButton(" Level "); 
    JButton Marathon  = createStyledButton("Marathon mode");
    JButton SavedState = createStyledButton("Saved States");
    transient private BufferedImage backgroundImage; // background image 

    public MenuPanel(GameFrame gameFrame){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.height, SCREEN_FULL_SIZE.width/2));

        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "BrickBreaker.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Level.setPreferredSize(BUTTON_SIZE);
        QuickGame.setPreferredSize(BUTTON_SIZE);
        Marathon.setPreferredSize(BUTTON_SIZE);
        SavedState.setPreferredSize(BUTTON_SIZE);
        //add actionListener to the Quickgame button
        QuickGame.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "gamePanel"); // switching the card layout
            gameFrame.getPanelContainer().add(this, "MenuPanel");
            gameFrame.startGame(0);
        });

        Level.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuLevel");
        });
        
        Marathon.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "gamePanel"); // switching the card layout
            gameFrame.getPanelContainer().add(this, "MenuPanel");
            gameFrame.startGame(-1);
        });
        SavedState.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "gamePanel"); // switching the card layout
            gameFrame.getPanelContainer().add(this, "MenuPanel");
            gameFrame.startGame(100);
        });


        this.add(QuickGame);
        this.add(Marathon);
        this.add(Level);
        this.add(SavedState);
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

