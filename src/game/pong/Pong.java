package game.pong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import display.engine.PhysicsEngine;
import display.engine.utils.Vector2D;
import game.pong.entities.Player;
import game.pong.entities.Ball;
import game.pong.entities.Wall;
import game.rules.Game;

import java.awt.Dimension;
import java.awt.Toolkit;

import display.view.*;
import display.view.brickbreakerview.GamePanel;

/**
 * The `Pong` class represents the Pong game. It extends the `Game` class and contains
 * the game logic, entities, and physics engine. It handles player input, ball movement,
 * scoring, and collision detection with walls. The game is displayed in a `GameFrame`.
 * 
 * The `Pong` class provides methods to start the game, update the game state, reset the score,
 * check for collisions with walls, update player positions, and clear game components.
 * 
 * The game is played by two players who control paddles on opposite sides of the screen.
 * The objective is to hit the ball with the paddle and prevent it from hitting the walls
 * behind the paddles. Each time the ball hits a wall behind the opponent's paddle, the
 * corresponding player's score increases. The game ends when one player reaches a certain
 * score limit.
 * 
 * The `Pong` class also provides getters and setters for accessing and modifying game
 * components such as players, ball, and score.
 */
public class Pong extends Game{
	
	public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static double ratio = Scale.getRatioForResolution(SCREEN_FULL_SIZE.getWidth(), SCREEN_FULL_SIZE.getHeight());
    int scale = (int) (SCREEN_FULL_SIZE.getWidth() / SCREEN_FULL_SIZE.getHeight() * ratio);

	// Path to the assets folder
	public final static String ASSETS_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator 
	+ "game" + File.separator + "pong" + File.separator + "assets" + File.separator;
	GameFrame gameframe;

	// Entities
	private Player player0, player1;
	private int score0, score1;
	private Ball ball;
	private Wall eastWall, northWall, westWall, southWall;
	private static final int WALL_WIDTH = 20;

	// Physics engine
	private PhysicsEngine physicEngine = new PhysicsEngine();
	
