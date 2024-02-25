package display.view;

import javax.swing.*;

import java.awt.*;

public class GamePanel extends JPanel {
	private static final Color GAME_BACKGROUND_COLOR = new Color(30,30,30);
	public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	private GameFrame gameFrame;
    private JPanel gameZone = new JPanel();
    private JPanel statZone = new JPanel();
	private JLabel score = new JLabel();
	private JLabel life = new JLabel();
	private JLabel nbBricks = new JLabel();
    
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

		this.gameZone.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.width, SCREEN_FULL_SIZE.height*9/10));
		this.gameZone.setBackground(Color.BLACK);

		this.statZone.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.width,SCREEN_FULL_SIZE.height/10));
		this.statZone.setBackground(Color.WHITE);
		this.statZone.setLayout(new FlowLayout());

		this.score.setPreferredSize(new Dimension(200,100));
		this.life.setPreferredSize(new Dimension(200,100));
		this.nbBricks.setPreferredSize(new Dimension(200,100));

		this.statZone.add(this.score);
		this.statZone.add(this.life);
		this.statZone.add(this.nbBricks); 

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

	public void updateStat(int score, int life, int Bricks){
		this.score.setText("Score : " + score);
		this.life.setText("Life : " + life); 
		this.nbBricks.setText("Bricks : " + Bricks);
	}
}