package game.breakout;

import java.util.Random;

import game.breakout.entities.Brick;


public class Level {
    
    public static void level(Breakout b) {
		switch (b.getLevel()) {
			case 0:
				createLevel1(b);
				break;
			case 1:
				createLevel1(b);
				break;
			case 2:
				createLevel1(b);
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
			default:
				break;
		}
	}
	

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
	
				b.getBricks().add(new Brick(initialXPos+column*BRICK_SPACING,verticalPos,
				Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT,
				randomLifespan, dropBonus));
			}
		}
	}
}
