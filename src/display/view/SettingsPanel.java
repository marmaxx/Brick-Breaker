package display.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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


    private JButton vide = new JButton();

    private double gravityValue = GRAVITY_DEFAULT_VALUE;
    private double reboundValue = REBOUND_DEFAULT_VALUE;
    private double frictionValue = FRICTION_DEFAULT_VALUE;
    private int paddleSpeedValue = PADDLE_SPEED_DEFAULT_VALUE;
    private BufferedImage backgroundImage;

    private JPanel mainContainer = new JPanel() {
        @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessiner l'image de fond
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    };

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
                repaint();
            }
        });

        this.reboundSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event){
                setReboundValue(reboundSlider.getValue());
                reboundValueLabel.setText(String.valueOf(reboundValue));
                repaint();
            }
        });

        this.frictionSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event){
                setFrictionValue(frictionSlider.getValue());
                frictionValueLabel.setText(String.valueOf(frictionValue));
                repaint();
            }
        });

        this.paddleSpeedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event){
                setPaddleSpeedValue(paddleSpeedSlider.getValue());
                paddleSpeedValueLabel.setText(String.valueOf(paddleSpeedValue));
                repaint();
            }
        });

        this.submitButton.addActionListener((event) -> {
            PhysicsEngine.GRAVITY_CONSTANT = (gravityValue/10);
            PhysicsEngine.rebondForce = reboundValue;
            PhysicsEngine.FRICTION_COEFFICIENT = frictionValue;
            Player.DEFAULT_SPEED= paddleSpeedValue;
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
            this.repaint();
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
            this.repaint();
        });

        this.backButton.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
        });

        this.submitButton.addMouseListener(new ButtonMouseListener(this.submitButton));
        this.reinitializeButton.addMouseListener(new ButtonMouseListener(this.reinitializeButton));
        this.backButton.addMouseListener(new ButtonMouseListener(this.backButton));

        this.mainContainer.setLayout(new GridBagLayout());

        this.vide.setFocusPainted(false); 
        this.vide.setBorderPainted(false); 
        this.vide.setContentAreaFilled(false);

        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(1, 1, 1, 1);
        this.mainContainer.add(this.vide, gbc); 

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.mainContainer.add(this.gravityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.mainContainer.add(this.reboundLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        this.mainContainer.add(this.gravitySlider, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        this.mainContainer.add(this.gravityValueLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        this.mainContainer.add(this.reboundSlider, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        this.mainContainer.add(this.reboundValueLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        this.mainContainer.add(this.frictionLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        this.mainContainer.add(this.frictionSlider, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        this.mainContainer.add(this.frictionValueLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        this.mainContainer.add(this.paddleSpeedLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        this.mainContainer.add(this.paddleSpeedSlider, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        this.mainContainer.add(this.paddleSpeedValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.mainContainer.add(this.submitButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        this.mainContainer.add(this.reinitializeButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        this.mainContainer.add(this.backButton, gbc); 

        this.setLayout(new BorderLayout());
        this.add(this.mainContainer, BorderLayout.CENTER);
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
}
