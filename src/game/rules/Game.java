package game.rules;

import display.view.brickbreakerview.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

import java.util.concurrent.TimeUnit;

public abstract class Game implements Serializable{

	public static final long serialVersionUID = 16L;
    
	protected GamePanel panel;
	protected String name;
	protected boolean paused;
	protected int renderedFrames;
	protected int currentFPS;
	protected int maxFPS;
	protected long lastRenderTime;
	protected boolean vSync;

	public final static int DEFAULT_FPS = 60;

	
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
		this.setVSync(true);
		// on peut mettre true mais ça n'a pas l'air d'être constant à 60 FPS
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
	protected void setPanel(GamePanel panel) {
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
	protected void setRenderedFrames(int renderedFrames) {
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
	protected void setCurrentFPS(int currentFPS) {
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
	 * Get the VSync state
	 * 
	 * @return The VSync state
	 */
	public boolean isVSync() {
		return this.vSync;
	}

	/**
	 * Set the VSync state
	 * 
	 * @param vSync The VSync state to set
	 */
	public void setVSync(boolean vSync) {
		this.vSync = vSync;
	}

	/**
	 * Start the game<br>
	 * This method starts a new thread that updates the game<br>
	 * Note: This method has to be Overridden to start your custom game
	 * (using super.start() to keep the update loop running)
	 */
	public void start() {
		long second;
		Thread gameThread;

		// For some reason, the timer makes the game capped
		// at either 60 or 30 FPS
		// To make it into a feature, we use two different threads
		// depending on the VSync state
		if(this.isVSync()) {
			second = TimeUnit.SECONDS.toMillis(1);

			// Adding 3 to the maxFPS prevents the game from
			// running at a lower FPS than the actual maxFPS
			int updateTime = (int) (second / (this.getMaxFPS()+3));
			//System.out.println(updateTime);

			gameThread = new Thread(() -> {
				Timer gameTimer = new Timer(updateTime, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!Game.this.isPaused()){
							Game.this.update(updateTime);
						}
					}
				});
				gameTimer.start();
			});
		} else {
			second = TimeUnit.SECONDS.toNanos(1);
			double updateTime = second / this.getMaxFPS();

			gameThread = new Thread(() -> {			
				while (true) {
					long now = System.nanoTime();
					double timeSpent = now - this.getLastRenderTime();
			
					if(timeSpent > updateTime && !Game.this.isPaused()) {
						Game.this.update(updateTime);
					}
				}
			});
		}

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
	 * This method is called every frame to update the game.
	 * It refreshes both the logic and the rendering of the game
	 * This method is called every frame to update the game.
	 * It refreshes both the logic and the rendering of the game
	 */
	public void update(double deltaTime) {
		this.render();
		this.onUpdate(deltaTime);
	}

	/**
	 * This method is called every frame to update the game logic
	 */
	public abstract void onUpdate(double deltaTime);

	/**
	 * Render the game and calculate the FPS
	 */
	public void render(){
		// Calculate maxFPS

		long second = TimeUnit.SECONDS.toNanos(1);
		long now = System.nanoTime();
		long timeSpent = now - this.getLastRenderTime();
		this.setLastRenderTime(now);

		// Calculate FPS
		this.setCurrentFPS((int) (second / timeSpent));
		this.setRenderedFrames(this.getRenderedFrames() + 1);
		this.getPanel().repaint();
		
		this.getPanel().getFrame().setTitle(this.getName() + " - " + "(FPS: " + this.getCurrentFps() + ")"
		+ ", Frame: " + this.getRenderedFrames() + ")"
		// NOTE: The panel size depends on your system's DPI settings (scaling factor, ex: 125%,...)
		// The size displayed might not be the same as the actual number of pixels occupied by the window
		+ " [" + this.getPanel().getFrame().getSize().width + "x" + this.getPanel().getFrame().getSize().height + "]");
	}


}