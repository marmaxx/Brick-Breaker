package display.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

public class GameFrame extends JFrame {
	public static JPanel GameView; //to be able to add/manipulate graphical elements to the game
    
	public static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize(); // get the size of the screen
    
    public GameFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Breakout");
        setLayout(new BorderLayout()); // set Layout Manager of JFrame to Border Layout
        setSize(SCREEN_SIZE); // set the size of the Frame to size of screen
        
        GameView=GameZone();
        this.add(GameView, BorderLayout.CENTER); // Add game zone JPanel to the center of the Frame
        this.setVisible(true); // set the Frame to visible
    }
    
    /**
     * 
     * gameZone.setLayout(null) is necessary, it appears that if this line of code isn't there 
     * gameZone inherits the Layout of View and it creates unexpected results
     */
    public JPanel GameZone(){
        JPanel gameZone = new JPanel(); // create new JPanel for the game zone 
        gameZone.setBackground(new Color(153,153,153));
        gameZone.setLayout(null); 
        return gameZone;
    }
}