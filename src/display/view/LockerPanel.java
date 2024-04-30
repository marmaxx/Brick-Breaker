package display.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


import game.breakout.Breakout;
import game.breakout.entities.Ball;
import game.breakout.entities.Player;

public class LockerPanel extends JPanel{
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension LOCKER_ZONE = new Dimension(SCREEN_FULL_SIZE.width*4/5, SCREEN_FULL_SIZE.height*9/10);
    
    private BufferedImage backgroundImage;
    private ImageIcon ballImage;
    private ImageIcon trailImage;
    private JPanel mainContainer =  new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dessiner l'image de fond
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    };

    private JPanel ballPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dessiner l'image de fond
            if (ballImage != null) {
                Image image = ballImage.getImage();
                g.drawImage(image, 0, 0, 0, 0, this);
            }
        }
    };

    private JPanel trailPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dessiner l'image de fond
            if (trailImage != null) {
                Image image = trailImage.getImage();
                g.drawImage(image, 0, 0, 0, 0, this);
            }
        }
    };

    private JPanel paddlePanel = new JPanel();

    private JLabel ballLabel = createStyledLabel("Balle:    ");
    private JLabel trailLabel = createStyledLabel("Trainée:");
    private JLabel paddleLabel = createStyledLabel("Paddle: ");
    private JButton leftBallButton = createStyledButton(30, 27, "left_arrow.png");
    private JButton rightBallButton = createStyledButton(30, 27, "right_arrow.png");
    private JButton leftTrailButton = createStyledButton(30, 27, "left_arrow.png");
    private JButton rightTrailButton = createStyledButton(30, 27, "right_arrow.png");
    private JButton leftPaddleButton = createStyledButton(30, 27, "left_arrow.png");
    private JButton rightPaddleButton = createStyledButton(30, 27, "right_arrow.png");
    private JButton submitButton = createStyledButton("Valider");
    private JButton reinitializeButton = createStyledButton("Réinitialiser");
    private JButton backButton = createStyledButton("Retour");

    private String[] ballOptions = {"Meteorite", "earth", "mars", "venus"};
    private String[] colorOptions = {"white", "yellow", "red", "green", "blue"};

    private int ballCurrentIndex = 0;
    private int trailCurrentColorIndex = 0;
    private int paddleCurrentColorIndex = 0;

    public LockerPanel(GameFrame gameFrame){
        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Settings.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        ballPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dessiner l'image de fond
                if (ballImage != null) {
                    Image image = ballImage.getImage();
                    g.drawImage(image, 0, 0, 160, 160, this);
                }
            }
        };
        ballPanel.setBackground(new Color(0,0,0,0));

        trailPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dessiner l'image de fond
                if (trailImage != null) {
                    Image image = trailImage.getImage();
                    g.drawImage(image, 0, 0, 160, 160, this);
                }
            }
        };
        trailPanel.setBackground(new Color(0,0,0,0));

        ballImage = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Meteorite.png");
        trailImage = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "redTrail.png");

        this.leftBallButton.addActionListener((event) -> {
            this.repaint();
            ballPreviousColor();
            ballImage = createBallImageFrom(ballCurrentIndex);
        });

        this.rightBallButton.addActionListener((event) -> {
            ballNextColor();
            ballImage = createBallImageFrom(ballCurrentIndex);
            this.repaint();
        });

        this.leftTrailButton.addActionListener((event) -> {
            trailPreviousColor();
            trailImage = createTrailImageFrom(trailCurrentColorIndex);
            this.repaint();
        });

        this.rightTrailButton.addActionListener((event) -> {
            trailNextColor();
            trailImage = createTrailImageFrom(trailCurrentColorIndex);            
            this.repaint();
        });

        this.leftPaddleButton.addActionListener((event) -> {
            paddlePreviousColor();
            this.paddlePanel.setBackground(createColorFrom(paddleCurrentColorIndex));
            this.repaint();
        });

        this.rightPaddleButton.addActionListener((event) -> {
            paddleNextColor();
            this.paddlePanel.setBackground(createColorFrom(paddleCurrentColorIndex));
            this.repaint();            
        });

        this.submitButton.addActionListener((event) -> {
            Ball.DEFAULT_IMAGE = createBallImageFrom(ballCurrentIndex).getImage();
            Player.DEFAULT_COLOR = createColorFrom(paddleCurrentColorIndex);
            Ball.DEFAULT_TRAIL_COLOR = createColorFrom(trailCurrentColorIndex);
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
            this.repaint();
        });

        this.reinitializeButton.addActionListener((event) -> {
            Ball.DEFAULT_IMAGE = createBallImageFrom(0).getImage();
            Ball.DEFAULT_TRAIL_COLOR = createColorFrom(0);
            Player.DEFAULT_COLOR = createColorFrom(2);
            ballCurrentIndex = 0;
            trailCurrentColorIndex = 2;
            paddleCurrentColorIndex = 0;
            this.paddlePanel.setBackground(createColorFrom(paddleCurrentColorIndex));
            trailImage = createTrailImageFrom(trailCurrentColorIndex);
            this.trailPanel.repaint();
            ballImage = createBallImageFrom(ballCurrentIndex);
            this.ballPanel.repaint();
            this.repaint();
        });

        this.backButton.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
            this.repaint();
        });
        
        this.mainContainer.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(2, 2, 2, 2);
        this.mainContainer.add(emptyJButton(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.mainContainer.add(emptyJButton(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.mainContainer.add(this.ballLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        this.mainContainer.add(this.leftBallButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        this.mainContainer.add(this.ballPanel, gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        this.mainContainer.add(this.rightBallButton, gbc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        this.mainContainer.add(emptyJButton(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.mainContainer.add(emptyJButton(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.mainContainer.add(this.trailLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        this.mainContainer.add(this.leftTrailButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        this.mainContainer.add(this.trailPanel, gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        this.mainContainer.add(this.rightTrailButton, gbc);

        gbc.gridx = 5;
        gbc.gridy = 2;
        this.mainContainer.add(emptyJButton(), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.mainContainer.add(emptyJButton(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        this.mainContainer.add(this.paddleLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        this.mainContainer.add(this.leftPaddleButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        this.mainContainer.add(this.paddlePanel, gbc);

        gbc.gridx = 4;
        gbc.gridy = 3;
        this.mainContainer.add(this.rightPaddleButton, gbc);

        gbc.gridx = 5;
        gbc.gridy = 3;
        this.mainContainer.add(emptyJButton(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.mainContainer.add(emptyJButton(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        this.mainContainer.add(this.submitButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        this.mainContainer.add(this.reinitializeButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        this.mainContainer.add(this.backButton, gbc);

        gbc.gridx = 4;
        gbc.gridy = 4;
        this.mainContainer.add(emptyJButton(), gbc);

        this.setLayout(new BorderLayout());
        this.add(this.mainContainer, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        button.setContentAreaFilled(false);
        return button;
    }
    
    private JButton createStyledButton(int width, int height, String url){
        ImageIcon imageIcon = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + url);
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(image));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        button.setContentAreaFilled(false);       
        return button;
    }

    private JLabel createStyledLabel (String text){
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Ubuntu", Font.BOLD, 22));
        return label;
    }

    private void ballPreviousColor() {
        ballCurrentIndex--;
        if (ballCurrentIndex < 0) {
            ballCurrentIndex = ballOptions.length - 1;
        }
    }

    private void ballNextColor() {
        ballCurrentIndex++;
        if (ballCurrentIndex >= ballOptions.length) {
            ballCurrentIndex = 0;
        }
    }

    private void trailPreviousColor() {
        trailCurrentColorIndex--;
        if (trailCurrentColorIndex < 0) {
            trailCurrentColorIndex = colorOptions.length - 1;
        }
    }

    private void trailNextColor() {
        trailCurrentColorIndex++;
        if (trailCurrentColorIndex >= colorOptions.length) {
            trailCurrentColorIndex = 0;
        }
    }

    private void paddlePreviousColor() {
        paddleCurrentColorIndex--;
        if (paddleCurrentColorIndex < 0) {
            paddleCurrentColorIndex = colorOptions.length - 1;
        }
    }

    private void paddleNextColor() {
        paddleCurrentColorIndex++;
        if (paddleCurrentColorIndex >= colorOptions.length) {
            paddleCurrentColorIndex = 0;
        }
    }

    private Color createColorFrom(int index){
        Color color = Color.WHITE;
        switch(index){
            case 0: color = Color.WHITE; break;
            case 1: color = Color.YELLOW; break;
            case 2: color = Color.RED; break;
            case 3: color = Color.GREEN; break;
            case 4: color = Color.BLUE; break;
        }
        return color;
    }

    private ImageIcon createBallImageFrom(int index){
        return new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + ballOptions[index] + ".png");
    }

    private ImageIcon createTrailImageFrom(int index){
        return new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + colorOptions[index] + "Trail.png");
    }

    private JButton emptyJButton (){
        JButton b = new JButton();
        b.setFocusPainted(false); 
        b.setBorderPainted(false); 
        b.setContentAreaFilled(false);
        return b;
    }
}
