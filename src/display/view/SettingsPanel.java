package display.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import display.engine.PhysicsEngine;
import game.breakout.Breakout;

public class SettingsPanel extends JPanel{
    public static final Dimension BUTTON_SIZE = new Dimension(300,100); 
    public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension SETTINGS_ZONE = new Dimension(SCREEN_FULL_SIZE.width*4/5, SCREEN_FULL_SIZE.height*9/10);

    private JLabel gravityLabel = createStyledLabel("Force de gravité: ");
    private JSlider gravitySlider = createStyledSlider("Gravité", 0, 10, 5);
    private JLabel reboundLabel = createStyledLabel("Force du rebond: ");
    private JSlider reboundSlider = createStyledSlider("Rebond", 0, 10, 5);
    private JLabel frictionLabel = createStyledLabel("Force de frottements: ");
    private JSlider frictionSlider = createStyledSlider("Frottements", 0, 10, 5);
    private JButton backButton = createStyledButton("Retour");
    private BufferedImage backgroundImage;
    private JPanel sliderContainer =  new JPanel() {
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
                PhysicsEngine.GRAVITY_CONSTANT = gravitySlider.getValue();
            }
        });

        this.reboundSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event){
                PhysicsEngine.rebondForce = reboundSlider.getValue();
            }
        });

        this.frictionSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event){
                PhysicsEngine.FRICTION_COEFFICIENT = frictionSlider.getValue();
            }
        });

        this.backButton.addActionListener((event) -> {
            gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "classicGame");
        });

        this.sliderContainer.setLayout(new BoxLayout(this.sliderContainer, BoxLayout.Y_AXIS));
        this.sliderContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.sliderContainer.add(Box.createVerticalGlue());
        
        this.sliderContainer.add(this.gravityLabel);
        this.sliderContainer.add(Box.createVerticalStrut(40));
        this.sliderContainer.add(this.gravitySlider);
        this.sliderContainer.add(Box.createVerticalStrut(40));
        
        this.sliderContainer.add(this.reboundLabel);
        this.sliderContainer.add(Box.createVerticalStrut(40));
        this.sliderContainer.add(this.reboundSlider);
        this.sliderContainer.add(Box.createVerticalStrut(40));
        
        this.sliderContainer.add(this.frictionLabel);
        this.sliderContainer.add(Box.createVerticalStrut(40));
        this.sliderContainer.add(this.frictionSlider);
        this.sliderContainer.add(Box.createVerticalStrut(40));

        this.sliderContainer.add(this.backButton);
        this.sliderContainer.add(Box.createVerticalGlue());

        this.setLayout(new BorderLayout());
        this.setPreferredSize(SETTINGS_ZONE);
        this.add(sliderContainer, BorderLayout.CENTER);

    }

    private JSlider createStyledSlider (String text, int min, int max, int value){
        JSlider slider = new JSlider(min, max, value);
        //slider.setBackground(Color.WHITE);
        slider.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
        //slider.setUI(new CustomSliderUI(slider));
        return slider;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        return button;
    }

    private JLabel createStyledLabel (String text){
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Ubuntu", Font.BOLD, 18));
        return label;
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
