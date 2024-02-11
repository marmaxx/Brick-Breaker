package game.rules;

import display.view.GamePanel;
import game.breakout.Breakout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import java.util.concurrent.TimeUnit;

public abstract class Game{
    protected GamePanel panel;
	protected String name;
	protected int renderedFrames;
	protected int currentFPS;
	protected long lastRenderTime;

	/**
	 * Initialize a new game
	 * 
	 * @param panel The panel to render the game
	 * @param name The name of the game
	 */
    public Game(GamePanel panel, String name) {
        this.setPanel(panel);
		this.getPanel().getFrame().setTitle(name);
		this.setName(name);
		this.setRenderedFrames(0);
		this.setCurrentFPS(0);
    }

	/**
	 * Get the panel in which the game is rendered
	 * 	
	 * @return The panel in which the game is rendered
	 */
	public GamePanel getPanel() {
		return this.panel;
	}

	/**
	 * Set the panel in which the game is rendered
	 * 
	 * @param panel The panel to set
	 */
	private void setPanel(GamePanel panel) {
		this.panel = panel;
	}

	/**
	 * Get the name of the game
	 * 
	 * @return The name of the game
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name of the game
	 * 
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the total number of frames rendered
	 * 
	 * @return The number of frames rendered
	 */
	private int getRenderedFrames() {
		return this.renderedFrames;
	}

	/**
	 * Set the total number of frames rendered
	 * 
	 * @param renderedFrames The number of frames to set
	 */
	private void setRenderedFrames(int renderedFrames) {
		this.renderedFrames = renderedFrames;
	}

	/**
	 * Get the current FPS
	 * 
	 * @return The current FPS
	 */
	public int getCurrentFps() {
		return this.currentFPS;
	}

	/**
	 * Set the current FPS
	 * 
	 * @param currentFPS The FPS to set
	 */
	private void setCurrentFPS(int currentFPS) {
		this.currentFPS = currentFPS;
	}

	/**
	 * Get the time of the last render
	 * 
	 * @return The time of the last render
	 */
	private long getLastRenderTime() {
		return this.lastRenderTime;
	}

	/**
	 * Set the time of the last render
	 * 
	 * @param lastRenderTime The time of the last render to set
	 */
	private void setLastRenderTime(long lastRenderTime) {
		this.lastRenderTime = lastRenderTime;
	}

	/**
	 * Start the game
	 */
	public void start() {
		Timer timer = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.this.update();

			}
		});
		timer.start();
	}

	/**
	 * This method is called every frame to update the game.<br>
	 * It natively calculates the FPS and updates the window title but should be overridden to update the game logic
	 */
	public void update() {
		// Calculate FPS
		long currentTime = System.nanoTime();
		long deltaTime = currentTime - this.getLastRenderTime();
		this.setCurrentFPS((int) TimeUnit.SECONDS.toNanos(1) / (int) deltaTime);
		this.setLastRenderTime(currentTime);

		this.setRenderedFrames(this.getRenderedFrames() + 1);
		this.getPanel().getFrame().setTitle(this.getName() + " - " + "(FPS: " + this.getCurrentFps() + ", Frame: " + this.getRenderedFrames() + ")");
		this.render();
	}

	/**
	 * Render the game
	 */
	public abstract void render();
}