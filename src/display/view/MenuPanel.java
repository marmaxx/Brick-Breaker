package display.view;
import javax.swing.*;
import java.awt.*;

import game.breakout.Breakout;
import game.rules.Game;

public class MenuPanel extends JPanel {
    //private static final Color GAME_BACKGROUND_COLOR = new Color(30,30,30);
    public static final Dimension BUTTON_SIZE = new Dimension(300,100);
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    JButton QuickGame = new JButton("Quick Game");

    public MenuPanel(GameFrame gameFrame){
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(SCREEN_FULL_SIZE.height, SCREEN_FULL_SIZE.width/2));


        QuickGame.setPreferredSize(BUTTON_SIZE);
        QuickGame.setBackground(Color.RED);

        QuickGame.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getContainer(), "gamePanel");
            Game game = new Breakout(gameFrame);
			game.start();
        });


        this.add(QuickGame);
    }
}
