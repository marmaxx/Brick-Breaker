package display.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class GameOver extends JPanel{
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private JButton exit = new JButton(" EXIT ");
    private JButton backToMenu = new JButton(" Back to Menu ");
    private BufferedImage backgroundImage; // background image 


    public GameOver(GameFrame frame){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(SCREEN_FULL_SIZE);
        

         try {
            backgroundImage = ImageIO.read(new File("src/GameOver.jpg")); // Chemin vers votre image
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
            frame.dispose();
            GameFrame gameFrame = new GameFrame();
			gameFrame.addMenu(new MenuPanel(gameFrame));
			gameFrame.getCardlayout().show(gameFrame.getContainer(), "menuPanel");
        });


        this.add(this.exit);
        this.add(this.backToMenu);
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
