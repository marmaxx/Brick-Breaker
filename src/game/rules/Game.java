package game.rules;

import display.view.GamePanel;

import java.util.concurrent.TimeUnit;

public abstract class Game{
    protected GamePanel panel;
	protected String name;
	protected boolean paused;
	protected int renderedFrames;
	protected int currentFPS;
	protected int maxFPS;
	protected long lastRenderTime;

	public final int DEFAULT_FPS = 60;

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
		this.setMaxFPS(DEFAULT_FPS);
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
	 * Get the maximum FPS
	 * 
	 * @return The maximum FPS
	 */
	public int getMaxFPS() {
		return this.maxFPS;
	}

	/**
	 * Set the maximum FPS
	 * 
	 * @param maxFPS The maximum FPS to set
	 */
	public void setMaxFPS(int maxFPS) {
		this.maxFPS = maxFPS;
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
	 * Start the game<br>
	 * This method starts a new thread that updates the game<br>
	 * Note: This method has to be Overridden to start your custom game
	 * (using super.start() to keep the update loop running)
	 */
	public void start() {
		Thread gameThread = new Thread(() -> {
			// Calculate the time between each update
			long second = TimeUnit.SECONDS.toNanos(1);
			double updateTime = second / this.getMaxFPS();
		
			while (true) {
				long now = System.nanoTime();
				double timeSpent = now - this.getLastRenderTime();
		
				if(timeSpent > updateTime && !Game.this.isPaused()) {
					Game.this.update();
		
					// Calculate FPS
					this.setCurrentFPS((int) (second / timeSpent));
					this.setLastRenderTime(now);
				}
			}
		});
		gameThread.start();
	}

	/**
	 * Pause the game
	 */
	public void pause() {
		this.getPanel().getFrame().setTitle(this.getName() + " (Pause)");
		this.paused = true;
	}

	/**
	 * Resume the game
	 */
	public void resume() {
		this.paused = false;
	}

	/**
	 * Check if the game is paused
	 * 
	 * @return True if the game is paused, false otherwise
	 */
	public boolean isPaused() {
		return this.paused;
	}

	/**
	 * This method is called every frame to update the game.<br>
	 * It natively calculates the FPS and updates the window title but should be overridden to update the game logic
	 * (using super.update() to keep the informations & rendering correct)
	 */
	public void update() {
		this.setRenderedFrames(this.getRenderedFrames() + 1);
		this.render();

		this.getPanel().getFrame().setTitle(this.getName() + " - " + "(FPS: " + this.getCurrentFps() + ")"
		+ ", Frame: " + this.getRenderedFrames() + ")"
		// NOTE: The panel size depends on your system's DPI settings (scaling factor, ex: 125%,...)
		// The size displayed might not be the same as the actual number of pixels occupied by the window
		+ " [" + this.getPanel().getFrame().getSize().width + "x" + this.getPanel().getFrame().getSize().height + "]");

	}

	/**
	 * Render the game
	 */
	public void render(){
		this.getPanel().repaint();
	}
}