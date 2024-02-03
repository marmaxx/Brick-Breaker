import javax.swing.SwingUtilities;

import display.view.GameFrame;
import game.brickbreaker.BrickBreaker;
import game.Game;

public class Main {
    public static void main(String [] args){
        // Create new thread to stat new view
        SwingUtilities.invokeLater(() -> new GameFrame());
        SwingUtilities.invokeLater(()-> new GameGraphicsFactory());
    }
}
