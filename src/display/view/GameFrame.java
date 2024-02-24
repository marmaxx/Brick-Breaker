package display.view;

import javax.swing.JFrame;

import java.awt.*;

public class GameFrame extends JFrame {
	public static final Color INTERFACE_BACKGROUND = Color.BLUE;

	private GamePanel gamePanel;
    
	/**
	 * Instantiates a new GameFrame
	 * 
	 * @param title The title of the frame
	 */
    public GameFrame(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);

        this.setLayout(new GridLayout(1, 1)); 

		this.getContentPane().setBackground(GameFrame.INTERFACE_BACKGROUND);

		this.setGamePanel(new GamePanel(this));
		this.getContentPane().add(this.getGamePanel());

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
}