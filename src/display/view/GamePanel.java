package display.view;

import javax.swing.JPanel;

import java.awt.*;

public class GamePanel extends JPanel {
	private static final Color GAME_BACKGROUND_COLOR = new Color(30,30,30);
	public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private GameFrame gameFrame;
	private JPanel gameZone = new JPanel();
	private JPanel statZone = new JPanel();
    
	/**
	 * Instantiates a new GamePanel
	 * 
	 * @param gameFrame The frame in which the panel is displayed
	 * @param color The background color of the panel
	 */
    public GamePanel(GameFrame gameFrame, Color color) {

		this.setFrame(gameFrame);
		this.setBackground(color);
		this.setLayout(new FlowLayout());
		//this.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.height, SCREEN_FULL_SIZE.width/2));
		this.setPreferredSize(SCREEN_FULL_SIZE);

		gameZone.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.width/2, SCREEN_FULL_SIZE.height));
		gameZone.setBackground(Color.BLACK);

		statZone.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.width,SCREEN_FULL_SIZE.height/10));
		statZone.setBackground(Color.WHITE);

		this.add(statZone);
		this.add(gameZone);
    }

	/**s
	 * Instantiates a new GamePanel
	 * 
	 * @param gameFrame The frame in which the panel is displayed
	 */
	public GamePanel(GameFrame gameFrame) {
		this(gameFrame, GamePanel.GAME_BACKGROUND_COLOR);
	}

	/**
	 * Get the frame in which the panel is displayed
	 * 
	 * @return The frame in which the panel is displayed
	 */
	public GameFrame getFrame() {
		return this.gameFrame;
	}

	/**
	 * Set the frame in which the panel is displayed
	 * 
	 * @param gameFrame The frame in which the panel is displayed
	 */
	private void setFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	/**
	 * Get the JPanel in which the game is displayed
	 * 
	 * @return The JPanel in which the game is displayed
	 */
	public JPanel getGameZone(){
		return this.gameZone;
	}
}