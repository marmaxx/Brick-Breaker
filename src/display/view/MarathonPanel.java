package display.view;

import javax.swing.*;
import game.breakout.Breakout;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MarathonPanel extends JPanel{
    public static final long serialVersionUID = 101L;

    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private JButton Start = createStyledButton("START");
    private JButton Rules = createStyledButton("RULES");
    private JButton menu = createStyledButton(" Back to Menu ");
    transient private BufferedImage backgroundImage;

    public MarathonPanel(GameFrame gameFrame){
       this.setLayout(new FlowLayout()) ;
       this.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.height, SCREEN_FULL_SIZE.width/2));

       try {
        backgroundImage = ImageIO.read(new File("src" + java.io.File.separator + "resources" + java.io.File.separator + "BrickBreaker.png"));
    } catch (IOException e) {
        e.printStackTrace();
    }

    this.Start.setPreferredSize(BUTTON_SIZE);
    this.menu.setPreferredSize(BUTTON_SIZE);
    this.Rules.setPreferredSize(BUTTON_SIZE);

    this.Start.addMouseListener(new ButtonMouseListener(this.Start));
    this.Rules.addMouseListener(new ButtonMouseListener(this.Rules));
    this.menu.addMouseListener(new ButtonMouseListener(this.menu));


    this.Start.addActionListener((event) -> {
        gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "gamePanel"); // switching the card layout
        gameFrame.getPanelContainer().add(this, "MenuPanel");
        gameFrame.startGame(-1);
    });

    this.menu.addActionListener((event) -> {
        gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
    });

    this.add(Start);
    this.add(Rules);
    this.add(menu);


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


