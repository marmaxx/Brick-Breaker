package display.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import display.engine.PhysicsEngine;
import game.breakout.Breakout;
import game.breakout.entities.Player;

public class SettingsPanel extends JPanel{
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension SETTINGS_ZONE = new Dimension(SCREEN_FULL_SIZE.width*4/5, SCREEN_FULL_SIZE.height*9/10);
    public static final int GRAVITY_DEFAULT_VALUE = 5;
    public static final int REBOUND_DEFAULT_VALUE = 100;
    public static final int FRICTION_DEFAULT_VALUE = 5;
    public static final int PADDLE_SPEED_DEFAULT_VALUE = 10;

    private JLabel gravityLabel = createStyledLabel("Force de gravité: ");
    private JLabel gravityValueLabel = createStyledLabel(String.valueOf(GRAVITY_DEFAULT_VALUE));
    private JSlider gravitySlider = createStyledSlider("Gravité", 0, 10, 5);    

    private JLabel reboundValueLabel = createStyledLabel(String.valueOf(REBOUND_DEFAULT_VALUE));
    private JLabel reboundLabel = createStyledLabel("Force du rebond: ");
    private JSlider reboundSlider = createStyledSlider("Rebond", 0, 200, 100);

    private JLabel frictionValueLabel = createStyledLabel(String.valueOf(FRICTION_DEFAULT_VALUE));
    private JLabel frictionLabel = createStyledLabel("Force de frottements: ");
    private JSlider frictionSlider = createStyledSlider("Frottements", 0, 10, 5);

    private JButton submitButton = createStyledButton("Valider");
    private JButton reinitializeButton = createStyledButton("Réinitialiser");
    private JButton backButton = createStyledButton("Retour");

    private JLabel paddleSpeedLabel = createStyledLabel("Vitesse du paddle:");
    private JLabel paddleSpeedValueLabel = createStyledLabel(String.valueOf(PADDLE_SPEED_DEFAULT_VALUE));
    private JSlider paddleSpeedSlider = createStyledSlider("PaddleSpeed", 5, 30, 10);

    private double gravityValue = GRAVITY_DEFAULT_VALUE;
    private double reboundValue = REBOUND_DEFAULT_VALUE;
    private double frictionValue = FRICTION_DEFAULT_VALUE;
    private int paddleSpeedValue = PADDLE_SPEED_DEFAULT_VALUE;
    private BufferedImage backgroundImage;
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
    private JPanel sliderContainer = new JPanel();
    private JPanel buttonContainer = new JPanel();
    private JPanel gravitySliderContainer = new JPanel();
    private JPanel reboundSliderContainer = new JPanel();
    private JPanel frictionSliderContainer = new JPanel();
    private JPanel paddleSpeedSliderContainer = new JPanel();
    
