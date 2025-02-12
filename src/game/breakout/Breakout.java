/**
 * The `Breakout` class represents a Breakout game. It extends the `Game` class and implements the `KeyListener` interface.
 * It contains methods for serializing and deserializing the game state, handling key events, and initializing the game objects.
 * The game consists of bricks, bonuses, balls, planets, a player, and walls. The player controls a paddle to bounce the ball and break the bricks.
 * The game is displayed in a `GameFrame` and rendered in a `GamePanel`.
 * 
 * @param gameFrame The frame in which the game is displayed
 * @param level The level of the game
 */
package game.breakout;

import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


import display.engine.PhysicsEngine;
import display.engine.shapes.Circle;

import display.engine.utils.Vector2D;
import display.view.GameFrame;
import display.view.Scale;
import display.view.brickbreakerview.*;
import game.breakout.entities.Ball;
import game.breakout.entities.Bonus;
import game.breakout.entities.Player;
import game.breakout.entities.Bonus.BonusType;
import game.breakout.entities.Wall;
import game.breakout.entities.Brick;
import game.breakout.entities.Planet;
import game.rules.Game;

import java.io.FileInputStream;

import java.io.ObjectInputStream;

import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.*;

public class Breakout extends Game {

	public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static double ratio = Scale.getRatioForResolution(SCREEN_FULL_SIZE.getWidth(), SCREEN_FULL_SIZE.getHeight());
    int scale = (int) (SCREEN_FULL_SIZE.getWidth() / SCREEN_FULL_SIZE.getHeight() * ratio);

	public static final long serialVersionUID = 15L;
	private boolean gameEnded;

	public final static String ASSETS_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator 
	+ "game" + File.separator + "breakout" + File.separator + "assets" + File.separator;

	transient public GameFrame gameframe;

	private ArrayList<Brick> bricks;
	private ArrayList<Bonus> bonuses;
	private ArrayList<Ball>  Balls;
	private ArrayList<Planet> planets;
	private Player player;
	private Ball ball;
	private Wall eastWall, northWall, westWall;
	private static final int WALL_WIDTH = 5; // fixed value, it's ok since it's just a thickness of 5
	
	// init at 1 because it takes time to initialize the list of bricks 
	// and it would lead us to the win panel directly
	private int nbBricks = 1; 
	private int score = 0;
	private int life = 3; 

	public PhysicsEngine getPhysicEngine() {
		return physicEngine;
	}

	private PhysicsEngine physicEngine = new PhysicsEngine();
	
	private int level = 0;
	private boolean trollLevel = false;

	/**
	 * Instantiates a new Breakout game
	 * 
	 * @param gameFrame The frame in which the game is displayed
	 */
	public Breakout(GameFrame gameFrame, int level) {
		super(gameFrame.getGamePanel(), "Breakout");
		this.level = level;
		this.gameframe = gameFrame;
		this.gameframe.setGame(this);
		this.setBricks(new ArrayList<Brick>());
		this.setBonuses(new ArrayList<Bonus>());
		this.setBalls(new ArrayList<Ball>());
		this.setPlanets(new ArrayList<Planet>());

		this.setPlayer(new Player(Player.DEFAULT_COLOR, Player.DEFAULT_SIZE,Player.DEFAULT_SPEED, 51,new Vector2D(530, 700),false));

		Ball mainBall = new Ball(Ball.DEFAULT_IMAGE2,  30,50,new Vector2D(565,670),true);
		this.setBall(mainBall);

		mainBall.active=false;
		this.getBalls().add(mainBall);
		
		this.setEastWall(new Wall(WALL_WIDTH, (int)GamePanel.GAME_ZONE_SIZE.getHeight(), 100,new Vector2D((int)GamePanel.GAME_ZONE_SIZE.getWidth()-WALL_WIDTH, 0),false));

		this.setWestWall(new Wall(WALL_WIDTH, (int)GamePanel.GAME_ZONE_SIZE.getHeight(),100,new Vector2D(0, 0),false));

		this.setNorthWall(new Wall((int)GamePanel.GAME_ZONE_SIZE.getWidth(), WALL_WIDTH,100,new Vector2D(0, 0),false));

		physicEngine.getPhysicalObjects().add(ball);
		physicEngine.getPhysicalObjects().add(player);
		physicEngine.getPhysicalObjects().add(eastWall);
		physicEngine.getPhysicalObjects().add(westWall);
		physicEngine.getPhysicalObjects().add(northWall);	

	
		this.addKeyListener();
	}

