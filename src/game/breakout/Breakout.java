package game.breakout;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import display.view.GameFrame;
import display.view.GamePanel;
import game.breakout.entities.Ball;
import game.breakout.entities.Player;
import game.breakout.entities.Ball.DirectionBall;
import game.breakout.entities.Wall;

import game.breakout.entities.Brick;
import game.breakout.entities.rules.Entity;
import game.breakout.entities.rules.Entity.Direction;
import game.rules.Game;
import physics.PhysicsEngine;
import physics.PhysicalObject;
import physics.utils.Vector2D;

public class Breakout extends Game{
	public final static String ASSETS_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator 
	+ "game" + File.separator + "breakout" + File.separator + "assets" + File.separator;

	private ArrayList<Brick> bricks;
	private Player player;
	private Ball ball;
	private Wall eastWall, northWall, westWall;
	private static final int WALL_WIDTH = 20;
	private PhysicalObject<Entity> physicalBall;
	private PhysicalObject<Entity> physicalPlayer;
	private PhysicalObject<Entity> physicalEastWall;
	private PhysicalObject<Entity> physicalNorthWall;
	private PhysicalObject<Entity> physicalWestWall;
	private ArrayList<PhysicalObject<Entity>> physicalBricks = new ArrayList<>();
	private PhysicsEngine<Entity> physicEngine = new PhysicsEngine<>();



	/**
	 * Instantiates a new Breakout game
	 * 
	 * @param gameFrame The frame in which the game is displayed
	 */
	public Breakout(GameFrame gameFrame) {
		super(gameFrame.getGamePanel(), "Breakout");
		this.setBricks(new ArrayList<Brick>());
		this.setPlayer(new Player(Player.DEFAULT_COLOR, 630,700, Player.DEFAULT_SIZE));
		Vector2D playerVectPos = new Vector2D(630, 700);
		this.physicalPlayer = new PhysicalObject<Entity>(player, 5, playerVectPos, false, player.getRepresentation());
		this.setBall(new Ball(Ball.DEFAULT_COLOR, 630,600, 30));
		Vector2D ballVectPos = new Vector2D(630, 600);
		this.physicalBall = new PhysicalObject<Entity>(ball, 2, ballVectPos, true, ball.getRepresentation());
		this.setEastWall(new Wall(0, 0, WALL_WIDTH, (int)GamePanel.SCREEN_FULL_SIZE.getHeight()));
		Vector2D VectEastWallPos = new Vector2D(0, 0);
		this.physicalEastWall = new PhysicalObject<Entity>(eastWall, 100,VectEastWallPos , false, eastWall.getRepresentation());
		this.setWestWall(new Wall((int)GamePanel.SCREEN_FULL_SIZE.getWidth()-WALL_WIDTH, 0, WALL_WIDTH, (int)GamePanel.SCREEN_FULL_SIZE.getHeight()));
		Vector2D VectWestWallPos = new Vector2D((int)GamePanel.SCREEN_FULL_SIZE.getWidth()-WALL_WIDTH, 0);
		this.physicalWestWall = new PhysicalObject<Entity>(eastWall, 100, VectWestWallPos, false, westWall.getRepresentation());
		this.setNorthWall(new Wall(0, 0, (int)GamePanel.SCREEN_FULL_SIZE.getWidth(), WALL_WIDTH));
		this.physicalNorthWall = new PhysicalObject<Entity>(eastWall, 100, VectEastWallPos, false, northWall.getRepresentation());
		this.physicEngine.getPhysicalObjects().add(physicalBall);


		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:
					case KeyEvent.VK_LEFT:
						Breakout.this.getPlayer().setDirection(Direction.LEFT);
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						Breakout.this.getPlayer().setDirection(Direction.RIGHT);
						break;
					case KeyEvent.VK_ESCAPE:
						if(Breakout.this.isPaused()){
							Breakout.this.resume();
						}
						else{
							Breakout.this.pause();
						}
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:
					case KeyEvent.VK_LEFT:
						Breakout.this.getPlayer().setDirection(Direction.NONE);
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						Breakout.this.getPlayer().setDirection(Direction.NONE);
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
		int initialXPos = (int) Math.floor(this.getPanel().getPreferredSize().getWidth()
		/ 2 - (columns * BRICK_SPACING) / 2);
		
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				int verticalPos = Brick.DEFAULT_POS_Y + row * (Brick.DEFAULT_HEIGHT + 10);
				int randomLifespan = new Random().nextInt(Brick.MAX_LIFESPAN);

				this.getBricks().add(new Brick(initialXPos+column*BRICK_SPACING,verticalPos,
				Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				randomLifespan, false));
				Brick brick = new Brick(initialXPos+column*BRICK_SPACING,verticalPos,
				Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				randomLifespan, false);
				Vector2D brickVectPos = new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos);
				PhysicalObject<Entity> physicalBrick = new PhysicalObject<Entity>(brick, 10, brickVectPos, false, brick.getRepresentation());
				this.physicalBricks.add(physicalBrick);
			}
		}
		for (PhysicalObject<Entity> brick:physicalBricks){
			this.physicEngine.getPhysicalObjects().add(brick);
		} 
	}

	/**
	 * @see game.rules.Game#start()
	 */
	@Override
	public void start() {
		super.start();
		this.createBricks(6, 10);

		// Add all entities to the game
		for (Brick brick : this.getBricks()) {
			this.getPanel().add(brick.getRepresentation());
		}
		this.getPanel().add(this.getPlayer().getRepresentation());
		this.getPanel().add(this.getBall().getRepresentation());
		this.getPanel().add(this.getEastWAll().getRepresentation());
		this.getPanel().add(this.getWestWall().getRepresentation());
		this.getPanel().add(this.getNorthWall().getRepresentation());
		this.getBall().setDirectionBall(DirectionBall.UP_RIGHT);
	}

	/**
	 * Update the player entity
	 */
	public void updatePlayer() {
		if(!this.getPlayer().willBeOffScreen(this.getPanel(), Player.MOVE_SPEED)){
			this.getPlayer().move(Player.MOVE_SPEED);

		}
	}

	/**
	 * Update the ball entity
	 */
	public void updateBall() {
		if(this.getBall().willBeOffScreen(this.getPanel(), Ball.MOVE_SPEED)
		|| this.getBall().getRepresentation().isColliding(this.getPlayer().getRepresentation())){
			this.getBall().reverseDirectionBall(this.getPanel(), Ball.MOVE_SPEED);
		}
		else if (this.getBall().willLoose(panel, Ball.MOVE_SPEED)){
			// the ball respawn for the moment 
			this.getBall().setDirectionBall(DirectionBall.UP_RIGHT);
			this.getBall().getRepresentation().setPosX(630);
			this.getBall().getRepresentation().setPosY(600);
		}
		this.getBall().move(Ball.MOVE_SPEED);
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
				this.getBall().reverseDirectionBall(this.getPanel(), Ball.MOVE_SPEED);
				if (brick.getLifespan()-1 < Brick.MIN_LIFESPAN) {
					this.getPanel().remove(brick.getRepresentation());
					// Safely remove the brick from the collection
					iterator.remove(); 
				}
				else{
					brick.setLifespan(brick.getLifespan() - 1);
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
	public void onUpdate(double deltaTime) {
		this.updatePlayer();
		//this.updateBall();
		this.updateBricks();
		this.physicEngine.update(deltaTime);

	}
}
