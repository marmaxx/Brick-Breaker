package display.view;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import game.breakout.Breakout;

public class GamePanel extends JPanel {
	private static final Color GAME_BACKGROUND_COLOR = new Color(30,30,30);
	public static final Dimension SCREEN_FULL_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Dimension GAME_ZONE_SIZE = new Dimension(SCREEN_FULL_SIZE.width*4/5, SCREEN_FULL_SIZE.height*9/10);
	public static final Dimension STAT_ZONE_GAME = new Dimension(SCREEN_FULL_SIZE.width,SCREEN_FULL_SIZE.height/10);
	
	private GameFrame gameFrame;
    private JPanel gameZone = new JPanel(){
         @Override
         protected void paintComponent(Graphics g) {
             super.paintComponent(g);
             // Dessiner l'image de fond
             if (backgroundImage != null) {
                 g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
             }
         }
    };
    private JPanel statZone = new JPanel();
	private JLabel score = new JLabel();
    private JLabel life = new JLabel();
    private JLabel nbBricks = new JLabel();
	private JButton menu = createStyledButton("MENU");
	private MenuInGame menuInGame; 
	JLayeredPane layeredPane = new JLayeredPane();

	private BufferedImage backgroundImage; // background image 
    
	/**
	 * Instantiates a new GamePanel
	 * 
	 * @param gameFrame The frame in which the panel is displayed
	 * @param color The background color of the panel
	 */
    public GamePanel(GameFrame gameFrame, Color color) {

		try {
            backgroundImage = ImageIO.read(new File(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "BackGround.jpg")); 
        } catch (IOException e) {
            e.printStackTrace();
        }


		this.setFrame(gameFrame);
		this.setBackground(color);
		this.setLayout(new FlowLayout()); //set GamePanel to FlowLayout
		this.setPreferredSize(SCREEN_FULL_SIZE);

		this.gameZone.setPreferredSize(GAME_ZONE_SIZE);

		this.statZone.setPreferredSize(STAT_ZONE_GAME);
		this.statZone.setBackground(new Color(30,30,30,150));
		this.statZone.setLayout(new FlowLayout()); // set StatZone to flow Layout 

		this.score.setPreferredSize(new Dimension(200, 100));
        this.score.setForeground(Color.WHITE); // set color of the text
        this.life.setPreferredSize(new Dimension(200, 100));
        this.life.setForeground(Color.WHITE); // set color of the text
        this.nbBricks.setPreferredSize(new Dimension(200, 100));
        this.nbBricks.setForeground(Color.WHITE); // set color of the text

		this.menu.addMouseListener(new ButtonMouseListener(this.menu));

        this.menuInGame = new MenuInGame(this.gameFrame,this);
        this.menuInGame.setVisible(false);


       
        
        // layeredPane.setPreferredSize(GAME_ZONE_SIZE);
        // this.menuInGame.setBounds(0, 0, GAME_ZONE_SIZE.width, GAME_ZONE_SIZE.height);
        // layeredPane.add(this.menuInGame, JLayeredPane.POPUP_LAYER);

		this.menu.addActionListener((event) -> {
			this.getFrame().getGame().pause();
			this.gameZone.setVisible(false);
			this.menuInGame.setVisible(true);
		});


		this.statZone.add(this.score);
        this.statZone.add(this.life);
        this.statZone.add(this.nbBricks);
		this.statZone.add(this.menu);

		this.add(statZone);
		this.add(gameZone);
		this.add(menuInGame);
    }

	/**s
	 * Instantiates a new GamePanel
	 * 
	 * @param gameFrame The frame in which the panel is displayed
	 */
	public GamePanel(GameFrame gameFrame) {
		this(gameFrame, GamePanel.GAME_BACKGROUND_COLOR);
	}

	/**
	 * Get the frame in which the panel is displayed
	 * 
	 * @return The frame in which the panel is displayed
	 */
	public GameFrame getFrame() {
		return this.gameFrame;
	}

	/**
	 * Set the frame in which the panel is displayed
	 * 
	 * @param gameFrame The frame in which the panel is displayed
	 */
	private void setFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	/**
	 * Get the JPanel in which the game is displayed
	 * 
	 * @return The JPanel in which the game is displayed
	 */
	public JPanel getGameZone(){
		return this.gameZone;
	}

	/**
	 * Get the JPanel in which the menu is displayed
	 * 
	 * @return The JPanel in which the menu is displayed
	 */
	public JPanel getMenu(){
		return this.menuInGame;
	}



	/** 
	 * update game life in JLabel
	 * @param life The Life in game
	 * @param bricks The number of brick in game
	*/
	public void updateLife(int life){
		this.life.setText("Life : " + life);
		//this.statZone.repaint(); // Redraw the statZone
	}

	/**
	 * update game score and number of bricks in JLabel
	 * @param score The score in game
	 * @param bricks The number of bricks 
	 */
	public void updateScore(int score, int bricks){
		//this.score.setText("Score : " + score);
		//this.nbBricks.setText("Bricks : " + bricks);
		//this.statZone.repaint(); // Redraw the statZone
	}


	public void resumeGamePanel(){
		this.menuInGame.setVisible(false);
		this.gameZone.setVisible(true);
	}

	private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Ubuntu", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        return button;
    }

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
	
}