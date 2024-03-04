package display.view;

import javax.swing.*;
import java.awt.*;


public class GameOver extends JPanel{
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private JLabel title = new JLabel(" GAME OVER "); 
    private JButton exit = new JButton(" EXIT ");


    public GameOver(){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(SCREEN_FULL_SIZE);
        
         //settings title
         this.add(this.title);

         //settings exit button 
         this.exit.setPreferredSize(BUTTON_SIZE);
         this.exit.addActionListener((event) ->{
             System.exit(0);
         });
        this.add(this.exit);
 
    }
}
