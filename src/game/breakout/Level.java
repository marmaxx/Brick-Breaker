package game.breakout;
import java.util.Random;


import display.engine.utils.Vector2D;
import game.breakout.entities.Brick;
import game.breakout.entities.rules.Entity;

import java.io.Serializable;


public class Level implements Serializable {
    public static final long serialVersionUID = 20L;
	/**
     * Generates the bricks for the specified level.
     * 
     * @param b the Breakout game instance
     */
    public static void level(Breakout b) {
		switch (b.getLevel()) {
			case 0:
				createLevel1(b);
				break;
			case 1:
				createLevel1(b);
				break;
			case 2:
				createLevel2(b);
				break;
			case 3:
				createLevel1(b);
				break;
			case 4:
				createLevel1(b);
				break;
			case 5:
				createLevel1(b);
				break;
			case 6:
				createLevel1(b);
				break;
			case -1: 
				createLevelMarathon(b);
				break;
			default:
				System.out.println("unhandled level number");
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
				randomLifespan, dropBonus,10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);
				b.getBricks().add(brick);

				b.getPhysicEngine().getPhysicalObjects().add(brick);
			}
		}

	}





	 /**
     * Generates the bricks for level 2.
     * 
     * 
     * Bricks are created in a grid layout with random lifespans and optional bonus drops.
     * Additionally, bricks at odd positions are rotated by -10 degrees.
     * 
     * 
     * @param b the Breakout game instance
     */
    public static void createLevel2(Breakout b) {

        int columns = 8;
        int rows = 4;
        final int BRICK_SPACING = Brick.DEFAULT_WIDTH + 10;

        int initialXPos = (int) Math.floor(b.getPanel().getGameZone().getPreferredSize().getWidth()
                / 2 - (columns * BRICK_SPACING) / 2);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if ((row % 2 == 0) && (column % 2 == 0) || (row % 2 == 1) && (column % 2 == 1)) {
                    int verticalPos = Brick.DEFAULT_POS_Y + row * (Brick.DEFAULT_HEIGHT + 10);
                    int randomLifespan = new Random().nextInt(Brick.MAX_LIFESPAN);

                    int randomNumber = new Random().nextInt(4) + 1;
                    boolean dropBonus = (randomNumber == 1);

                    Brick brick = new Brick(Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				randomLifespan, dropBonus,10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);


					// Apply rotation of -10 degrees to rectangles at odd positions
                    if ((row % 2 == 1) && (column % 2 == 1)) {
                        brick.getRectangle().setRotate(-10); 
                    }

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
				randomLifespan, dropBonus,10,new Vector2D(initialXPos+column*BRICK_SPACING,verticalPos),false);

				brick.moveRight();
				b.getBricks().add(brick);

				b.getPhysicEngine().getPhysicalObjects().add(brick);
			}
		}
	}
}