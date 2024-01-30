
/**
 * this class is to initialize the graphical elements of the game. It is not to be used for updating the game 
 */
public class GameGraphicsFactory {
	
	public GameGraphicsFactory() {
		//beginning of paddle implementation
		Paddle paddle = new Paddle();
		View.GameView.getRootPane().add(paddle);
		//end of paddle implementation
	}
}
