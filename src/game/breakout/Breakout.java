package game.breakout;

import java.util.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import display.view.GameFrame;
import display.view.GamePanel;
import game.breakout.entities.Ball;
import game.breakout.entities.Bonus;
import game.breakout.entities.Player;
import game.breakout.entities.Wall;
import game.breakout.entities.Brick;
import game.breakout.entities.rules.Entity;
import game.rules.Game;
import physics.PhysicsEngine;
import physics.PhysicalObject;
import physics.utils.Vector2D;

public class Breakout extends Game{
	public final static String ASSETS_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator 
	+ "game" + File.separator + "breakout" + File.separator + "assets" + File.separator;

	GameFrame gameframe;

	private ArrayList<Brick> bricks;
	private ArrayList<Bonus> bonuses;
	private Player player;
	private Ball ball;
	private Wall eastWall, northWall, westWall;
	private static final int WALL_WIDTH = 20;
	private int nbBricks;
	private int score = 0;
	private int life = 3; // number of hearths when the game starts
	private PhysicalObject<Entity> physicalBall;
	private PhysicalObject<Entity> physicalPlayer;
	private PhysicalObject<Entity> physicalEastWall;
	private PhysicalObject<Entity> physicalNorthWall;
	private PhysicalObject<Entity> physicalWestWall;
	private ArrayList<PhysicalObject<Entity>> physicalBricks = new ArrayList<>();
	private PhysicsEngine<Entity> physicEngine = new PhysicsEngine<>();
	private double forceX;



