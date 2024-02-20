package game.breakout;

import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Dimension; 
import java.awt.Toolkit;
import display.view.GameFrame;
import display.view.GamePanel;
import game.breakout.entities.Ball;
import game.breakout.entities.Player;
import game.breakout.entities.Wall;
import game.breakout.entities.Brick;
import game.breakout.entities.rules.Entity;
import game.rules.Game;

public class Breakout extends Game{
	private ArrayList<Entity> bricks;
	private Player player;
	private Ball ball;
	private Wall eastWall, northWall, westWall;
	private static final int WALL_WIDTH = 20;

	/**
	 * Instantiates a new Breakout game
	 * 
	 * @param gameFrame The frame in which the game is displayed
	 */
	public Breakout(GameFrame gameFrame) {
		super(gameFrame.getGamePanel(), "Breakout");
		this.bricks = new ArrayList<Entity>();
		this.setPlayer(new Player(630,700));
		this.setBall(new Ball(630,700, 30, Color.CYAN));
		this.setEastWall(new Wall(0, 0, WALL_WIDTH, (int)GamePanel.SCREEN_FULL_SIZE.getHeight()));
		this.setWestWall(new Wall((int)GamePanel.SCREEN_FULL_SIZE.getWidth()-WALL_WIDTH, 0, WALL_WIDTH, (int)GamePanel.SCREEN_FULL_SIZE.getHeight()));
		this.setNorthWall(new Wall(0, 0, (int)GamePanel.SCREEN_FULL_SIZE.getWidth(), WALL_WIDTH));

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

	public Wall getEastWAll(){
		return this.eastWall;
	}

	public void setEastWall (Wall wall){
		this.eastWall = wall;
	}

	public Wall getWestWall(){
		return this.westWall;
	}

	public void setWestWall(Wall wall){
			this.westWall = wall;
	}

	public Wall getNorthWall(){
		return this.northWall;
	}

	public void setNorthWall(Wall wall){
		this.northWall =  wall;
	}

	/**
	 * Initializes bricks in a level
	 * 
	 * @param numberOfBrick the number of bricks to initialize
	 */
	public void bricksInitialisation(int numberOfBrick){
		int width = 100; //width of each bricks 
		int height = 20; //height of each bricks
		int verticalPos = 200; // Initial vertical position of the first brick in a row of bricks
		int widthPos = 1; // initiali horizontal position of the first brick in a row of bricks
		int spaceBeforeBorderScreenSide = 200;

		// Get screen size 
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 Dimension screenSize = toolkit.getScreenSize();
		 int screenWidth = (int) screenSize.getWidth(); 

		Random random = new Random(); // random generator for lifespans
		
		for(int i = 1; i < numberOfBrick+1; i++){
			int randomLife = random.nextInt(4); // generate random number between 0 and 3
			if (widthPos*110 >= screenWidth - spaceBeforeBorderScreenSide){ //check if the next brick will go beyon the screen size
				verticalPos += 30; // Move to the next row 
				widthPos = 1; //reset horizental pose to the next row
			}
			this.bricks.add(new Brick(widthPos*110,verticalPos,width,height,randomLife,false)); //create new brick 
			widthPos++; // Move to the next horizontal position for the next brick
		}
	}


	/**
	 * @see game.rules.Game#start()
	 */
	@Override
	public void start() {
		super.start();
		//for fun to see that initialisation can change for each level 
		Random random = new Random();
		int randomNumberOfBrick = random.nextInt(50 - 20) + 20;
		this.bricksInitialisation(randomNumberOfBrick);

		// Add all entities to the game
		for (Entity brick : this.getBricks()) {
			this.getPanel().add(brick.getRepresentation());
		}
		this.getPanel().add(this.getPlayer().getRepresentation());
		this.getPanel().add(this.getBall().getRepresentation());
		this.getPanel().add(this.getEastWAll().getRepresentation());
		this.getPanel().add(this.getWestWall().getRepresentation());
		this.getPanel().add(this.getNorthWall().getRepresentation());
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
		for (Entity b : bricks){
			if (b instanceof Brick && ((Brick)b).isDestroyed()){
				this.bricks.remove(b);
			}
		}
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
