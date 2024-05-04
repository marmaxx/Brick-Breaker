package display.view;

import javax.swing.*; 
import java.awt.*; 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class MenuLevel extends JPanel {
    public static final long serialVersionUID = 55L;
	

    private GameFrame game_frame;
    private int nbLevelUnlock;
    private BufferedImage backgroundImage; // background image 
    private static final String LEVEL_1_IMAGE_PATH = Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Level1.png";

     private enum Level {
        level_1,
        level_2,
        level_3, 
        level_4, 
        level_5, 
        level_6
    };
    private JButton level_1 = createStyledButton("Level 1" , LEVEL_1_IMAGE_PATH);   
    private JButton level_2 = createStyledButton("Level 2" , LEVEL_1_IMAGE_PATH); 
    private JButton level_3 = createStyledButton("Level 3" , LEVEL_1_IMAGE_PATH); 
    private JButton level_4 = createStyledButton("Level 4" , LEVEL_1_IMAGE_PATH);   
    private JButton level_5 = createStyledButton("Level 5" , LEVEL_1_IMAGE_PATH); 
    private JButton level_6 = createStyledButton("Level 6" , LEVEL_1_IMAGE_PATH); 
    private JButton menu = new JButton("MENU");


    public MenuLevel(GameFrame gameFrame){
        this.game_frame = gameFrame;
        this.nbLevelUnlock = this.game_frame.getNbLevelUnlock(); 

        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Careers.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setLayout(new GridBagLayout());
        this.addActionListener();

        this.menu.setFont(new Font("Ubuntu", Font.BOLD, 22));
        this.menu.setPreferredSize(new Dimension(400, 80));
        this.menu.setMaximumSize(new Dimension(400, 80));
        this.menu.setForeground(Color.WHITE);
        this.menu.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.menu.setFocusPainted(false); 
        this.menu.setBorderPainted(false); 
        this.menu.setContentAreaFilled(false);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(2, 2, 2, 2);
        this.add(menu, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(level_1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(level_2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(level_3, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        this.add(level_4, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(level_5, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(level_6, gbc);

        this.menu.addActionListener((event) -> {
            this.game_frame.getCardlayout().show(this.game_frame.getPanelContainer(), "classicGame");
        });
        this.addMouseListener(menu);

    }


    private JButton createStyledButton(String level, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        JButton button = new JButton(level, scaledIcon);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);


        button.setPreferredSize(new Dimension(400, 80));
        button.setMaximumSize(new Dimension(400, 80));
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        button.setContentAreaFilled(false);
        return button;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}

