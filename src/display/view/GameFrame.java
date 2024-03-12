package display.view;

import game.rules.Game;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
	public static final Color INTERFACE_BACKGROUND = Color.WHITE;

	private Game game;
	private GamePanel gamePanel;
	private MenuPanel menuPanel;
	private JPanel container;
	private GameOver game_over; 
	private WinPanel game_win;
	private CardLayout cardLayout;
	public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    
	/**
	 * Instantiates a new GameFrame
	 * 
	 * @param title The title of the frame
	 */
    public GameFrame(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);

        this.setLayout(new FlowLayout()); 

		this.getContentPane().setBackground(GameFrame.INTERFACE_BACKGROUND);

		this.cardLayout = new CardLayout(); //set new cardLayout
		this.container = new JPanel(cardLayout); //creat containers for managing panel in the frame 

		this.setGamePanel(new GamePanel(this));
		this.game_over = new GameOver(this);
		this.game_win = new WinPanel(this);

		this.container.add(this.gamePanel, "gamePanel");
		this.container.add(this.game_over, "gameOver"); 
		this.container.add(this.game_win, "winPanel");

		this.add(this.container);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Instantiates a new GameFrame
	 */
	public GameFrame() {
		this("Fenêtre de jeu vide");
	}

	/**
	 * Get the game panel attached to the frame
	 * 
	 * @return The game panel attached to the frame
	 */
	public GamePanel getGamePanel() {
		return this.gamePanel;
	}

	/**
	 * Set the game panel to attach to the frame
	 * 
	 * @param gamePanel The game panel to attach to the frame
	 */
	private void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	/**
	 * Get the container panel to the frame
	 * 
	 * @return The container panel attached to the frame
	 */
	public JPanel getContainer() {
		return this.container;
	}


	/**
	 * Get the cardLayout used for managing panel in the frame
	 * 
	 * @return The CardLayout
	 */
	public CardLayout getCardlayout(){
		return this.cardLayout;
	}


	/**
	 * Add MenuPanel to the Container
	 * 
	 * @param MenuPanel The menu panel to add
	 */
	public void addMenu(MenuPanel menu){
		this.container.add(menu, "menuPanel");
		this.menuPanel = menu;
	}


	/**
	 * Get the menu panel attach to the containers
	 * 
	 * @return The menu Panel
	 */
	public MenuPanel getMenuPanel(){
		return this.menuPanel;
	}
	
	/**
	 * Get the game over panel attach to the containers
	 * 
	 * @return The game over Panel
	 */
	public GameOver getGameOverPanel(){
		return this.game_over;
	}

	/**
	 * Get the win panel attach to the containers
	 * 
	 * @return The win Panel
	 */
	public WinPanel getWinPanel(){
		return this.game_win;
	}


	public void setGame(Game game){
		this.game = game;
	}

	public Game getGame(){
		return this.game;
	}
}