	/**
	 * Instantiates a new SpaceInvader game
	 * 
	 * @param gameFrame The frame in which the game is displayed
	 */
	public Pong(GameFrame gameFrame) {
		// to set the title of the game
		super(gameFrame.getGamePanel(), "Pong");
		this.gameframe = gameFrame;
		this.gameframe.setGame(this);
		// to set the entities that will be needed for the game
		this.setPlayer0(new Player(Player.DEFAULT_IMAGE, Player.DEFAULT_SIZE,Player.DEFAULT_SPEED, 0,new Vector2D(WALL_WIDTH + 10, (int)(ratio * 20)),false));
		this.setPlayer1(new Player(Player.DEFAULT_IMAGE, Player.DEFAULT_SIZE,Player.DEFAULT_SPEED, 0,new Vector2D((int)GamePanel.GAME_ZONE_SIZE.getWidth() - 2*WALL_WIDTH - 10, (int)(ratio * 20)),false));
		Ball ball = new Ball(game.pong.entities.Ball.DEFAULT_IMAGE,  30, 50,new Vector2D((int)(ratio * 30),(int)(ratio * 20)),true);
		ball.setRotationCoeff(0.5);
		// to set the ball
		this.setBall(ball);
		ball.active=false;
		// to set the walls of the game which would be used as delimitation borders
		this.setEastWall(new Wall(WALL_WIDTH, 800, 100,new Vector2D((int)GamePanel.GAME_ZONE_SIZE.getWidth()-WALL_WIDTH, 0),false, true));
		this.setWestWall(new Wall(WALL_WIDTH, 800,100,new Vector2D(0, 0),false, true));
		this.setNorthWall(new Wall((int)GamePanel.GAME_ZONE_SIZE.getWidth(), WALL_WIDTH,100,new Vector2D(0, 0),false, false));
		this.setSouthWall(new Wall((int)GamePanel.GAME_ZONE_SIZE.getWidth(), WALL_WIDTH,100,new Vector2D(0, 800),false, false));
		// to add the entities to the physic engine
		this.physicEngine.getPhysicalObjects().add(ball);
		this.physicEngine.getPhysicalObjects().add(northWall);
		this.physicEngine.getPhysicalObjects().add(player0);
		this.physicEngine.getPhysicalObjects().add(player1);
		this.physicEngine.getPhysicalObjects().add(eastWall);
		this.physicEngine.getPhysicalObjects().add(westWall);
		this.physicEngine.getPhysicalObjects().add(southWall);

		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					// reset key
					case KeyEvent.VK_X:
						// if the ball is active 
						if (Pong.this.getBall().active){
							// the reset key will just reset its position
							//Pong.this.getBall().deleteBall(Pong.this);
							Pong.this.getBall().deleteEntity(Pong.this);
							Pong.this.getBall().respawnBall(Pong.this, (int)(ratio * 30),(int)(ratio * 20));
						}
						// if the ball is not active but still the reset button is pressed
						else { 
							// the reset will go further and reset the score
							Pong.this.resetScore();
						}
						break;
					// movement keys
					case KeyEvent.VK_Z:
						Pong.this.getPlayer0().moveUp();
						break;
					case KeyEvent.VK_UP:
						Pong.this.getPlayer1().moveUp();
						break;
					case KeyEvent.VK_S:
						Pong.this.getPlayer0().moveDown();
						break;
					case KeyEvent.VK_DOWN:
						Pong.this.getPlayer1().moveDown();
						break;
					case KeyEvent.VK_P:
						if(Pong.this.isPaused()){
							Pong.this.resume();
						}
						else{
							Pong.this.pause();
						}
						break;
					// space key to start the game
					case KeyEvent.VK_SPACE:
						if (!Pong.this.getBall().active){
							//SpaceInvader.this.getBall().setIsMoving(true);
							Pong.this.getBall().active=true;
							Vector2D newPosition = new Vector2D(Pong.this.getBall().getRepresentation().getX(), Pong.this.getBall().getRepresentation().getY());
							Pong.this.getBall().setPosition(newPosition);
							// random that will choose 1 or -1
							Vector2D speed = new Vector2D(0.6, 0);
							Pong.this.getBall().setSpeed(speed);
						}
						break;
					// M key to go to the menu
					case KeyEvent.VK_M:
						if (!Pong.this.gameframe.getPongGame().isPaused()){
							Pong.this.pause();
							Pong.this.gameframe.getGamePanel().getGameZone().setVisible(false);
							Pong.this.gameframe.getGamePanel().getMenu().setVisible(true);
						}
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Z:
						Pong.this.getPlayer0().stopUp();
						break;
					case KeyEvent.VK_UP:
						Pong.this.getPlayer1().stopUp();
						break;
					case KeyEvent.VK_S:
						Pong.this.getPlayer0().stopDown();
						break;
					case KeyEvent.VK_DOWN:
						Pong.this.getPlayer1().stopDown();
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
	 * @see game.rules.Game#start()
	 */
	@Override
	public void start() {
		super.start();
		// Add all entities to the game
		this.getPanel().getGameZone().add(this.getEastWAll().getRepresentation());
		this.getPanel().getGameZone().add(this.getWestWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getNorthWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getSouthWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getPlayer1().getRepresentation());
		this.getPanel().getGameZone().add(this.getPlayer0().getRepresentation());
		this.getPanel().getGameZone().add(this.getBall().getRepresentation());
		this.getPanel().updateScore(this.getscore0(), this.getscore1());
		/*  for test purposes, may be useful later
		int resolutionRatio = Scale.getRatioForResolution();
		System.out.println("Resolution Ratio: " + resolutionRatio);
		*/
	}

	/**
	 *  Reset both of the score
	 */
	public void resetScore(){
		this.setScore0(0);
		this.setScore1(0);
		this.getPanel().updateScore(this.getscore0(), this.getscore1());
	}

	/**
	 * Check if the player's ammo is colliding with an enemy
	 * 
	 * @return boolean true if the player's ammo is colliding with an enemy
	 */
	public boolean collidingWithWall(){
		return this.getBall().collidingWithWall();
	}

	/**
	 * Check if the player's ammo is still in the game zone
	 */
	public void updateBall(){ 
		if (collidingWithWall()){
			// if the ball is colliding with the east wall
			if (this.getBall().getRepresentation().getX() > 2*WALL_WIDTH){
				this.setScore0(this.getscore0()+1);
			}
			else{
				this.setScore1(this.getscore1()+1);
			}
			this.getBall().deleteEntity(this);
			this.getBall().respawnBall(this, (int)(ratio * 30),(int)(ratio * 20));
			this.getPanel().updateScore(this.getscore0(), this.getscore1());
		}
	}
	
	/** 
	 * update the player entity
	 * 
	 */
	public void updatePlayer(Player p) {
		// if the player is not going to be off screen
		if(!p.willBeOffScreen(this.getPanel(), p.getIntSpeed())){
			// update the player's position accordingly to the inputs put by the keys
			p.setLastPos(p.getPosition());
			p.move(p.getIntSpeed());
		}
		else{
			p.stopUp();
			p.stopDown();
		}
	}

	/**
	 * an unified update
	 * 
	 * @see game.rules.Game#onUpdate()
	 */
	@Override
	public void onUpdate(double deltaTime) {
		this.updatePlayer(this.getPlayer0());
		this.updatePlayer(this.getPlayer1());
		this.updateBall();
		this.physicEngine.update(deltaTime);
	}

	/**
	 * Clear all game components
	 */
	public void clearGameComponents() {
		// Remove the player from the game zone
		this.getPlayer0().deleteEntity(this);
		this.getPlayer1().deleteEntity(this);
		// Remove the Ball from the game zone
		this.getBall().deleteEntity(this);;
		// Remove the walls from the game zone
		this.getNorthWall().deleteEntity(this);
		this.getSouthWall().deleteEntity(this);
		this.getEastWAll().deleteEntity(this);
		this.getWestWall().deleteEntity(this);
	}

/* all the getters and setters */

	/**
	 *  Set the score for the first player
	 * 
	 * @return the score of the first player
	 */
	public void setScore0(int score){
		this.score0 = score;
	}

	/**
	 *  Set the score for the second player
	 * 
	 * @return the score of the second player
	 */
	public void setScore1(int score){
		this.score1 = score;
	}

	/**
	 * Get the player entity in the game.
	 * 
	 * @return The player entity
	 */
	public Player getPlayer1() {
		return this.player1;
	}

	/**
	 * Set the player entity in the game.
	 * 
	 * @param player The player entity
	 */
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	/**
	 * Get the player entity in the game.
	 * 
	 * @return The player entity
	 */
	public Player getPlayer0() {
		return this.player0;
	}

	/**
	 * Set the player entity in the game.
	 * 
	 * @param player The player entity
	 */
	public void setPlayer0(Player Player) {
		this.player0 = Player;
	}

	/**
	 * Get the Ball entity in the game.
	 * 
	 * @return The Ball entity
	 */
	public Ball getBall() {
		return this.ball;
	}

	/**
	 * Set the Ball entity in the game.
	 * 
	 * @param Ball The Ball entity
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}

	/**
	 * Get the width of the wall in the game.
	 * 
	 * @return The width of the wall
	 */
	public static int getWallWidth(){
		return WALL_WIDTH;
	}

	/**
	 * Get the east wall in the game.
	 * 
	 * @return The east wall
	 */
	public Wall getEastWAll(){
		return this.eastWall;
	}

	/**
	 * Set the east wall in the game.
	 * 
	 * @param wall The east wall
	 */
	public void setEastWall (Wall wall){
		this.eastWall = wall;
	}

	/**
	 * Get the west wall in the game.
	 * 
	 * @return The west wall
	 */
	public Wall getWestWall(){
		return this.westWall;
	}

	/**
	 * Set the west wall in the game.
	 * 
	 * @param wall The west wall
	 */
	public void setWestWall(Wall wall){
			this.westWall = wall;
	}

	/**
	 * Get the north wall in the game.
	 * 
	 * @return The north wall
	 */
	public Wall getNorthWall(){
		return this.northWall;
	}

	/**
	 * Set the north wall in the game.
	 * 
	 * @param wall The north wall
	 */
	public void setNorthWall(Wall wall){
		this.northWall =  wall;
	}

	/**
	 * Get the south wall in the game.
	 * 
	 * @return The south wall
	 */
	public Wall getSouthWall(){
		return this.southWall;
	}

	/**
	 * Set the south wall in the game.
	 * 
	 * @return wall The south wall
	 */
	public void setSouthWall(Wall wall){
		this.southWall = wall;
	}

	/**
	 * Get the score of the first player.
	 * 
	 * @return The life
	 */
	public int getscore0(){
		return 	this.score0;
	}

	/**
	 * Get the score of the second player.
	 * 
	 * @return The life
	 */
	public int getscore1(){
		return 	this.score1;
	}

	/**
	 * 
	 * Get the GameFrame of the game 
	 * 
	 * @return The GameFrame
	 */
	public GameFrame getGameFrame(){
		return this.gameframe;
	}

	/**
	 * Get the PhysicsEngine of the game
	 * 
	 * @return The PhysicsEngine
	 */
	public PhysicsEngine getPhysicEngine() {
		return physicEngine;
	}
	
}
