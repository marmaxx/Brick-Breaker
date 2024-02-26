import javax.swing.SwingUtilities;
import display.view.GameFrame;
import game.breakout.Breakout;
import game.rules.Game;

public class Main {
    public static void main(String [] args){
		
		// Create the game frame asynchonously (not immediately)
		// in a separate thread
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameFrame gameFrame = new GameFrame();
				// Create the game and attach it to the frame
				Game game = new Breakout(gameFrame);
				game.start();
			}
		});

    }
}
