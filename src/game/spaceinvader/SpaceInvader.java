package game.spaceinvader;

import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import display.engine.PhysicsEngine;
import display.engine.rules.PhysicalObject;
import display.engine.utils.Vector2D;
import game.rules.Game;
import game.spaceinvader.entities.EnemiesAmmo;
import game.spaceinvader.entities.Enemy;
import game.spaceinvader.entities.Player;
import game.spaceinvader.entities.PlayersAmmo;
import game.spaceinvader.entities.Wall;
import game.spaceinvader.entities.EnemiesAmmo.EnemiesAmmoType;
import display.view.brickbreakerview.*;
import display.view.*;
import display.view.brickbreakerview.ClassicGamePanel;
import display.view.Scale;

public class SpaceInvader extends Game{
	
	// Path to the assets folder
	public final static String ASSETS_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator 
	+ "game" + File.separator + "spaceInvader" + File.separator + "assets" + File.separator;
	GameFrame gameframe;

	// Entities
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemiesAmmo> enemiesAmmos;
	private ArrayList<PlayersAmmo>  playersAmmos;
	private Player player;
	private PlayersAmmo playersAmmo;
	private Wall eastWall, northWall, westWall;
	private static final int WALL_WIDTH = 20;
	// init at 1 because it takes time to initialize the list of enemies 
	// and it would lead us to the win panel directly
	private int nbEnemies = 1; 
	private int life = 5;

	// Physics engine
	private PhysicsEngine physicEngine = new PhysicsEngine();
	
