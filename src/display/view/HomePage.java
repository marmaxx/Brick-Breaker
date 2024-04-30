package display.view;

import display.view.brickbreakerview.*;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage; 

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.breakout.Breakout;


public class HomePage extends JPanel{

    private GameFrame gameframe;
    private BufferedImage backgroundImage; 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension BUTTON_SIZE = new Dimension((int)(SCREEN_FULL_SIZE.getWidth()*20/100), (int)(SCREEN_FULL_SIZE.getHeight()*10/100)); 

    private JButton BrickBreaker  = createStyledButton(" Brick Bracker ");
    private JButton SpaceInvader = createStyledButton(" Space Invader ");
    private JButton Ponk = createStyledButton(" Ponk ");
    private JButton exit = createStyledButton("Quitter");

    public HomePage(GameFrame gameframe){

        this.gameframe = gameframe;
        this.setLayout(new FlowLayout()); 
        this.setPreferredSize(new Dimension(SCREEN_FULL_SIZE));

        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "BrickBreaker.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.BrickBreaker.addActionListener((event) -> {
            gameframe.addMenu(new MenuPanel(gameframe));
            this.gameframe.getCardlayout().show(this.gameframe.getPanelContainer(), "menuPanel");
        });

        this.exit.addActionListener((event) -> {
            System.exit(0);
        });

        this.BrickBreaker.addMouseListener(new ButtonMouseListener(this.BrickBreaker));
        this.SpaceInvader.addMouseListener(new ButtonMouseListener(this.SpaceInvader));
        this.Ponk.addMouseListener(new ButtonMouseListener(this.Ponk));
        this.exit.addMouseListener(new ButtonMouseListener(this.exit));

        this.add(this.BrickBreaker); 
        this.add(this.SpaceInvader); 
        this.add(this.Ponk);  
        this.add(this.exit);                                            
    }


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 22));
        button.setPreferredSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
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
