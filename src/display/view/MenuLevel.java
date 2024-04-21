package display.view;

import javax.swing.*; 
import java.awt.*; 

import game.breakout.Breakout;
import game.rules.Game;

public class MenuLevel extends JPanel {

    private GameFrame game_frame; 
     private enum Level {
        level_1,
        level_2,
        level_3, 
        level_4, 
        level_5, 
        level_6
    };
    private JButton level_1 = createStyledButton(" Level 1");   
    private JButton level_2 = createStyledButton(" Level 2"); 
    private JButton level_3 = createStyledButton(" Level 3"); 
    private JButton level_4 = createStyledButton(" Level 4");   
    private JButton level_5 = createStyledButton(" Level 5"); 
    private JButton level_6 = createStyledButton(" Level 6"); 
    private JButton menu = createStyledButton(" Back to Menu ");

    public MenuLevel(GameFrame gameFrame){
        this.game_frame = gameFrame;

        this.setLayout(new GridLayout(2,4));
        this.setBackground(new Color(30,30,30));
        this.addActionListener();
        this.addLevel();

        this.menu.addActionListener((event) -> {
            this.game_frame.getCardlayout().show(this.game_frame.getPanelContainer(), "classicGame");
        });

        this.add(menu);
    }


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219)); // Bleu
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        return button;
    }

    private void addLevel(){
        this.add(level_1);
        this.add(level_2); 
        this.add(level_3); 
        this.add(level_4); 
        this.add(level_5); 
        this.add(level_6); 
    }

    private void addActionListener(){
        Level[] levelTab = Level.values(); 
        for (int i = 1; i < levelTab.length+1; i++) {
            JButton button = null;
            switch(i) {
                case 1:
                    button = level_1;
                    this.addAction(1, button);
                    break;
                case 2:
                    button = level_2;
                    this.addAction(2, button);
                    break;
                case 3:
                    button = level_3;
                    this.addAction(3, button);
                    break;
                case 4:
                    button = level_4;
                    this.addAction(4, button);
                    break;
                case 5:
                    button = level_5;
                    this.addAction(5, button);
                    break;
                case 6:
                    button = level_6;
                    this.addAction(6, button);
                    break;
            }
        } 
    }

    private void startgame(int i){
        game_frame.getCardlayout().show(game_frame.getPanelContainer(), "gamePanel"); // switching the card layout
        game_frame.getPanelContainer().add(game_frame.getMenuPanel(), "MenuPanel");
        Game game = new Breakout(game_frame,i); //created instance of Breakout
		game.start(); //starting the game 
    }

    private void addAction(int i, JButton b){
        b.addActionListener((event) -> {
        startgame(i);
        });
    }

}
