package display.view.brickbreakerview;

import display.view.*;

import javax.swing.*;

import game.breakout.Breakout;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClassicGamePanel extends JPanel {
    public static final long serialVersionUID = 100L;

    // instead of creating a button of a 300,300 size, it take 10% of the height and 30% of the width
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static double ratio = Scale.getRatioForResolution(SCREEN_FULL_SIZE.getWidth(), SCREEN_FULL_SIZE.getHeight());
    int scale = (int) (SCREEN_FULL_SIZE.getWidth() / SCREEN_FULL_SIZE.getHeight() * ratio);
    public static final Dimension BUTTON_SIZE = new Dimension((int)(21 * ratio), (int)(9 * ratio));

    private JButton QuickGame = createStyledButton("Quick Game"); //button to start quick game
    private JButton Level = createStyledButton(" Level ");
    private JButton menu = createStyledButton(" Back to Menu");
    transient private BufferedImage backgroundImage; // background image 

    public ClassicGamePanel(GameFrame gameFrame){
        this.setLayout(new FlowLayout());
        gameFrame.getPanelContainer().add(this, "classicGame");
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
            gameFrame.startBreakoutGame(0);
        });

        Level.addActionListener((event) -> {
            gameFrame.getPanelContainer().add(new MenuLevel(gameFrame),"menuLevel");
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
        button.setFont(new Font("Ubuntu", Font.BOLD, (int)(ratio*2)));
        button.setPreferredSize(new Dimension((int)(ratio*30), (int)(ratio*6)));
        button.setMaximumSize(new Dimension((int)(ratio*30), (int)(ratio*6)));
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
    


