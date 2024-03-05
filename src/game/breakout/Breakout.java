package game.breakout;

import java.util.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import display.view.GameFrame;
import display.view.GamePanel;
import game.breakout.entities.Ball;
import game.breakout.entities.Player;
import game.breakout.entities.Wall;
import game.breakout.entities.Brick;
import game.rules.Game;

public class Breakout extends Game{
	public final static String ASSETS_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator 
	+ "game" + File.separator + "breakout" + File.separator + "assets" + File.separator;

	private ArrayList<Brick> bricks;
	private Player player;
	private Ball ball;
	private Wall eastWall, northWall, westWall;
	private static final int WALL_WIDTH = 20;
	private int nbBricks;
	private int score = 0;
	private int life = 3; // number of hearths when the game starts

	/**
	 * Instantiates a new Breakout game
	 * 
	 * @param gameFrame The frame in which the game is displayed
	 */
	public Breakout(GameFrame gameFrame) {
		super(gameFrame.getGamePanel(), "Breakout");
		this.setBricks(new ArrayList<Brick>());
		this.setPlayer(new Player(Player.DEFAULT_COLOR, 530,700, Player.DEFAULT_SIZE));
		this.setBall(new Ball(Ball.DEFAULT_COLOR, 565,668, 30));
		this.setEastWall(new Wall(0, 0, WALL_WIDTH, (int)GamePanel.GAME_ZONE_SIZE.getHeight()));
		this.setWestWall(new Wall((int)GamePanel.GAME_ZONE_SIZE.getWidth()-WALL_WIDTH, 0, WALL_WIDTH, (int)GamePanel.GAME_ZONE_SIZE.getHeight()));
		this.setNorthWall(new Wall(0, 0, (int)GamePanel.GAME_ZONE_SIZE.getWidth(), WALL_WIDTH));

		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:
					case KeyEvent.VK_LEFT:
						Breakout.this.getPlayer().moveLeft();
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						Breakout.this.getPlayer().moveRight();
						break;
					case KeyEvent.VK_ESCAPE:
						if(Breakout.this.isPaused()){
							Breakout.this.resume();
						}
						else{
							Breakout.this.pause();
						}
						break;
					case KeyEvent.VK_SPACE:
						if (!Breakout.this.getBall().getIsMoving()) Breakout.this.getBall().setIsMoving(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:
					case KeyEvent.VK_LEFT:
						Breakout.this.getPlayer().stopLeft();
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						Breakout.this.getPlayer().stopRight();
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
	public ArrayList<Brick> getBricks() {
		return this.bricks;
	}

	/**
	 * Set the list of bricks in the game.
	 * 
	 * @param bricks The list of bricks
	 */
	public void setBricks(ArrayList<Brick> bricks) {
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

	public static int getWallWidth(){
		return WALL_WIDTH;
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
	 * Get the score number in the game.
	 * 
	 * @return The score number
	 */
	public int getScore(){
		return this.score;
	}

	/**
	 * Get the bricks number in the game.
	 * 
	 * @return The bricks number 
	 */
	public int getNbBricks(){
		return this.nbBricks;
	}

	/**
	 * Get the life in the game.
	 * 
	 * @return The life
	 */
	public int getLife(){
		return 	this.life;
	}

	/**
	 * Initializes bricks in a level
	 * 
	 * @param rows The number of rows of bricks
	 * @param columns The number of columns of bricks
	 */
	public void createBricks(int rows, int columns){
		// TODO: Prevent the amount of bricks from exceeding the panel's width and height
		// See GraphicalObject#isOnScreen(x, y, panel)

		final int BRICK_SPACING = Brick.DEFAULT_WIDTH + 10;

		// Start the bricks at the center of the panel
		int initialXPos = (int) Math.floor(this.getPanel().getGameZone().getPreferredSize().getWidth()
		/ 2 - (columns * BRICK_SPACING) / 2);
		
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				int verticalPos = Brick.DEFAULT_POS_Y + row * (Brick.DEFAULT_HEIGHT + 10);
				int randomLifespan = new Random().nextInt(Brick.MAX_LIFESPAN);

				this.getBricks().add(new Brick(initialXPos+column*BRICK_SPACING,verticalPos,
				Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				randomLifespan, false));
			}
		}
	}

	/**
	 * @see game.rules.Game#start()
	 */
	@Override
	public void start() {
		super.start();
		this.createBricks(4, 8);
		this.nbBricks = this.bricks.size(); //initialize nbBricks withe the size of list bricks

		// Add all entities to the game
		for (Brick brick : this.getBricks()) {
			this.getPanel().getGameZone().add(brick.getRepresentation());
		}
		this.getPanel().getGameZone().add(this.getEastWAll().getRepresentation());
		this.getPanel().getGameZone().add(this.getWestWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getNorthWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getPlayer().getRepresentation());
		this.getPanel().getGameZone().add(this.getBall().getRepresentation());
		this.getBall().moveUp();
		this.getBall().moveRight();
	}

	/**
	 * Update the player entity
	 */
	public void updatePlayer() {
		if(!this.getPlayer().willBeOffScreen(this.getPanel(), Player.MOVE_SPEED)){
			if (!this.getBall().getIsMoving()){
				if(this.getPlayer().movingLeft()){

					this.getBall().getRepresentation().setPosX(this.getBall().getRepresentation().getPosX()-Player.MOVE_SPEED);

				}else if(this.getPlayer().movingRight()){

					this.getBall().getRepresentation().setPosX(this.getBall().getRepresentation().getPosX()+Player.MOVE_SPEED);

				}
			}
			this.getPlayer().move(Player.MOVE_SPEED);
		}
	}

	/**
	 * Update the ball entity
	 */
	public void updateBall() {
		if (this.getBall().getIsMoving()){
			int[] ballCurrPos = {this.getBall().getRepresentation().getPosX(),this.getBall().getRepresentation().getPosY()};
			int[] playerCurrPos = {this.getPlayer().getRepresentation().getPosX(),this.getPlayer().getRepresentation().getPosY()};
			int[] ballNextPos = this.getBall().getNextPos(Ball.MOVE_SPEED);
			int[] playerNextPos = this.getPlayer().getNextPos(Player.MOVE_SPEED);

			if(this.getBall().getRepresentation().isGoingToCollide(this.getPlayer().getRepresentation(), ballNextPos, playerNextPos)){
				int[] vector = {ballCurrPos[0]  - (playerNextPos[0] + this.getPlayer().getRepresentation().getWidth()/2), (playerNextPos[1] + this.getPlayer().getRepresentation().getWidth()/2) -   ballCurrPos[1] };
				double angle = Math.atan2(vector[1], vector[0]);
				double angleInDegrees = Math.toDegrees(angle);
				System.out.println(angleInDegrees);
				if ((int)(angleInDegrees) > 60) {
					this.getBall().reverseVerticalMomentum();
					System.out.println("Collision with top of the paddle");
				} else {    
					this.getBall().reverseHorizontalMomentum();
					this.getPlayer().stopRight();
					this.getPlayer().stopLeft();
					System.out.println("Collision with side of the paddle");
				}
			}

			if(this.getBall().willBeOffScreen(this.getPanel(), Ball.MOVE_SPEED)){
					this.getBall().reverseHorizontalMomentum();
			} else if (this.getBall().willLoose(panel, Ball.MOVE_SPEED)){
				// the ball respawn for the moment 
				this.getBall().moveUp();
				this.getBall().moveRight();
				this.getBall().getRepresentation().setPosX(this.getPlayer().getRepresentation().getPosX()+30);
				this.getBall().getRepresentation().setPosY(this.getPlayer().getRepresentation().getPosY()-this.getPlayer().getRepresentation().getHeight());
				this.getBall().setIsMoving(false);
				this.life--;
			}
			this.getBall().move(Ball.MOVE_SPEED);
		}
	}

	/**
	 * Update the bricks entities
	 */
	public void updateBricks() {
		// Using an iterator to safely remove bricks from the collection
		// Without getting the ConcurrentModificationException
		Iterator<Brick> iterator = this.getBricks().iterator();

		while (iterator.hasNext()) {
			Brick brick = iterator.next();
			if (brick.getRepresentation().isColliding(this.getBall().getRepresentation())) {
				//this.getBall().reverseHorizontalMomentum();
				this.getBall().reverseVerticalMomentum();          
				if (brick.getLifespan()-1 < Brick.MIN_LIFESPAN) {
					this.getPanel().getGameZone().remove(brick.getRepresentation());
					this.nbBricks--; // Decrement the count of brick when the brick is broken
					this.score += 100; // Incremen the score when the brick is broken
					// Safely remove the brick from the collection
					iterator.remove(); 
				}
				else{
					brick.setLifespan(brick.getLifespan() - 1);
					this.score += 10; // Increment the score when the brick is touched
				}
				// Break the loop to prevent the ball from colliding with multiple bricks
				// and avoid the multiple reverseDirection() calls (making the ball continue in the same direction)
				break;
			}
		}
	}

	/**
	 * @see game.rules.Game#onUpdate()
	 */
	@Override
	public void onUpdate() {
		this.updatePlayer();
		this.updateBall();
		this.updateBricks();
		//this.getPanel().updateStat(this.score, this.life, this.nbBricks); // update JLabel of statZone in GamePanel 
	}
}
