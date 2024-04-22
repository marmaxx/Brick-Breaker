package display.view;

import javax.swing.*;

import game.breakout.Breakout;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClassicGamePanel extends JPanel {
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private JButton QuickGame = createStyledButton("Quick Game"); //button to start quick game
    private JButton Level = createStyledButton(" Level ");
    private JButton menu = createStyledButton(" Back to Menu");
    private BufferedImage backgroundImage; // background image 

    public ClassicGamePanel(GameFrame gameFrame){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.height, SCREEN_FULL_SIZE.width/2));

        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "BrickBreaker.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        QuickGame.setPreferredSize(BUTTON_SIZE);
        Level.setPreferredSize(BUTTON_SIZE);
        menu.setPreferredSize(BUTTON_SIZE);

        //add actionListener to the Quickgame button
        QuickGame.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "gamePanel"); // switching the card layout
            gameFrame.getPanelContainer().add(this, "classicGame");
            gameFrame.startGame(0);
        });

        Level.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuLevel");
        });

        menu.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
        });

        this.QuickGame.addMouseListener(new ButtonMouseListener(this.QuickGame));
        this.menu.addMouseListener(new ButtonMouseListener(this.menu));
        this.Level.addMouseListener(new ButtonMouseListener(this.Level));

        this.add(this.QuickGame);
        this.add(this.Level);
        this.add(this.menu);
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
    


