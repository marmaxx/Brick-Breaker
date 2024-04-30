package display.view.brickbreakerview;

import display.view.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

    private JPanel paddlePanel = new JPanel();/* {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dessiner l'image de fond
            if (paddleImage != null) {
                g.drawImage(paddleImage, 0, 0, 0, 0, this);
            }
        }*/
    
    private JPanel ballContainer = new JPanel();
    private JPanel trailContainer = new JPanel();
    private JPanel paddleContainer = new JPanel();
    private JPanel lockerContainer = new JPanel();
    private JPanel buttonContainer = new JPanel();

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
                    g.drawImage(image, 0, 0, 100, 100, this);
                }
            }
        };

        trailPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dessiner l'image de fond
                if (trailImage != null) {
                    Image image = trailImage.getImage();
                    g.drawImage(image, 0, 0, 100, 100, this);
                }
            }
        };

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
        
        this.ballPanel.setBackground(new Color(30,30,30,0));
        this.ballPanel.setPreferredSize(new Dimension(100,100));
        this.trailPanel.setBackground(new Color(30,30,30,0));
        this.trailPanel.setPreferredSize(new Dimension(100,100));
        this.paddlePanel.setBackground(Color.WHITE);
        this.paddlePanel.setPreferredSize(new Dimension(100,100));

        this.lockerContainer.setLayout(new BoxLayout(this.lockerContainer, BoxLayout.Y_AXIS));
        this.lockerContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.lockerContainer.setBackground(new Color(0,0,0,0));

        this.ballContainer.setBackground(new Color(0,0,0,0));
        this.ballContainer.setPreferredSize(new Dimension(200, 200));
        this.ballContainer.add(this.ballLabel);
        this.ballContainer.add(this.leftBallButton);
        this.ballContainer.add(this.ballPanel);
        this.ballContainer.add(this.rightBallButton);

        this.trailContainer.setBackground(new Color(0,0,0,0));
        this.trailContainer.setPreferredSize(new Dimension(200,200));
        this.trailContainer.add(this.trailLabel);
        this.trailContainer.add(this.leftTrailButton);
        this.trailContainer.add(this.trailPanel);
        this.trailContainer.add(this.rightTrailButton);

        this.paddleContainer.setBackground(new Color(0,0,0,0));
        this.paddleContainer.setPreferredSize(new Dimension(200,200));
        this.paddleContainer.add(this.paddleLabel);
        this.paddleContainer.add(this.leftPaddleButton);
        this.paddleContainer.add(this.paddlePanel);
        this.paddleContainer.add(this.rightPaddleButton);

        this.lockerContainer.add(Box.createVerticalGlue());

        this.lockerContainer.add(Box.createVerticalStrut(200));
        this.lockerContainer.add(this.ballContainer);

        this.lockerContainer.add(Box.createVerticalStrut(1));
        this.lockerContainer.add(this.trailContainer);

        this.lockerContainer.add(Box.createVerticalStrut(1));
        this.lockerContainer.add(this.paddleContainer);

        this.lockerContainer.add(Box.createVerticalGlue());

        this.buttonContainer.setLayout(new BoxLayout(this.buttonContainer, BoxLayout.X_AXIS));
        this.buttonContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.buttonContainer.setBackground(new Color(0,0,0,0));

        this.buttonContainer.add(Box.createHorizontalGlue());
        this.buttonContainer.add(this.submitButton);
        this.buttonContainer.add(Box.createHorizontalStrut(20));
        this.buttonContainer.add(this.reinitializeButton);
        this.buttonContainer.add(Box.createHorizontalStrut(20));
        this.buttonContainer.add(this.backButton);
        this.buttonContainer.add(Box.createHorizontalGlue());

        this.mainContainer.setLayout(new BoxLayout(this.mainContainer, BoxLayout.Y_AXIS));
        this.lockerContainer.add(Box.createVerticalGlue());
        this.mainContainer.add(Box.createVerticalStrut(50));
        this.mainContainer.add(this.lockerContainer);
        this.mainContainer.add(Box.createVerticalStrut(5));
        this.mainContainer.add(this.buttonContainer);
        this.lockerContainer.add(Box.createVerticalGlue());
        this.setLayout(new BorderLayout());
        this.setPreferredSize(LOCKER_ZONE);
        this.add(mainContainer, BorderLayout.CENTER);
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
}