	/**
	 * Instantiates a new SpaceInvader game
	 * 
	 * @param gameFrame The frame in which the game is displayed
	 */
	public SpaceInvader(GameFrame gameFrame) {

		// to set the title of the game
		super(gameFrame.getGamePanel(), "Space Invader");
		this.gameframe = gameFrame;
		this.gameframe.setGame(this);

		// to set the entities that will be needed for the game
		this.setEnemies(new ArrayList<Enemy>());
		this.setEnemiesAmmos(new ArrayList<EnemiesAmmo>());
		this.setPlayersAmmos(new ArrayList<PlayersAmmo>());
		this.setPlayer(new Player(Player.DEFAULT_IMAGE, Player.DEFAULT_SIZE,Player.DEFAULT_SPEED, 51,new Vector2D(530, 700),false));
		PlayersAmmo mainAmmo = new PlayersAmmo(PlayersAmmo.DEFAULT_IMAGE,  30,50,new Vector2D(535,700),true);

		// to set the player's ammo 
		this.setPlayersAmmo(mainAmmo);
		mainAmmo.active=false;
		this.getPlayersAmmos().add(mainAmmo);

		// to set the walls of the game which would be used as delimitation borders
		this.setEastWall(new Wall(WALL_WIDTH, (int)GamePanel.GAME_ZONE_SIZE.getHeight(), 100,new Vector2D((int)GamePanel.GAME_ZONE_SIZE.getWidth()-WALL_WIDTH, 0),false));
		this.setWestWall(new Wall(WALL_WIDTH, (int)GamePanel.GAME_ZONE_SIZE.getHeight(),100,new Vector2D(0, 0),false));
		this.setNorthWall(new Wall((int)GamePanel.GAME_ZONE_SIZE.getWidth(), WALL_WIDTH,100,new Vector2D(0, 0),false));

		// to add the entities to the physic engine
		this.physicEngine.getPhysicalObjects().add(playersAmmo);
		this.physicEngine.getPhysicalObjects().add(northWall);	

		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:
					case KeyEvent.VK_LEFT:
						SpaceInvader.this.getPlayer().moveLeft();
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						SpaceInvader.this.getPlayer().moveRight();
						break;
					case KeyEvent.VK_P:
						if(SpaceInvader.this.isPaused()){
							SpaceInvader.this.resume();
						}
						else{
							SpaceInvader.this.pause();
						}
						break;
					case KeyEvent.VK_SPACE:
						if (!SpaceInvader.this.getPlayersAmmo().active){
							//SpaceInvader.this.getPlayersAmmo().setIsMoving(true);
							SpaceInvader.this.getPlayersAmmo().active=true;
							Vector2D newPosition = new Vector2D(SpaceInvader.this.playersAmmo.getRepresentation().getX(), SpaceInvader.this.playersAmmo.getRepresentation().getY());
							SpaceInvader.this.playersAmmo.getRepresentation().setBounds((int)newPosition.getX(), (int)newPosition.getY(), SpaceInvader.this.playersAmmo.getRepresentation().getWidth(), SpaceInvader.this.playersAmmo.getRepresentation().getHeight());
							SpaceInvader.this.playersAmmo.setPosition(newPosition);
							Vector2D speed = new Vector2D(0, -0.3);
							SpaceInvader.this.playersAmmo.setSpeed(speed);
						}
						break;
					case KeyEvent.VK_M:
						if (!SpaceInvader.this.gameframe.getSpaceInvaderGame().isPaused()){
							SpaceInvader.this.pause();
							SpaceInvader.this.gameframe.getGamePanel().getGameZone().setVisible(false);
							SpaceInvader.this.gameframe.getGamePanel().getMenu().setVisible(true);
						}
						break;
					case KeyEvent.VK_R:
					if (SpaceInvader.this.gameframe.getSpaceInvaderGame().isPaused()){
						SpaceInvader.this.resume();
						SpaceInvader.this.gameframe.getGamePanel().getGameZone().setVisible(true);
						SpaceInvader.this.gameframe.getGamePanel().getMenu().setVisible(false);
					}
					break;
						
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_Q:
					case KeyEvent.VK_LEFT:
						SpaceInvader.this.getPlayer().stopLeft();
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						SpaceInvader.this.getPlayer().stopRight();
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
	 * Get the list of enemies in the game.
	 * 
	 * @return The list of enemies
	 */
	public ArrayList<Enemy> getEnemies() {
		return this.enemies;
	}

	/**
	 * Set the list of enemies in the game.
	 * 
	 * @param enemies The list of enemies
	 */
	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	/**
	 * Get the list of enemies in the game.
	 * 
	 * @return The list of enemies
	 */
	public ArrayList<PlayersAmmo> getPlayersAmmos() {
		return this.playersAmmos;
	}

	/**
	 * Set the list of enemies in the game.
	 * 
	 * @param enemies The list of enemies
	 */
	public void setPlayersAmmos(ArrayList<PlayersAmmo> ammos) {
		this.playersAmmos = ammos;
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
	 * Get the list of enemiesAmmos in the game.
	 * 
	 * @return The list of enemiesAmmos
	 */
	public ArrayList<EnemiesAmmo> getEnemiesAmmos(){
		return this.enemiesAmmos;
	}
	

	/**
	 *  Set the list of enemiesAmmos in the game.
	 * 
	 *  @param enemiesAmmos The list of enemiesAmmos
	 */
	public void setEnemiesAmmos(ArrayList<EnemiesAmmo> enemiesAmmos){
		this.enemiesAmmos=enemiesAmmos;
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
	 * Get the playersAmmo entity in the game.
	 * 
	 * @return The playersAmmo entity
	 */
	public PlayersAmmo getPlayersAmmo() {
		return this.playersAmmo;
	}

	/**
	 * Set the playersAmmo entity in the game.
	 * 
	 * @param playersAmmo The playersAmmo entity
	 */
	public void setPlayersAmmo(PlayersAmmo playersAmmo) {
		this.playersAmmo = playersAmmo;
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
	 * Get the enemies number in the game.
	 * 
	 * @return The enemies number 
	 */
	public int getnbEnemies(){
		return this.nbEnemies;
	}

	/**
	 * set the enemies number in the game.
	 * 
	 * @param e enemies number 
	 */
	public void setnbEnemies(int e){
		this.nbEnemies = e;
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

	/**
	 * Get the PhysicsEngine of the game
	 * 
	 * @return The PhysicsEngine
	 */
	public PhysicsEngine getPhysicEngine() {
		return physicEngine;
	}


	/**
	 * Create the enemies in the game
	 * 
	 * @param rows The number of rows of enemies
	 * @param columns The number of columns of enemies
	 */
	public void createEnemy(int rows, int columns){
		
		// Spacing between each brick, H for horizontal, V for vertical
		final int BRICK_H_SPACING = (int)(Enemy.DEFAULT_WIDTH * 1.05);
		final int BRICK_V_SPACING = (int)(Enemy.DEFAULT_HEIGHT * 1.20);
	
		// Start the enemies at the center of the panel
		int initialXPos = (int) Math.floor(this.getPanel().getGameZone().getPreferredSize().getWidth()
		/ 2 - (columns * BRICK_H_SPACING) / 2);
		
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				int verticalPos = Enemy.DEFAULT_POS_Y + row * BRICK_V_SPACING;
				int randomLifespan = new Random().nextInt(Enemy.MAX_LIFESPAN);
				Enemy enemy = new Enemy(Enemy.DEFAULT_WIDTH,Enemy.DEFAULT_HEIGHT,
				randomLifespan, 10,new Vector2D(initialXPos+column*BRICK_H_SPACING,verticalPos),false);

				// Add the enemy to the game, move to the right by default
				enemy.moveRight();
				this.getEnemies().add(enemy);
				
				// Add the enemy to the physic engine
				this.getPhysicEngine().getPhysicalObjects().add(enemy);
			}
		}
	}
		
	/**
	 * Create a bonus at a given position (posX is the center of the bonus, however y is the top)
	 * 
	 * @param posX
	 * @param posY
	 */
	public void createEnemyAmmo(int posX, int posY){
	
		// Get a random between 0 and the last number of the hashmap 
		int randomEnemiesAmmoType = new Random().nextInt(EnemiesAmmo.MAX_ENEMIESAMMOTYPE);
		this.getEnemiesAmmos().add(new EnemiesAmmo(EnemiesAmmo.enemiesAmmoTypes.get(EnemiesAmmoType.values()[randomEnemiesAmmoType]), posX, posY, EnemiesAmmo.DEFAULT_SIZE, EnemiesAmmoType.values()[randomEnemiesAmmoType]));
		for (EnemiesAmmo enemiesAmmo : this.getEnemiesAmmos()) {
			this.getPanel().getGameZone().add(enemiesAmmo.getRepresentation());
		}
	}

	/**
	 * @see game.rules.Game#start()
	 */
	@Override
	public void start() {
		super.start();
		this.createEnemy(5,8);
		this.nbEnemies = this.enemies.size(); //initialize nbEnemies withe the size of list enemies

		// Add all entities to the game
		for (Enemy enemy : this.getEnemies()) {
			this.getPanel().getGameZone().add(enemy.getRepresentation());
		}
		this.getPanel().getGameZone().add(this.getEastWAll().getRepresentation());
		this.getPanel().getGameZone().add(this.getWestWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getNorthWall().getRepresentation());
		this.getPanel().getGameZone().add(this.getPlayer().getRepresentation());
		this.getPanel().getGameZone().add(this.getPlayersAmmo().getRepresentation());
		this.getPanel().updateLife(this.life);


		// Call getRatioForResolution from Scale.java and print the result
		// use width and heigth from classicgamejava
		int resolutionRatio = Scale.getRatioForResolution(ClassicGamePanel.SCREEN_FULL_SIZE.getWidth(), ClassicGamePanel.SCREEN_FULL_SIZE.getHeight());
		System.out.println("Resolution Ratio: " + resolutionRatio);


	}

	/**
	 * Update the player entity
	 */
	public void updatePlayer() {
		if(!this.getPlayer().willBeOffScreen(this.getPanel(), this.getPlayer().getIntSpeed())){
			this.player.setLastPos(this.player.getPosition());
			this.getPlayer().move(this.getPlayer().getIntSpeed());
			
			if (!this.getPlayersAmmo().active){
				this.playersAmmo.getRepresentation().setPosX(this.player.getRepresentation().getX() + this.player.getRepresentation().getWidth()/3);				
			}
		}
		else{
			this.getPlayer().stopLeft();
			this.getPlayer().stopRight();
		}
	}

	/**
	 * Update the player's ammo entity
	 */
	 public void updatePlayersAmmo() {
			//if(this.getPlayersAmmo().active)this.getPlayersAmmo().trail.addPoint(new Circle(Color.RED, this.getPlayersAmmo().getRepresentation().getPosX()+(this.getPlayersAmmo().getRepresentation().getWidth()/2), this.getPlayersAmmo().getRepresentation().getPosY()+(this.getPlayersAmmo().getRepresentation().getHeight()/2), 10, 10),this);
			if( this.getLife() <=0 && this.getnbEnemies() > 0){
				this.clearGameComponents();
				this.gameframe.getCardlayout().show(this.gameframe.getPanelContainer(), "gameOver");
			}
		}
	
	
	/**
	 * Update the enemies entities in the marathon game mode
	 */
	public void updateMarathonEnemies() {
		// Using an iterator to safely remove enemies from the collection
		// Without getting the ConcurrentModificationException
		ListIterator<Enemy> iterator = this.getEnemies().listIterator();
		boolean shouldChangeDirection = false;

		while (iterator.hasNext()) {
			Enemy enemy = iterator.next();
			if (enemy.getRepresentation().getPosY()>this.getPlayer().getRepresentation().getPosY()){
				this.clearGameComponents();
				this.gameframe.getCardlayout().show(this.gameframe.getPanelContainer(), "gameOver");
			}
			if(enemy.willBeOffScreen(this.getPanel(),5)){
				shouldChangeDirection = true;
			}
			if (new Random().nextInt(5000) == 1){
				createEnemyAmmo(enemy.getRepresentation().getPosX() + enemy.getRepresentation().getWidth()/2, enemy.getRepresentation().getPosY());
			}
			enemy.move(1);
			if (!enemy.isActive()) {
				// Decrement the count of enemy when the enemy is broken
				this.nbEnemies--; 
				// Safely remove the enemy from the collection
				iterator.remove();
			}   
		}

		// Change direction of all enemies if any enemy touched the border
		if (shouldChangeDirection) {
			for (Enemy enemy : this.getEnemies()) {
				enemy.getRepresentation().setPosY(enemy.getRepresentation().getPosY()+15);
				enemy.setPosition(new Vector2D(enemy.getRepresentation().getPosX(), enemy.getRepresentation().getPosY()+30));
				if(enemy.movingRight()){
					enemy.stopRight();
					enemy.moveLeft();
				}else{
					enemy.stopLeft();
					enemy.moveRight();
				}
			}
		}

		// If there are no more enemies, the player wins
		if (this.nbEnemies == 0 && this.life > 0) {
			this.clearGameComponents();
			this.gameframe.getCardlayout().show(this.gameframe.getPanelContainer(), "winPanel");
		}
	}

	/**
	 * Update the bonus entity
	 */
	public void updateEnemyAmmo(){
		
		Iterator<EnemiesAmmo> iterator = this.getEnemiesAmmos().iterator();

		while(iterator.hasNext()){
			EnemiesAmmo enemyAmmo = iterator.next();
			//if (enemyAmmo.getRepresentation().isColliding(this.getPlayer().getRepresentation())){ used to be like that just in case if someone wanna do something on it
			if (checkCollision(enemyAmmo, this.getPlayer())){
				ApplyHit(enemyAmmo.getenemiesAmmoType());
				this.getPanel().getGameZone().remove(enemyAmmo.getRepresentation());
				iterator.remove();
			}
			else if (enemyAmmo.willBeOffScreen(this.getPanel(), enemyAmmo.getBulletVelocity())){
				this.getPanel().getGameZone().remove(enemyAmmo.getRepresentation());
				iterator.remove();
			}
			else{
				enemyAmmo.moveDown();
				enemyAmmo.move(enemyAmmo.getBulletVelocity());

			}
		}
	}

	/**
	 * Apply the bonus to the game state, given a specific type of bonus
	 */
	public void ApplyHit(EnemiesAmmo.EnemiesAmmoType enemiesAmmoType){
		switch(enemiesAmmoType){
			case LOW_DAMAGE_AMMO:
				// decrease the life by only one
				this.life--;
				// dont forget the ui update ... i thought the code was glithed -.-'
				this.getPanel().updateLife(this.life); 
				break; 
			case HIGH_DAMAGE_AMMO:
				// by 3
				this.life-=3;
				this.getPanel().updateLife(this.life);
				break;
			case DEFAULT:
				// by 2
				this.life-=2;
				this.getPanel().updateLife(this.life);
				break;
			default:
				break;
		}
	}


	/**
	 * update dedicated to print for test and debug purpose
	 */
	public void updateDebug(){
		System.out.println(Enemy.MAX_LIFESPAN);
	};

	/**
	 * Check if the player's ammo is still in the game zone
	 */
	public void CheckPlayersAmmoInGame(){
		if (this.playersAmmo.getPosition().getY() > this.getPanel().getGameZone().getHeight() || collidingWithEnemy()){
			int x = this.player.getRepresentation().getX()+this.player.getRepresentation().getWidth()/3;
			int y = this.player.getRepresentation().getY()-this.player.getRepresentation().getWidth()/3;
			this.getPlayersAmmo().deletePlayersAmmo(this);
			this.getPlayersAmmo().respawnPlayersAmmo(this, x, y);
		}
	}
	
	/**
	 * Check if the player's ammo is colliding with an enemy
	 * 
	 * @return boolean true if the player's ammo is colliding with an enemy
	 */
	public boolean collidingWithEnemy(){
		if (this.getPlayersAmmo().getHasHitAnEnemy() == true){
			this.getPlayersAmmo().setHasHitAnEnemy(false);
			return true;
		}
		return false;
	}

	/**
	 * check if two objects are colliding
	 * 
	 * @param object1
	 * @param object2
	 * @return boolean true if the two objects are colliding
	 */
	public boolean checkCollision(PhysicalObject object1, PhysicalObject object2){
		return object1.getRepresentation().isColliding(object2.getRepresentation());
	}

	/**
	 * check if the player's ammo is colliding with an enemy
	 * 
	 * @param enemy
	 * @return boolean if it collides with an enemy only
	 */
	public boolean CheckPlayersAmmoCollision(Enemy enemy){
		return this.checkCollision(this.playersAmmo, enemy);
	}

	/**
	 * check if the player's ammo is colliding with an enemy for all enemies
	 * 
	 * @return
	 */
	public boolean CheckPlayersAmmoCollision(){
		for (Enemy enemy : this.getEnemies()){
			if (checkCollision(this.getPlayersAmmo(), enemy)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @see game.rules.Game#onUpdate()
	 */
	@Override
	public void onUpdate(double deltaTime) {
		this.updatePlayer();
		this.updatePlayersAmmo();
		this.CheckPlayersAmmoInGame();
		this.physicEngine.update(deltaTime);
		this.updateMarathonEnemies();
		this.updateEnemyAmmo();
	}


	/**
	 * Clear all game components
	 */
	public void clearGameComponents() {
		// Remove all enemies from the list and game zone
		for (Enemy enemy : this.getEnemies()) {
			this.getPanel().getGameZone().remove(enemy.getRepresentation());
		}
		this.getEnemies().clear();
	
		// Remove all enemiesAmmos from the list and game zone
		for (EnemiesAmmo enemyAmmo : this.getEnemiesAmmos()) {
			this.getPanel().getGameZone().remove(enemyAmmo.getRepresentation());
		}
		this.getEnemiesAmmos().clear();
	
		// Remove the player from the game zone
		this.getPanel().getGameZone().remove(this.getPlayer().getRepresentation());
	
		// Remove the playersAmmo from the game zone
		this.getPanel().getGameZone().remove(this.getPlayersAmmo().getRepresentation());
	
		// Remove the walls from the game zone
		this.getPanel().getGameZone().remove(this.getEastWAll().getRepresentation());
		this.getPanel().getGameZone().remove(this.getWestWall().getRepresentation());
		this.getPanel().getGameZone().remove(this.getNorthWall().getRepresentation());
	}
	
}
