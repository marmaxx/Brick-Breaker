package display.view;

import javax.swing.*;

import game.breakout.Breakout;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.Serializable;


public class MenuPanel extends JPanel {
    public static final long serialVersionUID = 56L;
	
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();


    private JButton Marathon  = createStyledButton("Marathon mode");
    private JButton classic_Game  = createStyledButton("Classic Game");
    private JButton SavedGames = createStyledButton("Saved Games");
    transient private BufferedImage backgroundImage; // background image 


    public MenuPanel(GameFrame gameFrame){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.height, SCREEN_FULL_SIZE.width/2));

        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "BrickBreaker.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Marathon.setPreferredSize(BUTTON_SIZE);
        classic_Game.setPreferredSize(BUTTON_SIZE);
        SavedGames.setPreferredSize(BUTTON_SIZE);
        

        Marathon.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuMarathon"); //switching card layout
        });
        SavedGames.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "Saved States"); // switching the card layout
        });



        classic_Game.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "classicGame"); //switching card layout
        });
        this.SavedGames.addMouseListener(new ButtonMouseListener(this.SavedGames));
        this.Marathon.addMouseListener(new ButtonMouseListener(this.Marathon));
        this.classic_Game.addMouseListener(new ButtonMouseListener(this.classic_Game));

        this.add(this.classic_Game);
        this.add(this.Marathon);
        this.add(SavedGames);
    }


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 22));
        button.setPreferredSize(new Dimension(400, 80));
        button.setMaximumSize(new Dimension(400, 80));
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        button.setContentAreaFilled(false);
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

