package display.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridLayout;

public class SavedGames extends JPanel {
    public static final long serialVersionUID = 111L;
	

    private GameFrame game_frame;

    private JButton Save1= createStyledButton(" Save 1");    
    private JButton menu = createStyledButton(" Back to Menu ");

    public SavedGames(GameFrame gameFrame){
        this.game_frame = gameFrame; 

        this.setLayout(new GridLayout(2,4));
        this.setBackground(new Color(30,30,30));

        Save1.addActionListener((event) -> {
            game_frame.getCardlayout().show(game_frame.getPanelContainer(), "gamePanel");
            game_frame.startGame(100);
        });
        this.addMouseListener(Save1);
        this.add(Save1);

        this.menu.addActionListener((event) -> {
            this.game_frame.getCardlayout().show(this.game_frame.getPanelContainer(), "classicGame");
        });
        addMouseListener(menu);

        this.add(menu);
    }



    private void addMouseListener(JButton b){
        b.addMouseListener(new ButtonMouseListener(b));
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

}

