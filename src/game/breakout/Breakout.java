package game.breakout;

import java.util.ArrayList;
import java.util.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import display.view.GameFrame;
import game.breakout.entities.Ball;
import game.breakout.entities.Player;
import game.breakout.entities.rules.Entity;
import game.rules.Game;

public class Breakout extends Game{
	private ArrayList<Entity> bricks;
	private Player player;
	private Ball ball;

	/**
	 * Instantiates a new Breakout game
	 * 
	 * @param gameFrame The frame in which the game is displayed
	 */
	public Breakout(GameFrame gameFrame) {
		super(gameFrame.getGamePanel(), "Breakout");
		this.setBricks(new ArrayList<Entity>());
		this.setPlayer(new Player(300,300,100));
		this.setBall(new Ball());

		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:
					case KeyEvent.VK_LEFT:
						getPlayer().startMovingLeft();
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						getPlayer().startMovingRight();
						break;
					case KeyEvent.VK_SPACE:
						System.out.println("Touche espace pressée");
						break;
					case KeyEvent.VK_ESCAPE:
						System.out.println("Touche échap pressée");
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						getPlayer().stopMovingLeft();
						break;
					case KeyEvent.VK_RIGHT:
						getPlayer().stopMovingRight();
						break;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		};
		// Make the panel focusable so it can listen to key inputs
		this.getPanel().requestFocus();
		this.getPanel().addKeyListener(keyListener);
	}


	/**
	 * Get the list of bricks in the game.
	 * 
	 * @return The list of bricks
	 */
	public ArrayList<Entity> getBricks() {
		return this.bricks;
	}

	/**
	 * Set the list of bricks in the game.
	 * 
	 * @param bricks The list of bricks
	 */
	public void setBricks(ArrayList<Entity> bricks) {
		this.bricks = bricks;
	}

	/**
	 * Get the player entity in the game.
	 * 
	 * @return The player entity
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Set the player entity in the game.
	 * 
	 * @param player The player entity
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Get the ball entity in the game.
	 * 
	 * @return The ball entity
	 */
	public Ball getBall() {
		return this.ball;
	}

	/**
	 * Set the ball entity in the game.
	 * 
	 * @param ball The ball entity
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}

	/**
	 * @see game.rules.Game#start()
	 */
	@Override
	public void start() {
		super.start();

		// Add all entities to the game
		for (Entity brick : this.getBricks()) {
			this.getPanel().add(brick.getRepresentation());
		}
		this.getPanel().add(this.getPlayer().getRepresentation());
		this.getPanel().add(this.getBall().getRepresentation());
	}

	/**
	 * @see game.rules.Game#update()
	 */
	@Override
	public void update() {
		super.update();
		this.getPlayer().update();
		this.getBall().update(player);

		// TODO Update game logic
	}

	/**
	 * @see game.rules.Game#render()
	 */
	@Override
	public void render() {
		for (Entity brick : this.getBricks()) {
			brick.getRepresentation().repaint();
		}
		
		this.getPlayer().getRepresentation().repaint();
		this.getBall().getRepresentation().repaint();
	}
}
