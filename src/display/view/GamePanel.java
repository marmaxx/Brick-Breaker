package display.view;

import javax.swing.*;

import game.breakout.Breakout;

import java.awt.*;

public class GamePanel extends JPanel {
	private static final Color GAME_BACKGROUND_COLOR = new Color(30,30,30);
	public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Dimension GAME_ZONE_SIZE = new Dimension(SCREEN_FULL_SIZE.width*4/5, SCREEN_FULL_SIZE.height*9/10);
	public static final Dimension STAT_ZONE_GAME = new Dimension(SCREEN_FULL_SIZE.width,SCREEN_FULL_SIZE.height/10);
	public static final String DEFAULT_IMAGE = Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Galaxy.png";
    
	private GameFrame gameFrame;
	private JPanel gameZone = new JPanelWithBackground(DEFAULT_IMAGE);
    private JPanel statZone = new JPanel();
	private JLabel score = new JLabel();
    private JLabel life = new JLabel();
    private JLabel nbBricks = new JLabel();
	private JButton menu = new JButton("menu");
	private MenuInGame menuInGame; 
	
    
	/**
	 * Instantiates a new GamePanel
	 * 
	 * @param gameFrame The frame in which the panel is displayed
	 * @param color The background color of the panel
	 */
    public GamePanel(GameFrame gameFrame, Color color) {
		this.setFrame(gameFrame);
		this.setBackground(color);
		this.setLayout(new FlowLayout()); //set GamePanel to FlowLayout
		this.setPreferredSize(SCREEN_FULL_SIZE);

		this.gameZone.setPreferredSize(GAME_ZONE_SIZE);
		//this.gameZone.setBackground(Color.BLACK);

		this.statZone.setPreferredSize(STAT_ZONE_GAME);
		this.statZone.setBackground(new Color(30,30,30));
		this.statZone.setLayout(new FlowLayout()); // set StatZone to flow Layout 

		this.score.setPreferredSize(new Dimension(200, 100));
        this.score.setForeground(Color.WHITE); // set color of the text
        this.life.setPreferredSize(new Dimension(200, 100));
        this.life.setForeground(Color.WHITE); // set color of the text
        this.nbBricks.setPreferredSize(new Dimension(200, 100));
        this.nbBricks.setForeground(Color.WHITE); // set color of the text

		this.menu.setPreferredSize(new Dimension(100, 50)); 
		this.menu.setForeground(Color.MAGENTA);

        this.menuInGame = new MenuInGame(this.gameFrame,this);
        this.menuInGame.setVisible(false);


        // Utilisation d'un JLayeredPane pour superposer les composants
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400,700));
        // Ajout du MenuInGame au premier plan
        this.menuInGame.getMenuInGameSquarePane().setBounds(0, 0, 400, 800);
        layeredPane.add(this.menuInGame.getMenuInGameSquarePane(), JLayeredPane.POPUP_LAYER);
        // Ajout du JLayeredPane au GamePanel


		this.menu.addActionListener((event) -> {
			this.getFrame().getGame().pause();
			this.gameZone.setVisible(false);
			this.menuInGame.setVisible(true);
		});


		this.statZone.add(this.score);
        this.statZone.add(this.life);
        this.statZone.add(this.nbBricks);
		this.statZone.add(this.menu);

		this.add(statZone);
		this.add(gameZone);
		this.add(layeredPane);	
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

	/**
	 * Get the JPanel in which the menu is displayed
	 * 
	 * @return The JPanel in which the menu is displayed
	 */
	public JPanel getMenu(){
		return this.menuInGame;
	}

	/** 
	 * update game life in JLabel
	 * @param life The Life in game
	 * @param bricks The number of brick in game
	*/
	public void updateLife(int life){
		this.life.setText("Life : " + life);
		//this.statZone.repaint(); // Redraw the statZone
	}

	/**
	 * update game score and number of bricks in JLabel
	 * @param score The score in game
	 * @param bricks The number of bricks 
	 */
	public void updateScore(int score, int bricks){
		//this.score.setText("Score : " + score);
		//this.nbBricks.setText("Bricks : " + bricks);
		//this.statZone.repaint(); // Redraw the statZone
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
	}

	public void resumeGamePanel(){
		this.menuInGame.setVisible(false);
		this.gameZone.setVisible(true);
	}
	
}