package game.breakout;
import java.util.Random;


import display.engine.utils.Vector2D;
import game.breakout.entities.Brick;

import java.io.Serializable;


public class Level implements Serializable {
    public static final long serialVersionUID = 20L;

	// the number of unbreakable bricks that we have to pay attention for the win condition
	public static int unbreakableBrickNumber = 0; 
	
	/**
     * Generates the bricks for the specified level.
     * 
     * @param b the Breakout game instance
     */
    public static void level(Breakout b) {
		switch (b.getLevel()) {
			case 0:
				//createLevel1(b);
				//createLevel2(b);
				//createLevel3(b);
				createLevel4(b);
				break;
			case 1:
				createLevel1(b);
				break;
			case 2:
				createLevel2(b);
				break;
			case 3:
				createLevel3(b);
				break;
			case 4:
				createLevel4(b);
				break;
			case 5:
				createLevel1(b);
				break;
			case 6:
				createLevel1(b);
				break;
			case 100:
				break; // it just loads the saved state for now, later it will give the choice of selecting
			case -1:
				createLevelMarathon(b);
				break;
			default:
				break;
		}
	}
	


	/**
     * Generates the bricks for level 1.
     * 
     * <p>
     * Bricks are created in a grid layout with random lifespans and optional bonus drops.
     * </p>
     * 
     * @param b the Breakout game instance
     */
	public static void createLevel1(Breakout b){

	// TODO: Prevent the amount of bricks from exceeding the panel's width and height
		// See GraphicalObject#isOnScreen(x, y, panel)
		unbreakableBrickNumber = 0;
        int columns = 8; 
        int rows = 4;
		final int BRICK_SPACING = Brick.DEFAULT_WIDTH + 10;

		// Start the bricks at the center of the panel
		int initialXPos = (int) Math.floor(b.getPanel().getGameZone().getPreferredSize().getWidth()
		/ 2 - (columns * BRICK_SPACING) / 2);
		
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				int verticalPos = Brick.DEFAULT_POS_Y + row * (Brick.DEFAULT_HEIGHT + 10);
				int randomLifespan = new Random().nextInt(Brick.MAX_LIFESPAN - 1);

				// Generate a random number between 1 and 3
				int randomNumber = new Random().nextInt(4) + 1;
				boolean dropBonus = (randomNumber == 1);
	
				Brick brick = new Brick(Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				randomLifespan, dropBonus,false, false, 10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);
				b.getBricks().add(brick);

				b.getPhysicEngine().getPhysicalObjects().add(brick);
			}
		}

	}





	 /**
     * Generates the bricks for level 2.
     * 
     * 
     * Bricks are created in a grid layout with full life and optional bonus drops.
	 * 
	 * Additionnally, we have a probability of 1/10 that a brick appears unbreakable.
     * 
     * 
     * @param b the Breakout game instance
     */
    public static void createLevel2(Breakout b) {

        // TODO: Prevent the amount of bricks from exceeding the panel's width and height
		// See GraphicalObject#isOnScreen(x, y, panel)
		unbreakableBrickNumber = 0;
        int columns = 8; 
        int rows = 4;
		final int BRICK_SPACING = Brick.DEFAULT_WIDTH + 10;

		// Start the bricks at the center of the panel
		int initialXPos = (int) Math.floor(b.getPanel().getGameZone().getPreferredSize().getWidth()
		/ 2 - (columns * BRICK_SPACING) / 2);
		
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				int verticalPos = Brick.DEFAULT_POS_Y + row * (Brick.DEFAULT_HEIGHT + 10);
				//int randomLifespan = new Random().nextInt(Brick.MAX_LIFESPAN - 1);

				// Generate a random number between 1 and 3
				int randomNumber = new Random().nextInt(4) + 1;
				boolean dropBonus = (randomNumber == 1);
				
				// Generate a random number between 1 and 10
				int randomNumberUnbreakable = new Random().nextInt(11) + 1;
				boolean unbreakable = (randomNumberUnbreakable == 1);
				if (unbreakable) unbreakableBrickNumber += 1;
				Brick brick = new Brick(Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				Brick.MAX_LIFESPAN-1, dropBonus, unbreakable,false, 10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);
				b.getBricks().add(brick);

				b.getPhysicEngine().getPhysicalObjects().add(brick);
			}
		}
	}

	/**
     * Generates the bricks for level 3.
     * 
     * 
     * Bricks are created in a grid layout with full life and optional bonus drops.
	 * 
	 * Additionnally, all the bricks at the first row and at even position and all 
	 * the bricks at the last row and at odd position are unbreakable.
     * 
     * 
     * @param b the Breakout game instance
     */
	public static void createLevel3(Breakout b) {

        // TODO: Prevent the amount of bricks from exceeding the panel's width and height
		// See GraphicalObject#isOnScreen(x, y, panel)
		unbreakableBrickNumber = 0;
        int columns = 8; 
        int rows = 4;
		final int BRICK_SPACING = Brick.DEFAULT_WIDTH + 10;

		// Start the bricks at the center of the panel
		int initialXPos = (int) Math.floor(b.getPanel().getGameZone().getPreferredSize().getWidth()
		/ 2 - (columns * BRICK_SPACING) / 2);
		
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				int verticalPos = Brick.DEFAULT_POS_Y + row * (Brick.DEFAULT_HEIGHT + 10);
				int lifespan;

				// Generate a random number between 1 and 3
				int randomNumber = new Random().nextInt(4) + 1;
				boolean dropBonus = (randomNumber == 1);
				
				boolean unbreakable = (row == rows - 1 && column % 2 == 1) || (row == 0 && column % 2 == 0);
				if (unbreakable){
					lifespan = Brick.MAX_LIFESPAN;
					unbreakableBrickNumber += 1;
				}
				else lifespan = Brick.MAX_LIFESPAN - 1;
				Brick brick = new Brick(Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				lifespan, dropBonus, unbreakable, false, 10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);
				b.getBricks().add(brick);

				b.getPhysicEngine().getPhysicalObjects().add(brick);
			}
		}
	}

	/**
     * Generates the bricks for level 4.
     * 
     * 
     * Bricks are created in a grid layout with full life and optional bonus drops.
	 * 
	 * Additionnally, we have only one break in a row and there are all moving.
     * 
     * 
     * @param b the Breakout game instance
     */
	public static void createLevel4(Breakout b){
		unbreakableBrickNumber = 0;
		int columns = 8;
        int rows = 12;
		final int BRICK_SPACING = Brick.DEFAULT_WIDTH + 10;

		// Start the bricks at the center of the panel
		int initialXPos = (int) Math.floor(b.getPanel().getGameZone().getPreferredSize().getWidth()
		/ 2 - (columns * BRICK_SPACING) / 2);
		
		for(int row = 0; row < rows; row++){
			int randomColumn = new Random().nextInt(columns);
			System.out.println();
			for(int column = 0; column < columns; column++){
				int verticalPos = Brick.DEFAULT_POS_Y + row * (Brick.DEFAULT_HEIGHT + 10);

				// Generate a random number between 1 and 3
				int randomNumber = new Random().nextInt(4) + 1;
				boolean dropBonus = (randomNumber == 1);
				
				if (randomColumn == column){
					Brick brick = new Brick(Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
					Brick.MAX_LIFESPAN - 1, dropBonus, false, true, 10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);

					if (row % 2 == 0) brick.moveRight();
					else brick.moveLeft();
					b.getBricks().add(brick);

					b.getPhysicEngine().getPhysicalObjects().add(brick);
				}
			}
		}
	}




	/**
	 * handles the bricks in marathon mode
	 * 
	 */
	public static void createLevelMarathon(Breakout b){
		b.setLife(999);

		int columns = 8;
        int rows = 4;
		final int BRICK_SPACING = Brick.DEFAULT_WIDTH + 10;

		// Start the bricks at the center of the panel
		int initialXPos = (int) Math.floor(b.getPanel().getGameZone().getPreferredSize().getWidth()
		/ 2 - (columns * BRICK_SPACING) / 2);
		
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				int verticalPos = Brick.DEFAULT_POS_Y + row * (Brick.DEFAULT_HEIGHT + 10);
				int randomLifespan = new Random().nextInt(Brick.MAX_LIFESPAN);

				// Generate a random number between 1 and 3
				int randomNumber = new Random().nextInt(4) + 1;
				boolean dropBonus = (randomNumber == 1);
				
				Brick brick = new Brick(Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				randomLifespan, dropBonus, false, false, 10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);

				brick.moveRight();
				b.getBricks().add(brick);

				b.getPhysicEngine().getPhysicalObjects().add(brick);
			}
		}
	}
}