	/**
	 * Serializes the current state of the game when X is pressed
	 */
	public void writeToFile (ObjectOutputStream out) throws IOException {
		out.writeObject(this);
		out.close();
 	}

	/**
	 * handles deserializing the saved game state
	 */
	public static Breakout readFile(String saveName) throws IOException {
		Breakout game = null;

		try(FileInputStream in = new FileInputStream("src"+java.io.File.separator+"Saves"+ java.io.File.separator +saveName);
			ObjectInputStream s = new ObjectInputStream(in)) {
			game= (Breakout) s.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("couldn't find the class from save");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("couldn't find save");
			e.printStackTrace();
		}
		
		return game;
	}	

	/**
	 * handles setting up the game after deserialization, does what the constructor does when the game is normally initialized.
	 * calls load()
	 */
	public void setup(GamePanel gamePanel,int level, String title) {
		super.setPanel(gamePanel);
		super.getPanel().getFrame().setTitle(name);
		super.setName(name);
		super.setRenderedFrames(0);
		super.setCurrentFPS(0);
		super.setMaxFPS(DEFAULT_FPS);
		super.setVSync(true);


		this.load();
		this.level=level;


		this.addKeyListener();


	}



	/**
	 * reloads all objects in the game so that they appear properly after deserialization
	 */
	private void load(){
		this.getPanel().getGameZone().add(this.getPlayer().getRepresentation());

		for (Planet planete : this.getPlanets()){
			if(planete.isActive()) planete.getRepresentation().setImage(Planet.PLANET_IMAGE);
			this.getPanel().getGameZone().add(planete.getRepresentation());
		}
		
		this.getBall().getRepresentation().setImage(Ball.DEFAULT_IMAGE2);
		for (Ball ball : this.getBalls()){
			this.getPanel().getGameZone().add(ball.getRepresentation());
			ball.getRepresentation().repaint();
		}
		for (Brick brick : this.getBricks()){
			this.getPanel().getGameZone().add(brick.getRepresentation());
			brick.getRepresentation().setImage(Brick.lifespans.get(brick.getLifespan()));
		}
		for(Bonus bonus : this .getBonuses()){
			this.getPanel().getGameZone().add(bonus.getRepresentation());
			bonus.getRepresentation().setImage(Bonus.bonusTypes.get(bonus.getBonusType()));
		}
		this.getEastWall().getRepresentation().repaint();
		this.getWestWall().getRepresentation().repaint();
		this.getNorthWall().getRepresentation().repaint();
		this.getPanel().getGameZone().add(this.getEastWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getWestWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getNorthWall().getRepresentation());


	}


	public void writeObjects(String fileName) {
		try (FileOutputStream f = new FileOutputStream("src"+java.io.File.separator+"Saves"+ java.io.File.separator + fileName);
			ObjectOutputStream s = new ObjectOutputStream(f)) {
			this.writeToFile(s);
		} catch (IOException error) {
			error.printStackTrace();
		}
			System.out.println("Successfully saved Breakout!");
	}

