
import javax.swing.SwingUtilities;
import display.view.GameFrame;
import display.view.HomePage;

public class Main {
    public static void main(String [] args){
		// Create the game frame asynchonously (not immediately)
		// in a separate thread
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameFrame gameFrame = new GameFrame();
				gameFrame.addHomePage(new HomePage(gameFrame));
				gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "homePage");
			}
		});

    }
}