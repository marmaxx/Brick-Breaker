package display.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.breakout.Breakout;



public class WinPanel extends JPanel {
    public static final long serialVersionUID = 57L;
	
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private JButton exit = createStyledButton(" Exit ");
    private JButton backToMenu = createStyledButton(" Back to Menu ");
    transient private BufferedImage backgroundImage; // background image 

    public WinPanel(GameFrame frame){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(SCREEN_FULL_SIZE);


        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Victory.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }


        //settings exit button 
        this.exit.setPreferredSize(BUTTON_SIZE);
        this.exit.addActionListener((event) ->{
            System.exit(0);
        });

        //setting back to menu button
        this.backToMenu.setPreferredSize(BUTTON_SIZE);
        this.backToMenu.addActionListener(e -> {
            frame.getPanelContainer().remove(frame.getMenuLevel());
            frame.getPanelContainer().add(new MenuLevel(frame), "menuLevel");
            frame.getGame().clearGameComponents();
            frame.getGamePanel().getGameZone().removeAll();
            frame.getGame().setLife(3);
            frame.getGame().setNbBricks(1);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    frame.getCardlayout().show(frame.getPanelContainer(), "menuPanel");
                }
            });
        });

        this.exit.addMouseListener(new ButtonMouseListener(this.exit));
        this.backToMenu.addMouseListener(new ButtonMouseListener(this.backToMenu));

        this.add(this.exit);
        this.add(this.backToMenu);
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
