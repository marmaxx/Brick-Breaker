package display.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import game.breakout.Breakout;
import java.io.Serializable;


public class GameFrame extends JFrame {
	public static final long serialVersionUID = 50L;

	public static final Color INTERFACE_BACKGROUND = Color.WHITE;

	private Breakout game;
	private GamePanel gamePanel;
	private MenuPanel menuPanel;
	private JPanel container;
	private GameOver game_over; 
	private WinPanel game_win;
	private MenuLevel menu_level;
	private MarathonPanel menu_Marathon;
	private ClassicGamePanel menu_classic;
	private SavedGames savedGames;
	private CardLayout cardLayout;
	public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public int nbLevelUnlock = 1;
    
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
		this.menu_level = new MenuLevel(this);
		this.menu_Marathon = new MarathonPanel(this);
		this.menu_classic = new ClassicGamePanel(this);
		this.savedGames = new SavedGames(this);


		this.container.add(this.gamePanel, "gamePanel");
		this.container.add(this.game_over, "gameOver"); 
		this.container.add(this.game_win, "winPanel");
		this.container.add(this.menu_level, "menuLevel");
		this.container.add(this.menu_Marathon, "menuMarathon");
		this.container.add(this.menu_classic, "classicGame");
		this.container.add(this.savedGames, "Saved States");

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

	public SavedGames getSavedGames() {
		return this.savedGames;
	}

	public void setSavedGames(SavedGames savedGames) {
		this.savedGames = savedGames;
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
	public JPanel getPanelContainer() {
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

	public MenuLevel getMenuLevel(){
		return this.menu_level;
	}


	public void setGame(Breakout game){
		this.game = game;
	}

	public void setnbLevelUnlock(){
		++this.nbLevelUnlock;
	}

	public int getNbLevelUnlock(){
		return this.nbLevelUnlock;
	}

	public Breakout getGame(){
		return this.game;
	}

	public void startGame(int level){
		game = new Breakout(this, level); //created instance of Breakout
		game.start(); //starting the game 
	}

	public void loadGame(String saveName){
		Breakout game =null;
		try {
			game = Breakout.readFile(saveName);
			game.gameframe =this;
			game.setup(this.getGamePanel(), 100,"Breakout");
			game.getGameFrame().repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.game = game;
		game.start(); //starting the game 
	}
}