package display.view;

import javax.swing.*; 
import java.awt.*; 


public class MenuLevel extends JPanel {

    private GameFrame game_frame;
    private int nbLevelUnlock;
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
        this.nbLevelUnlock  = this.game_frame.getNbLevelUnlock(); 

        this.setLayout(new GridLayout(2,4));
        this.setBackground(new Color(30,30,30));
        this.addActionListener();
        this.addLevel();

        this.menu.addActionListener((event) -> {
            this.game_frame.getCardlayout().show(this.game_frame.getPanelContainer(), "classicGame");
        });
        addMouseListener(menu);

        this.add(menu);
    }


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 22));
        button.setPreferredSize(new Dimension(400, 80));
        button.setMaximumSize(new Dimension(400, 80));
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        button.setContentAreaFilled(false);
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
        int nb = this.nbLevelUnlock;
        if ( nb > levelTab.length){
            nb = levelTab.length;
        }
        for (int i = 1; i <  nb+1 ; i++) {
            JButton button = null;
            switch(i) {
                case 1:
                    button = level_1;
                    this.addAction(1, button);
                    this.addMouseListener(button);
                    break;
                case 2:
                    button = level_2;
                    this.addAction(2, button);
                    this.addMouseListener(button);
                    break;
                case 3:
                    button = level_3;
                    this.addAction(3, button);
                    this.addMouseListener(button);
                    break;
                case 4:
                    button = level_4;
                    this.addAction(4, button);
                    this.addMouseListener(button);
                    break;
                case 5:
                    button = level_5;
                    this.addAction(5, button);
                    this.addMouseListener(button);
                    break;
                case 6:
                    button = level_6;
                    this.addAction(6, button);
                    this.addMouseListener(button);
                    break;
            }
        } 
    }


    private void addAction(int i, JButton b){
        b.addActionListener((event) -> {
            game_frame.getCardlayout().show(game_frame.getPanelContainer(), "gamePanel");
            game_frame.startGame(i);
        });
    }

    private void addMouseListener(JButton b){
        b.addMouseListener(new ButtonMouseListener(b));
    }

}
