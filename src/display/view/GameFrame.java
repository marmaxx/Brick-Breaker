package display.view;

import javax.swing.*;

import java.awt.*;

public class GameFrame extends JFrame {
	public static final Color INTERFACE_BACKGROUND = Color.WHITE;

	private GamePanel gamePanel;
	private MenuPanel menuPanel;
	private JPanel container;
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
		this.container.add(this.gamePanel, "gamePanel");

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
}