package display.view;

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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import game.breakout.Breakout;
import game.breakout.entities.Ball;
import game.breakout.entities.Player;

public class LockerPanel extends JPanel{
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension LOCKER_ZONE = new Dimension(SCREEN_FULL_SIZE.width*4/5, SCREEN_FULL_SIZE.height*9/10);
    
    private BufferedImage backgroundImage;
    private BufferedImage ballImage;
    private BufferedImage trailImage;
    private BufferedImage paddleImage;
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

    private JPanel ballPanel = new JPanel();/* {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dessiner l'image de fond
            if (ballImage != null) {
                g.drawImage(ballImage, 0, 0, 100, 100, this);
            }
        }
    };*/

    private JPanel trailPanel = new JPanel(); /*{
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dessiner l'image de fond
            if (trailImage != null) {
                g.drawImage(trailImage, 0, 0, 0, 0, this);
            }
        }
    };*/

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

    private JLabel ballLabel = createStyledLabel("Balle:");
    private JLabel trailLabel = createStyledLabel("Trainée:");
    private JLabel paddleLabel = createStyledLabel("Paddle:");
    private JButton leftBallButton = createStyledButton(30, 27, "left_arrow.png");
    private JButton rightBallButton = createStyledButton(30, 27, "right_arrow.png");
    private JButton leftTrailButton = createStyledButton(30, 27, "left_arrow.png");
    private JButton rightTrailButton = createStyledButton(30, 27, "right_arrow.png");
    private JButton leftPaddleButton = createStyledButton(30, 27, "left_arrow.png");
    private JButton rightPaddleButton = createStyledButton(30, 27, "right_arrow.png");
    private JButton submitButton = createStyledButton("Valider");
    private JButton reinitializeButton = createStyledButton("Réinitialiser");
    private JButton backButton = createStyledButton("Retour");

    private String[] colorOptions = {"Blanc", "Jaune", "Rouge", "Vert", "Bleu"};
    private int ballCurrentColorIndex = 0;
    private int trailCurrentColorIndex = 2;
    private int paddleCurrentColorIndex = 0;

    public LockerPanel(GameFrame gameFrame){
        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Settings.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ballImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            trailImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "ball.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            paddleImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "ball.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.leftBallButton.addActionListener((event) -> {
            ballPreviousColor();
            this.ballPanel.setBackground(createColorFrom(ballCurrentColorIndex));
        });

        this.rightBallButton.addActionListener((event) -> {
            ballNextColor();
            this.ballPanel.setBackground(createColorFrom(ballCurrentColorIndex));            
        });

        this.leftTrailButton.addActionListener((event) -> {
            trailPreviousColor();
            this.trailPanel.setBackground(createColorFrom(trailCurrentColorIndex));
        });

        this.rightTrailButton.addActionListener((event) -> {
            trailNextColor();            
            this.trailPanel.setBackground(createColorFrom(trailCurrentColorIndex));
        });

        this.leftPaddleButton.addActionListener((event) -> {
            paddlePreviousColor();
            this.paddlePanel.setBackground(createColorFrom(paddleCurrentColorIndex));
        });

        this.rightPaddleButton.addActionListener((event) -> {
            paddleNextColor();
            this.paddlePanel.setBackground(createColorFrom(paddleCurrentColorIndex));            
        });

        this.submitButton.addActionListener((event) -> {
            Ball.DEFAULT_COLOR = createColorFrom(ballCurrentColorIndex);
            Player.DEFAULT_COLOR = createColorFrom(paddleCurrentColorIndex);
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
        });

        this.reinitializeButton.addActionListener((event) -> {
            Ball.DEFAULT_COLOR = createColorFrom(0);
            Player.DEFAULT_COLOR = createColorFrom(0);
            ballCurrentColorIndex = 0;
            paddleCurrentColorIndex = 0;
            this.paddlePanel.setBackground(createColorFrom(paddleCurrentColorIndex));
            this.ballPanel.setBackground(createColorFrom(ballCurrentColorIndex)); 
        });
        this.backButton.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
        });
        
        this.ballPanel.setBackground(Color.WHITE);
        this.trailPanel.setBackground(Color.RED);
        this.paddlePanel.setBackground(Color.WHITE);

        this.lockerContainer.setLayout(new BoxLayout(this.lockerContainer, BoxLayout.Y_AXIS));
        this.lockerContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.lockerContainer.setBackground(new Color(30,30,30,0));

        this.ballContainer.setBackground(new Color(30,30,30,0));
        this.ballContainer.add(this.ballLabel);
        this.ballContainer.add(this.leftBallButton);
        this.ballContainer.add(this.ballPanel);
        this.ballContainer.add(this.rightBallButton);

        this.trailContainer.setBackground(new Color(30,30,30,0));
        this.trailContainer.add(this.trailLabel);
        this.trailContainer.add(this.leftTrailButton);
        this.trailContainer.add(this.trailPanel);
        this.trailContainer.add(this.rightTrailButton);

        this.paddleContainer.setBackground(new Color(30,30,30,0));
        this.paddleContainer.add(this.paddleLabel);
        this.paddleContainer.add(this.leftPaddleButton);
        this.paddleContainer.add(this.paddlePanel);
        this.paddleContainer.add(this.rightPaddleButton);

        this.lockerContainer.add(Box.createVerticalGlue());

        this.lockerContainer.add(Box.createVerticalStrut(200));
        this.lockerContainer.add(this.ballContainer);
        this.lockerContainer.add(Box.createVerticalStrut(10));

        this.lockerContainer.add(Box.createVerticalStrut(10));
        this.lockerContainer.add(this.trailContainer);

        this.lockerContainer.add(Box.createVerticalStrut(10));
        this.lockerContainer.add(this.paddleContainer);

        this.lockerContainer.add(Box.createVerticalGlue());

        this.buttonContainer.setLayout(new BoxLayout(this.buttonContainer, BoxLayout.X_AXIS));
        this.buttonContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.buttonContainer.setBackground(new Color(30,30,30,0));

        this.buttonContainer.add(Box.createHorizontalGlue());
        this.buttonContainer.add(this.submitButton);
        this.buttonContainer.add(Box.createHorizontalStrut(20));
        this.buttonContainer.add(this.reinitializeButton);
        this.buttonContainer.add(Box.createHorizontalStrut(20));
        this.buttonContainer.add(this.backButton);
        this.buttonContainer.add(Box.createHorizontalGlue());

        this.mainContainer.setLayout(new BorderLayout());
        this.mainContainer.add(this.lockerContainer, BorderLayout.CENTER);
        this.mainContainer.add(this.buttonContainer, BorderLayout.SOUTH);
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
        ballCurrentColorIndex--;
        if (ballCurrentColorIndex < 0) {
            ballCurrentColorIndex = colorOptions.length - 1;
        }
    }

    private void ballNextColor() {
        ballCurrentColorIndex++;
        if (ballCurrentColorIndex >= colorOptions.length) {
            ballCurrentColorIndex = 0;
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
}