	/**
	 * Instantiates a new Breakout game
	 * 
	 * @param gameFrame The frame in which the game is displayed
	 */
	public Breakout(GameFrame gameFrame) {
		super(gameFrame.getGamePanel(), "Breakout");
		this.gameframe = gameFrame;
		this.gameframe.setGame(this);
		this.setBricks(new ArrayList<Brick>());
		this.setPlayer(new Player(Player.DEFAULT_COLOR, 630,700, Player.DEFAULT_SIZE));
		Vector2D playerVectPos = new Vector2D(630, 700);
		this.physicalPlayer = new PhysicalObject<Entity>(player, 51, playerVectPos, false, player.getRepresentation());
		this.setBall(new Ball(Ball.DEFAULT_COLOR, 350,400, 30));
		Vector2D ballVectPos = new Vector2D(350, 400);
		this.physicalBall = new PhysicalObject<Entity>(ball, 50, ballVectPos, true, ball.getRepresentation());
		Vector2D speed = new Vector2D(0.1, 0.1);
		this.physicalBall.setSpeed(speed);
		this.setEastWall(new Wall(0, 0, WALL_WIDTH, (int)GamePanel.GAME_ZONE_SIZE.getHeight()));
		Vector2D VectEastWallPos = new Vector2D(0, 0);
		this.physicalEastWall = new PhysicalObject<Entity>(eastWall, 100,VectEastWallPos , false, eastWall.getRepresentation());
		this.setWestWall(new Wall((int)GamePanel.GAME_ZONE_SIZE.getWidth()-WALL_WIDTH, 0, WALL_WIDTH, (int)GamePanel.GAME_ZONE_SIZE.getHeight()));
		Vector2D VectWestWallPos = new Vector2D((int)GamePanel.GAME_ZONE_SIZE.getWidth()-WALL_WIDTH, 0);
		this.physicalWestWall = new PhysicalObject<Entity>(westWall, 100, VectWestWallPos, false, westWall.getRepresentation());
		this.setNorthWall(new Wall(0, 0, (int)GamePanel.GAME_ZONE_SIZE.getWidth(), WALL_WIDTH));
		this.physicalNorthWall = new PhysicalObject<Entity>(northWall, 100, VectEastWallPos, false, northWall.getRepresentation());
		this.physicEngine.getPhysicalObjects().add(physicalBall);
		this.physicEngine.getPhysicalObjects().add(physicalPlayer);
		this.physicEngine.getPhysicalObjects().add(physicalEastWall);
		this.physicEngine.getPhysicalObjects().add(physicalWestWall);
		this.physicEngine.getPhysicalObjects().add(physicalNorthWall);		


		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:
					case KeyEvent.VK_LEFT:
						Breakout.this.getPlayer().moveLeft();
						forceX=-1;
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						Breakout.this.getPlayer().moveRight();
						forceX=1;
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
	 * Get the list of bonuses in the game.
	 * 
	 * @return The list of bonuses
	 */
	public ArrayList<Bonus> getBonuses(){
		return this.bonuses;
	}

	/**
	 *  Set the list of bonuses in the game.
	 * 
	 *  @param bonuses The list of bonuses
	 */
	public void setBonuses(ArrayList<Bonus> bonuses){
		this.bonuses=bonuses;
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

				// Generate a random number between 1 and 3
				int randomNumber = new Random().nextInt(4) + 1;
				boolean dropBonus = (randomNumber == 1);
	
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
	 * Create a bonus at a given position (posX is the center of the bonus, however y is the top)
	 * 
	 * @param posX
	 * @param posY
	 */
	public void createBonus(int posX, int posY){
	
		// Get a random between 0 and the last number of the hashmap 
		int randomBonusType = new Random().nextInt(Bonus.MAX_BONUSTYPE);
		this.getBonuses().add(new Bonus(posX - Bonus.DEFAULT_SIZE/2, posY, Bonus.DEFAULT_SIZE, randomBonusType));
		for (Bonus bonus : this.getBonuses()) {
			this.getPanel().getGameZone().add(bonus.getRepresentation());
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

		this.getPanel().updateScore(this.score, this.nbBricks);
		this.getPanel().updateLife(this.life);

	}

	/**
	 * Update the player entity
	 */
	public void updatePlayer() {
		if(!this.getPlayer().willBeOffScreen(this.getPanel(), this.getPlayer().getRepresentation().getSpeed())){
			if (!this.getBall().getIsMoving()){

				if(this.getPlayer().movingLeft()){

					this.getBall().getRepresentation().setPosX(this.getBall().getRepresentation().getPosX()-this.getPlayer().getRepresentation().getSpeed());

				}else if(this.getPlayer().movingRight()){

					this.getBall().getRepresentation().setPosX(this.getBall().getRepresentation().getPosX()+this.getPlayer().getRepresentation().getSpeed());

				}
			}
			this.getPlayer().move(Player.MOVE_SPEED);
			this.physicalPlayer.setPosition(new Vector2D(this.getPlayer().getCurrPos(Player.MOVE_SPEED)[0], this.getPlayer().getCurrPos(Player.MOVE_SPEED)[1]));
		}
	}

	/**
	 * Update the ball entity
	 */
	public void updateBall() {
		if (this.getBall().getIsMoving()){
			int[] playerCurrPos = this.getPlayer().getCurrPos(this.getPlayer().getRepresentation().getSpeed());

			Vector2D paddleToBallVector = this.getPlayer().getRepresentation().vectorFromCenterToCenter(this.getBall().getRepresentation());
			Vector2D paddleToTopLeftCornerVector = this.getPlayer().getRepresentation().vectorCenterToCoordinates(playerCurrPos[0], playerCurrPos[1]); //the vector from the paddle's center to its top left corner
			Vector2D paddleToTopRightCornerVector = this.getPlayer().getRepresentation().vectorCenterToCoordinates(playerCurrPos[0]+this.getPlayer().getRepresentation().getWidth(), playerCurrPos[1]);
			
			
			if(this.getBall().getRepresentation().isGoingToCollide(this.getPlayer().getRepresentation(),
			 this.getBall().getNextPos(this.getBall().getRepresentation().getSpeed()),
			  this.getPlayer().getNextPos(this.getPlayer().getRepresentation().getSpeed()))){

				if (paddleToTopLeftCornerVector.angleBetween(paddleToBallVector)<0 && paddleToTopRightCornerVector.angleBetween(paddleToBallVector)>0) {
					this.getBall().reverseVerticalMomentum();

				} else {    
					this.getBall().reverseHorizontalMomentum();
					this.getPlayer().stopRight();
					this.getPlayer().stopLeft();
					
				}
			}

			if(this.getBall().willBeOffScreen(this.getPanel(), Ball.MOVE_SPEED)){
					
			} else if (this.getBall().willLoose(panel, Ball.MOVE_SPEED)){
				if( this.getLife() == 1 && this.getNbBricks() > 0){
					this.gameframe.getCardlayout().show(this.gameframe.getContainer(), "gameOver");
				}

				// the ball respawn for the moment 
				
				this.getBall().getRepresentation().setPosX(this.getPlayer().getRepresentation().getPosX()+(this.getPlayer().getRepresentation().getWidth()/2));
				this.getBall().getRepresentation().setPosY(this.getPlayer().getRepresentation().getPosY()-this.getPlayer().getRepresentation().getHeight() -(this.getBall().getRepresentation().getHeight()/2));
				this.getBall().moveUp();
				this.getBall().moveRight();
				this.getBall().setIsMoving(false);
				this.life--;
				this.getPanel().updateLife(this.life);
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
		Iterator<PhysicalObject<Entity>> physicalIterator = physicalBricks.iterator();

		if (this.nbBricks == 0 && this.life >= 0){
			this.gameframe.getCardlayout().show(this.gameframe.getContainer(), "winPanel");
		}

		while (iterator.hasNext() && physicalIterator.hasNext()) {
			Brick brick = iterator.next();
			PhysicalObject<Entity> entity = physicalIterator.next();
			if (brick.getRepresentation().isColliding(this.getBall().getRepresentation())) {
				//this.getBall().reverseHorizontalMomentum();
				this.getBall().reverseVerticalMomentum();          
				if (brick.getLifespan()-1 < Brick.MIN_LIFESPAN) {
					if (brick.doesDropBonus()){
						// store the size of the brick
						createBonus(brick.getRepresentation().getPosX() + brick.getRepresentation().getWidth()/2, brick.getRepresentation().getPosY());
					}
					this.getPanel().getGameZone().remove(brick.getRepresentation());
					this.getPanel().getGameZone().remove(entity.getRepresentation());
					this.nbBricks--; // Decrement the count of brick when the brick is broken
					this.score += 100; // Increment the score when the brick is broken
					// Safely remove the brick from the collection
					iterator.remove();
					physicalIterator.remove();
					this.getPanel().updateScore(this.score, this.nbBricks);
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
	 * Update the bonus entity
	 */
	public void updateBonus(){
		
		Iterator<Bonus> iterator = this.getBonuses().iterator();
		
		while(iterator.hasNext()){
			Bonus bonus = iterator.next();
			if (bonus.getRepresentation().isColliding(this.getPlayer().getRepresentation())){
				applyBonus(bonus.getbonusType());
				this.getPanel().getGameZone().remove(bonus.getRepresentation());
				iterator.remove();
			}
			else if (bonus.willBeOffScreen(this.getPanel(), Bonus.MOVE_SPEED)){
				this.getPanel().getGameZone().remove(bonus.getRepresentation());
				iterator.remove();
			}
			else{
				bonus.move(Bonus.MOVE_SPEED);
			}
		}
	}

	/**
	 * Apply the bonus to the game state, given a specific type of bonus
	 */
	public void applyBonus(int bonusType){
		switch(bonusType){
			case 0:
				if (Breakout.this.getPlayer().getRepresentation().getWidth() + (int)(0.1f*Player.DEFAULT_SIZE) <= Player.MAX_SIZE) {
					Breakout.this.getPlayer().getRepresentation().setWidth(Breakout.this.getPlayer().getRepresentation().getWidth() + (int)(0.1f*Player.DEFAULT_SIZE));
				}
				break; 
			case 1:
				if (Breakout.this.getPlayer().getRepresentation().getWidth() - (int)(0.1f*Player.DEFAULT_SIZE) >= Player.MIN_SIZE) {
					Breakout.this.getPlayer().getRepresentation().setWidth(Breakout.this.getPlayer().getRepresentation().getWidth() - (int)(0.1f*Player.DEFAULT_SIZE));
				}
				break;
			case 2:
				if (Breakout.this.getPlayer().getRepresentation().getSpeed() + (int)(0.2f*Player.DEFAULT_SPEED) <= Player.MAX_SPEED) {
					Breakout.this.getPlayer().getRepresentation().setSpeed(Breakout.this.getPlayer().getRepresentation().getSpeed() + (int)(0.2f*Player.DEFAULT_SPEED));
					System.out.println(Breakout.this.getPlayer().getRepresentation().getSpeed());
				}
				break;
			case 3:
				if (Breakout.this.getPlayer().getRepresentation().getSpeed() - (int)(0.1f*Player.DEFAULT_SPEED) >= Player.MIN_SPEED) {
					Breakout.this.getPlayer().getRepresentation().setSpeed(Breakout.this.getPlayer().getRepresentation().getSpeed() - (int)(0.1f*Player.DEFAULT_SPEED));
					System.out.println(Breakout.this.getPlayer().getRepresentation().getSpeed());
				}
				break;
			case 4:
				// TODO : once the timer is implemented after a graphical fix
				break;
			case 5:
				// default image
				break;
			default:
				break;
		}
	}

	/**
	 * @see game.rules.Game#onUpdate()
	 */
	@Override
	public void onUpdate(double deltaTime) {
		this.updatePlayer();
		this.updateBall();
		this.updateBricks();
		this.updateBonus();
		//this.getPanel().updateStat(this.score, this.life, this.nbBricks); // update JLabel of statZone in GamePanel 
		this.physicEngine.update(deltaTime);

	}
}
