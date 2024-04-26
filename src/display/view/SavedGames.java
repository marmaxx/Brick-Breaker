package display.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridLayout;

import java.util.ArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.stream.Collectors;
import java.nio.file.Path;

public class SavedGames extends JPanel {
    public static final long serialVersionUID = 111L;
	
    public ArrayList<String> saveFileNames = getSaveFileNames("Saves");
    private GameFrame game_frame;
  
    private JButton menu = createStyledButton(" Back to Menu ");

    public SavedGames(GameFrame gameFrame){
        this.game_frame = gameFrame; 

        this.setLayout(new GridLayout(2,4));
        this.setBackground(new Color(30,30,30));
        for (String saveFileName : saveFileNames) {
            JButton saveButton = createStyledButton(saveFileName.substring(0, saveFileName.length() - 4)); // substring is used here to remove the ".txt" 
            saveButton.addActionListener((event) -> {
                game_frame.getCardlayout().show(game_frame.getPanelContainer(), "gamePanel");
                game_frame.loadGame(saveFileName);
            });
            
            JButton deleteButton = createStyledButton("Delete: " + saveFileName.substring(0, saveFileName.length() - 4));
            deleteButton.addActionListener((event) -> {
                deleteSaveFile(saveFileName);
                game_frame.getCardlayout().show(game_frame.getPanelContainer(), "menuPanel");
                updateSaveFileNames();
            });

            addMouseListener(saveButton);
            addMouseListener(deleteButton);
            this.add(saveButton);
            this.add(deleteButton);

        
        }

        this.menu.addActionListener((event) -> {
            this.game_frame.getCardlayout().show(this.game_frame.getPanelContainer(), "classicGame");
        });
        addMouseListener(menu);

        this.add(menu);
    }

    
    public ArrayList<String> getSaveFileNames(String directory) {
        ArrayList<String> saveFileNames = new ArrayList<>();
        try {
            saveFileNames = (ArrayList<String>) Files.walk(Paths.get(directory))
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFileNames;
    }
    
    public void updateSaveFileNames() {
        this.saveFileNames = getSaveFileNames("Saves");
        this.removeAll();
        menu = createStyledButton(" Back to Menu "); 

        this.setLayout(new GridLayout(2,4));
        this.setBackground(new Color(30,30,30));
        for (String saveFileName : saveFileNames) {
            JButton saveButton = createStyledButton(saveFileName.substring(0, saveFileName.length() - 4)); // substring is used here to remove the ".txt" 
            saveButton.addActionListener((event) -> {
                game_frame.getCardlayout().show(game_frame.getPanelContainer(), "gamePanel");
                game_frame.loadGame(saveFileName);
            });



            JButton deleteButton = createStyledButton("Delete: " + saveFileName.substring(0, saveFileName.length() - 4));
            deleteButton.addActionListener((event) -> {
                deleteSaveFile(saveFileName);
                game_frame.getCardlayout().show(game_frame.getPanelContainer(), "menuPanel");
                updateSaveFileNames();
            });

            addMouseListener(saveButton);
            addMouseListener(deleteButton);
            this.add(saveButton);
            this.add(deleteButton);

        }

        this.menu.addActionListener((event) -> {
            this.game_frame.getCardlayout().show(this.game_frame.getPanelContainer(), "classicGame");
        });
        addMouseListener(menu);

        this.add(menu);
    }

    private void deleteSaveFile(String fileName) {
        Path filePath = Paths.get("Saves"+java.io.File.separator + fileName);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