    public SettingsPanel(GameFrame gameFrame){

        try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Settings.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.gravitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event){
                setGravityValue(gravitySlider.getValue());
                gravityValueLabel.setText(String.valueOf(gravityValue));
            }
        });

        this.reboundSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event){
                setReboundValue(reboundSlider.getValue());
                reboundValueLabel.setText(String.valueOf(reboundValue));
            }
        });

        this.frictionSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event){
                setFrictionValue(frictionSlider.getValue());
                frictionValueLabel.setText(String.valueOf(frictionValue));
            }
        });

        this.paddleSpeedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event){
                setPaddleSpeedValue(paddleSpeedSlider.getValue());
                paddleSpeedValueLabel.setText(String.valueOf(paddleSpeedValue));
            }
        });

        this.submitButton.addActionListener((event) -> {
            PhysicsEngine.GRAVITY_CONSTANT = (gravityValue/10);
            PhysicsEngine.rebondForce = reboundValue;
            PhysicsEngine.FRICTION_COEFFICIENT = frictionValue;
            Player.DEFAULT_SPEED= paddleSpeedValue;
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
        });
        
        this.reinitializeButton.addActionListener((event) -> {
            this.gravitySlider.setValue(GRAVITY_DEFAULT_VALUE);
            this.reboundSlider.setValue(REBOUND_DEFAULT_VALUE);
            this.frictionSlider.setValue(FRICTION_DEFAULT_VALUE);
            this.paddleSpeedSlider.setValue(PADDLE_SPEED_DEFAULT_VALUE);
            PhysicsEngine.GRAVITY_CONSTANT = 0.5;
            PhysicsEngine.rebondForce = REBOUND_DEFAULT_VALUE;
            PhysicsEngine.FRICTION_COEFFICIENT = 0.5;
            Player.DEFAULT_SPEED = PADDLE_SPEED_DEFAULT_VALUE;
        });

        this.backButton.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
        });

        this.submitButton.addMouseListener(new ButtonMouseListener(this.submitButton));
        this.reinitializeButton.addMouseListener(new ButtonMouseListener(this.reinitializeButton));
        this.backButton.addMouseListener(new ButtonMouseListener(this.backButton));

        this.sliderContainer.setLayout(new BoxLayout(this.sliderContainer, BoxLayout.Y_AXIS));
        this.sliderContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.sliderContainer.setBackground(new Color(30,30,30,0));
        
        this.gravitySliderContainer.setBackground(new Color(30,30,30,0));
        this.gravitySliderContainer.add(this.gravityLabel);
        this.gravitySliderContainer.add(this.gravitySlider);
        this.gravitySliderContainer.add(this.gravityValueLabel);

        this.reboundSliderContainer.setBackground(new Color(30,30,30,0));
        this.reboundSliderContainer.add(this.reboundLabel);
        this.reboundSliderContainer.add(this.reboundSlider);
        this.reboundSliderContainer.add(this.reboundValueLabel);
        
        this.frictionSliderContainer.setBackground(new Color(30,30,30,0));
        this.frictionSliderContainer.add(this.frictionLabel);
        this.frictionSliderContainer.add(this.frictionSlider);
        this.frictionSliderContainer.add(this.frictionValueLabel);

        this.paddleSpeedSliderContainer.setBackground(new Color(30,30,30,0));
        this.paddleSpeedSliderContainer.add(this.paddleSpeedLabel);
        this.paddleSpeedSliderContainer.add(this.paddleSpeedSlider);
        this.paddleSpeedSliderContainer.add(this.paddleSpeedValueLabel);

        this.sliderContainer.add(Box.createVerticalGlue());

        this.sliderContainer.add(Box.createVerticalStrut(300));
        this.sliderContainer.add(this.gravitySliderContainer);
        this.sliderContainer.add(Box.createVerticalStrut(10));

        this.sliderContainer.add(this.reboundSliderContainer);
        this.sliderContainer.add(Box.createVerticalStrut(10));

        this.sliderContainer.add(this.frictionSliderContainer);
        this.sliderContainer.add(Box.createVerticalStrut(10));

        this.sliderContainer.add(this.paddleSpeedSliderContainer);
        this.sliderContainer.add(Box.createVerticalStrut(10));

        this.sliderContainer.add(Box.createVerticalGlue());

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
        this.mainContainer.add(this.sliderContainer, BorderLayout.NORTH);
        this.mainContainer.add(this.buttonContainer, BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(SETTINGS_ZONE);
        
        this.add(mainContainer, BorderLayout.CENTER);        
    }

    private JSlider createStyledSlider (String text, int min, int max, int value){
        JSlider slider = new JSlider(min, max, value);
        //slider.setBackground(Color.WHITE);
        //slider.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
        //slider.setUI(new CustomSliderUI(slider));
        return slider;
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

    private JLabel createStyledLabel (String text){
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Ubuntu", Font.BOLD, 22));
        return label;
    }

    private void setGravityValue(double value){
        this.gravityValue =value;
    }

    private void setReboundValue(double value){
        this.reboundValue =value;
    }

    private void setFrictionValue(double value){
        this.frictionValue =value;
    }

    private void setPaddleSpeedValue(int value){
        this.paddleSpeedValue = value;
    }

    public class CustomSliderUI extends BasicSliderUI {
        private BufferedImage thumbImage;

        public CustomSliderUI(JSlider b) {
            super(b);
            try {
                thumbImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "planet.png")); ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void paintThumb(Graphics g) {
            if (thumbImage != null) {
                g.drawImage(thumbImage, 0, 0, 50, 50, null);
            } else {
                super.paintThumb(g);
            }
        }
    }
}