	private void addKeyListener(){
		
		//remove all present key listeners to avoid conflicts 
		KeyListener[] keyListeners = this.getPanel().getKeyListeners();
		for (KeyListener keyListener : keyListeners) {
    		this.getPanel().removeKeyListener(keyListener);
		}
		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:break;
					case KeyEvent.VK_LEFT:
						if (Breakout.this.trollLevel) Breakout.this.getPlayer().moveRight();
						else Breakout.this.getPlayer().moveLeft();
						break;
					case KeyEvent.VK_D:
					{
						int ballDistanceX = Breakout.this.getBall().getRepresentation().getWidth();
						int ballDistanceY = Breakout.this.getBall().getRepresentation().getHeight();
						Random randomDistance = new Random();
						int randomX = (int)getBall().getPosition().getX() +  20+ballDistanceX;
						int randomY = (int)getBall().getPosition().getY() + 10+ballDistanceY;
						Vector2D ballPos = new Vector2D(randomX, randomY);
	
						Ball ball = new Ball(Ball.DEFAULT_COLOR, 20,20,ballPos,true);
						ball.setAcceleration(getBall().getAcceleration());
						ball.setSpeed(Breakout.this.getBall().getSpeed().add(new Vector2D(randomDistance.nextDouble(0.1), randomDistance.nextDouble(0.1))));
	
						getBalls().add(ball);
						getPanel().getGameZone().add(ball.getRepresentation());
						getPhysicEngine().getPhysicalObjects().add(ball);
						break;
					}
					case KeyEvent.VK_X: {
						BufferedImage screenShot = null;
						if (!Breakout.this.gameframe.getBreakoutGame().isPaused()){
							screenShot = screenShot();
							Breakout.this.pause();
							Breakout.this.gameframe.getGamePanel().getGameZone().setVisible(false);
						}
						String filename = JOptionPane.showInputDialog("Enter the filename to save:");
						if (filename != null) {
							try {
								ImageIO.write(screenShot, "JPG", new File("src"+java.io.File.separator+"SavesPics"+java.io.File.separator+filename+".jpg"));
							} catch (IOException ee) {
								System.out.println("couldn't take image");
							}

							writeObjects(filename + ".txt");
							Breakout.this.gameframe.getSavedGames().updateSaveFileNames();
						}
						Breakout.this.gameframe.getGamePanel().getGameZone().setVisible(true);
						Breakout.this.resume();
						break;
					}
					case KeyEvent.VK_RIGHT:
						if (Breakout.this.trollLevel) Breakout.this.getPlayer().moveLeft();
						else Breakout.this.getPlayer().moveRight();
						break;
					case KeyEvent.VK_P:
						if(Breakout.this.isPaused()){
							Breakout.this.resume();
						}
						else{
							Breakout.this.pause();
						}
						break;
					case KeyEvent.VK_V :
						Breakout.this.gameframe.setnbLevelUnlock();
						Breakout.this.endGame();
						Breakout.this.gameframe.getCardlayout().show(Breakout.this.gameframe.getPanelContainer(), "winPanel");
					case KeyEvent.VK_SPACE:
						if (!Breakout.this.getBall().active){
							//Breakout.this.getBall().setIsMoving(true);
							Breakout.this.getBall().active=true;
							Vector2D newPosition = new Vector2D(Breakout.this.ball.getRepresentation().getX(), Breakout.this.ball.getRepresentation().getY());
							Breakout.this.ball.setPosition(newPosition);
							Vector2D speed = new Vector2D(0.5, -0.5);
							Breakout.this.ball.setSpeed(speed);
						}
						break;
					case KeyEvent.VK_M:
						if (!Breakout.this.gameframe.getBreakoutGame().isPaused()){
							Breakout.this.pause();
							Breakout.this.gameframe.getGamePanel().getGameZone().setVisible(false);
							Breakout.this.gameframe.getGamePanel().getMenu().setVisible(true);
						}
						break;
					case KeyEvent.VK_R:
					if (Breakout.this.gameframe.getBreakoutGame().isPaused()){
						Breakout.this.resume();
						Breakout.this.gameframe.getGamePanel().getGameZone().setVisible(true);
						Breakout.this.gameframe.getGamePanel().getMenu().setVisible(false);
					}
					break;
						
				}
			}


			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:
					case KeyEvent.VK_LEFT:
						if (Breakout.this.trollLevel) Breakout.this.getPlayer().stopRight();
						else Breakout.this.getPlayer().stopLeft();
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						if (Breakout.this.trollLevel) Breakout.this.getPlayer().stopLeft();
						else Breakout.this.getPlayer().stopRight();
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
	 * Get the list of planets in the game.
	 * 
	 * @return The list of planets
	 */
	public ArrayList<Planet> getPlanets() {
		return this.planets;
	}

	/**
	 * Set the list of planets in the game.
	 * 
	 * @param planets The list of planets
	 */
	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}

	/**
	 * Get the list of bricks in the game.
	 * 
	 * @return The list of bricks
	 */
	public ArrayList<Ball> getBalls() {
		return this.Balls;
	}

	/**
	 * Set the list of bricks in the game.
	 * 
	 * @param bricks The list of bricks
	 */
	public void setBalls(ArrayList<Ball> ballz) {
		this.Balls = ballz;
	}

	/**
	 *  Set the life in the game.
	 * 
	 * @return life the life of the player
	 */
	public void setLife(int life){
		this.life = life;
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

	public Wall getEastWall(){
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
	 * Set the bricks number in the game
	 * 
	 * @param nbBricks the brick number
	 */
	public void setNbBricks(int nbBricks) {
		this.nbBricks = nbBricks;
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
	 * 
	 * Get the GameFrame of the game 
	 * 
	 * @return The GameFrame
	 */
	public GameFrame getGameFrame(){
		return this.gameframe;
	}


	public void setTrollLevel(boolean troll){
		this.trollLevel = troll;
	}


	
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
	
				Brick brick = new Brick(Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				randomLifespan, dropBonus, true, false, 10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);
				this.getBricks().add(brick);

				physicEngine.getPhysicalObjects().add(brick);
				this.getPanel().getGameZone().add(brick.getRepresentation()); 
			}
		}

	}
		
	/**
	 * Get the level in the game.
	 * 
	 * @return The level
	 */
	public int getLevel(){
		return 	this.level;
	}

	/**
	 * sets the level in the game.
	 */
	public void setLevel(int level){
		this.level = level;
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
		this.getBonuses().add(new Bonus(Bonus.bonusTypes.get(BonusType.values()[randomBonusType]), posX, posY, Bonus.DEFAULT_SIZE, BonusType.values()[randomBonusType]));
		for (Bonus bonus : this.getBonuses()) {
			bonus.getRepresentation().setBounds(posX, posY, bonus.getRepresentation().getWidth(), bonus.getRepresentation().getHeight());
			this.getPanel().getGameZone().add(bonus.getRepresentation());
		}
	}

	/**
	 * @see game.rules.Game#start()
	 */
	@Override
	public void start() {
		super.start();
		Level.level(this);
		this.nbBricks = this.bricks.size(); //initialize nbBricks withe the size of list bricks
		// Add all entities to the game
		for (Brick brick : this.getBricks()) {
			this.getPanel().getGameZone().add(brick.getRepresentation());
		}
		this.getPanel().getGameZone().add(this.getEastWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getWestWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getNorthWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getPlayer().getRepresentation());
		this.getPanel().getGameZone().add(this.getBall().getRepresentation());


		this.getPanel().updateLife(this.life);

	}

	/**
	 * Update the player entity
	 */
	public void updatePlayer() {
		if(!this.getPlayer().willBeOffScreen(this.getPanel(), this.getPlayer().getIntSpeed())){
			this.player.setLastPos(this.player.getPosition());
			this.getPlayer().move(this.getPlayer().getIntSpeed());
			
			if (!this.getBall().active){
				this.ball.getRepresentation().setPosX(this.player.getRepresentation().getX() + this.player.getRepresentation().getWidth()/3);				
			}
		}
		else{
			this.getPlayer().stopLeft();
			this.getPlayer().stopRight();
		}
	}


	 public void updateBall() {
		Circle trailPoint = new Circle(Color.RED, this.getBall().getRepresentation().getPosX()+(this.getBall().getRepresentation().getWidth()/2), this.getBall().getRepresentation().getPosY()+(this.getBall().getRepresentation().getHeight()/2), 10, 10);
		trailPoint.setBounds(trailPoint.getPosX(), trailPoint.getPosY(), trailPoint.getWidth(), trailPoint.getHeight());
		if(this.getBall().active) this.getBall().trail.addPoint(this);
			if( this.getLife() <=0 && this.getNbBricks() > 0){
				this.endGame();
				this.gameframe.getCardlayout().show(this.gameframe.getPanelContainer(), "gameOver");
			}
		}
	
	public void endGame(){
		this.gameEnded = true;
		this.clearGameComponents();
	}

	/**
	 * Update the planet entities
	 */
	public void updatePlanets() {
		
		Iterator<Planet> iterator = this.planets.iterator();
		while (iterator.hasNext()) {
			Planet planet = iterator.next();
			if (!planet.isActive()){
				this.getPhysicEngine().getPhysicalObjects().remove(planet);
				iterator.remove();
			}
		}
	}



	/**
	 * Update the bricks entities
	 */
	public void updateBricks() {
		// Using an iterator to safely remove bricks from the collection
		// Without getting the ConcurrentModificationException
		//System.out.println(nbBricks);
		//System.out.println(life);

		Iterator<Brick> iterator = this.getBricks().iterator();
	
		 
		if ((this.nbBricks == 0 && this.life >= 0) || (this.nbBricks == Level.unbreakableBrickNumber && this.life >= 0)){
			this.gameframe.setnbLevelUnlock();
			this.endGame();
			this.gameframe.getCardlayout().show(this.gameframe.getPanelContainer(), "winPanel");
		}


		while (iterator.hasNext()) {
			Brick brick = iterator.next();
			if (!brick.isActive()) {
				if (brick.doesDropBonus()){
					// store the size of the brick
					createBonus(brick.getRepresentation().getPosX() + brick.getRepresentation().getWidth()/2, brick.getRepresentation().getPosY());
				}
				this.nbBricks--; // Decrement the count of brick when the brick is broken
				this.score += 100; // Increment the score when the brick is broken
				// Safely remove the brick from the collection
				iterator.remove();
				
				
			}
			if (brick.isMoving()){
				if(brick.willBeOffScreen(this.getPanel(),5)){
					if(brick.movingRight()){
						brick.stopRight();
						brick.moveLeft();
					}else{
						brick.stopLeft();
						brick.moveRight();
					}
				}
				brick.move(1);
			}
		}
	}
	
	
	/**
	 * Update the bricks entities in the marathon game mode
	 */
	public void updateMarathonBricks() {
		// Using an iterator to safely remove bricks from the collection
		// Without getting the ConcurrentModificationException
		ListIterator<Brick> iterator = this.getBricks().listIterator();
		if(this.nbBricks <25){ // has to be here to not cause conflict with the iterator
			final int BRICK_SPACING = Brick.DEFAULT_WIDTH + 10;

			int rows =1;
			int columns = 8;
			// Start the bricks at the center of the panel
			int initialXPos = (int) Math.floor(this.getPanel().getGameZone().getPreferredSize().getWidth()
			/ 2 - (10 * BRICK_SPACING) / 2);
		
			for(int row = 0; row < rows; row++){
				for(int column = 0; column < columns; column++){
					int verticalPos = Brick.DEFAULT_POS_Y + row * (Brick.DEFAULT_HEIGHT + 10);
					int randomLifespan = new Random().nextInt(Brick.MAX_LIFESPAN);
	
					// Generate a random number between 1 and 3
					int randomNumber = new Random().nextInt(4) + 1;
					boolean dropBonus = (randomNumber == 1);
		
					Brick brick = new Brick(Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
					randomLifespan, dropBonus, false, false, 10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);
					iterator.add(brick);
					brick.moveRight();
					brick.getRepresentation().setBounds(brick.getRepresentation().getPosX(), brick.getRepresentation().getPosY(), brick.getRepresentation().getWidth(), brick.getRepresentation().getHeight());
					this.getPanel().getGameZone().add(brick.getRepresentation());
					this.getPhysicEngine().getPhysicalObjects().add(brick);
				}
			}
			this.nbBricks+=10;
		}

		while (iterator.hasNext()) {
			Brick brick = iterator.next();
			if (brick.getRepresentation().getPosY()>this.getPlayer().getRepresentation().getPosY()){
				this.endGame();
				this.gameframe.getCardlayout().show(this.gameframe.getPanelContainer(), "gameOver");
			}
			if(brick.willBeOffScreen(this.getPanel(),5)){
				for (Brick brick2 : this.getBricks()){
					brick2.getRepresentation().setPosY(brick2.getRepresentation().getPosY()+20);
					brick2.setPosition(new Vector2D(brick2.getRepresentation().getPosX(), brick2.getRepresentation().getPosY()+30));
					if(brick2.movingRight()){
						brick2.stopRight();
						brick2.moveLeft();
					}else{
						brick2.stopLeft();
						brick2.moveRight();
					}
				}
			}
			brick.move(1);
			if (!brick.isActive()) {
				if (brick.doesDropBonus()){
					// store the size of the brick
					createBonus(brick.getRepresentation().getPosX() + brick.getRepresentation().getWidth()/2, brick.getRepresentation().getPosY());
				}
				this.nbBricks--; // Decrement the count of brick when the brick is broken
				this.score += 100; // Increment the score when the brick is broken
				// Safely remove the brick from the collection
				iterator.remove();
				
				
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
				applyBonus(bonus.getBonusType());
				this.getPanel().getGameZone().remove(bonus.getRepresentation());
				iterator.remove();
			}
			else if (bonus.willBeOffScreen(this.getPanel(), Bonus.MOVE_SPEED)){
				this.getPanel().getGameZone().remove(bonus.getRepresentation());
				iterator.remove();
			}
			else{
				bonus.moveDown();
				bonus.move(Bonus.MOVE_SPEED);

			}
		}
	}

	/**
	 * Apply the bonus to the game state, given a specific type of bonus
	 */
	public void applyBonus(Bonus.BonusType bonusType){
		switch(bonusType){
			case BONUS_SIZE:
				if (Breakout.this.getPlayer().getRepresentation().getWidth() + (int)(0.1f*Player.DEFAULT_SIZE) <= Player.MAX_SIZE) {
					Breakout.this.getPlayer().getRepresentation().setWidth(Breakout.this.getPlayer().getRepresentation().getWidth() + (int)(0.1f*Player.DEFAULT_SIZE));
				}
				break; 
			case MALUS_SIZE:
				if (Breakout.this.getPlayer().getRepresentation().getWidth() - (int)(0.1f*Player.DEFAULT_SIZE) >= Player.MIN_SIZE) {
					Breakout.this.getPlayer().getRepresentation().setWidth(Breakout.this.getPlayer().getRepresentation().getWidth() - (int)(0.1f*Player.DEFAULT_SIZE));
				}
				break;
			case BONUS_SPEED:
				if (Breakout.this.getPlayer().getIntSpeed() + (int)(0.2f*Player.DEFAULT_SPEED) <= Player.MAX_SPEED) {
					Breakout.this.getPlayer().setIntSpeed(this.getPlayer().getIntSpeed() + (int)(0.2f*Player.DEFAULT_SPEED));
				}
				break;
			case MALUS_SPEED:
				if (Breakout.this.getPlayer().getIntSpeed() - (int)(0.1f*Player.DEFAULT_SPEED) >= Player.MIN_SPEED) {
					Breakout.this.getPlayer().setIntSpeed(Breakout.this.getPlayer().getIntSpeed() - (int)(0.1f*Player.DEFAULT_SPEED));
				}
				break;
			case BONUS_HEALTH:
				if (Breakout.this.getLife() < 3) {
					Breakout.this.setLife(Breakout.this.getLife() + 1);
					Breakout.this.getPanel().updateLife(Breakout.this.getLife());
				}
				break;
			case DEFAULT:
				
				ArrayList<Ball> ballsToBeAdded = new ArrayList<Ball>();
				
				Iterator<Ball> iterator = this.getBalls().iterator();
				
				while(iterator.hasNext()){
					Ball ballToBeDuplicated = iterator.next();


					Random randomDistance = new Random();
					int randomX = (int)ballToBeDuplicated.getPosition().getX() +10+this.getBall().getRepresentation().getWidth();
					int randomY = (int)ballToBeDuplicated.getPosition().getY() -30 -this.getBall().getRepresentation().getHeight();
					Vector2D ballPos = new Vector2D(randomX, randomY);

					Ball ball = new Ball(Ball.DEFAULT_COLOR, 20,20,ballPos,true);
					
					ball.setAcceleration(ballToBeDuplicated.getAcceleration());
					ball.setSpeed(ballToBeDuplicated.getSpeed().add(new Vector2D(randomDistance.nextDouble(0.3), randomDistance.nextDouble(0.3))));

					ballsToBeAdded.add(ball);

				}

				Iterator<Ball> ballsToBeAddedIterator = ballsToBeAdded.iterator();
				while(ballsToBeAddedIterator.hasNext()){
					Ball ball = ballsToBeAddedIterator.next();
					this.getBalls().add(ball);
					this.getPanel().getGameZone().add(ball.getRepresentation());
					this.getPhysicEngine().getPhysicalObjects().add(ball);
					
				}

				break;
			default:
				break;
		}
	}

	private BufferedImage screenShot() {
        Robot robot =null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.out.println("couldn't generate robot for image creation");
		}
        BufferedImage screenShot = robot.createScreenCapture(new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		return screenShot;
	}

	/**
	 * update dedicated to print for test and debug purpose
	 */
	public void updateDebug(){
		System.out.println(Brick.MAX_LIFESPAN);
	};


	public void checkBallInGame(){
		if (this.ball.getPosition().getY() > this.getPanel().getGameZone().getHeight()){
			this.ball.destroy();
			this.getPanel().getGameZone().remove(this.ball.getRepresentation());
			this.getPhysicEngine().getPhysicalObjects().remove(this.ball);
			this.getBalls().remove(this.ball);

			int x = this.player.getRepresentation().getX()+this.player.getRepresentation().getWidth()/3;
			int y = this.player.getRepresentation().getY()-this.player.getRepresentation().getWidth()/3;
			
			Ball ball = new Ball(Ball.DEFAULT_IMAGE2, 30, 50, new Vector2D(x, y), true);
			ball.setSpeed(new Vector2D(0.5, -0.5));
			ball.active=false;
			this.setBall(ball);
			this.getBalls().add(ball);

			this.getPanel().getGameZone().add(this.getBall().getRepresentation());
			this.physicEngine.getPhysicalObjects().add(ball);
			//this.ball.setIsMoving(true);
			this.life--;
			this.getPanel().updateLife(this.life);
		}
	}

	/**
	 * @see game.rules.Game#onUpdate()
	 */
	@Override
	public void onUpdate(double deltaTime) {
		//System.out.println("ACTIF ? "+this.planete.isActive());
		if(!this.gameEnded){
			this.updatePlayer();
			this.updateBall();
			this.checkBallInGame();
			//System.out.println(this.ball.getPosition());
			// if (this.ball.getIsMoving() == true) 
			this.physicEngine.update(deltaTime);
			if(this.level != -1 ){
				this.updateBricks();
				this.ball.resolveSpeedToHigh();
			}else{
			this.updateMarathonBricks();
			}
			this.updateBonus();
		}
		//this.updateDebug();
		//this.getPanel().updateStat(this.score, this.life, this.nbBricks); // update JLabel of statZone in GamePanel 
	}


	public void clearGameComponents() {
		// Remove all bricks from the list and game zone
		this.getPhysicEngine().getPhysicalObjects().clear();
	
		for (Brick brick : this.getBricks()) {
			brick.destroy();
		}
		this.getBricks().clear();
	
		// Remove all bonuses from the list and game zone
		for (Bonus bonus : this.getBonuses()) {
			bonus.destroy();
		}
		this.getBonuses().clear();
	
		this.getBall().destroy();
		for (Ball ball : this.getBalls()) {
			ball.destroy();
		}
		this.getBalls().clear();

		for (Planet planet : this.getPlanets()) {
			planet.destroy();
		}
		this.getPlanets().clear();


		

		// Remove the player from the game zone
		this.getPanel().getGameZone().remove(this.getPlayer().getRepresentation());
	
		// Remove the ball from the game zone
		this.getPanel().getGameZone().remove(this.getBall().getRepresentation());
	
		// Remove the walls from the game zone
		this.getPanel().getGameZone().remove(this.getEastWall().getRepresentation());
		this.getPanel().getGameZone().remove(this.getWestWall().getRepresentation());
		this.getPanel().getGameZone().remove(this.getNorthWall().getRepresentation());
	}


	
}
