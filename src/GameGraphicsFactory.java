import java.awt.Color;
import display.view.GameFrame;
import game.brickbreaker.entities.Player;

/**
 * this class is to initialize the graphical elements of the game. It is not to be used for updating the game 
 */
public class GameGraphicsFactory {
	
	public GameGraphicsFactory() {
		
		//beginning of player implementation
		Player player = new Player(500,500,20,20,1,1,1);
		GameFrame.GameView.add(player);
		//end of player implementation
		/* 
		Paddle paddle = new Paddle(980,300);
		GameFrame.GameView.add(paddle);
		*/
		
	}
}
