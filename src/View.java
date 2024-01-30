import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

public class View extends JFrame {
	public static View GameView; //to be able to add/manipulate elements in the view
    
	Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize(); // get the size of the screen
    
    public View(){
    	GameView=this;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Breakout");
        setLayout(new BorderLayout()); // set Layout Manager of JFrame to Border Layout
        setSize(ScreenSize); // set the size of the Frame to size of screen

        this.add(GameZone(), BorderLayout.CENTER); // Add game zone JPanel to the center of the Frame
        this.setVisible(true); // set the Frame to visible
    }
    
    public JPanel GameZone(){
        JPanel gameZone = new JPanel(); // create new JPanel for the game zone 
        gameZone.setBackground(new Color(153,153,153));
        return gameZone;
    }
}