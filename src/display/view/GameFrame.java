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

		this.cardLayout = new CardLayout();
		this.container = new JPanel(cardLayout);
		//this.container.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.height, SCREEN_FULL_SIZE.width/2));

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
	 * set the container panel attach the the frame
	 * 
	 * @param JPanel The container panel to attach to the frame
	 */
	private void setContainer(JPanel container){
		this.container = container;
	}

	public CardLayout getCardlayout(){
		return this.cardLayout;
	}


	public void addMenu(MenuPanel menu){
		this.container.add(menu, "menuPanel");
		this.menuPanel = menu;
	}

	public MenuPanel getMenuPanel(){
		return this.menuPanel;
	}
}