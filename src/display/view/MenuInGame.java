package display.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.io.Serializable;


public class MenuInGame extends JPanel implements Serializable{
    public static final long serialVersionUID = 54L;

    private JPanel menuInGameSquarePane = new JPanel(new BorderLayout());;
    private JButton resumeButton = createButton("Reprendre");
    private JButton BackToMenuButton = createButton("Retour Accueille");
    public JPanel buttonContainer = new JPanel();

    public MenuInGame(GameFrame frame, GamePanel pane){
        this.resumeButton.addActionListener(e -> {
           frame.getGame().resume();
           pane.resumeGamePanel();
           pane.requestFocus();
        });


        this.BackToMenuButton.addActionListener(e -> {
            frame.dispose();
            frame.getGame().clearGameComponents();
            pane.removeAll();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GameFrame gameFrame = new GameFrame();
                    gameFrame.addMenu(new MenuPanel(gameFrame));
                    gameFrame.getCardlayout().show(gameFrame.getPanelContainer(), "menuPanel");
                }
            });
            this.removeAll();
        });

        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonContainer.setOpaque(false);


        buttonContainer.add(Box.createVerticalStrut(300));
        buttonContainer.add(resumeButton);
        buttonContainer.add(BackToMenuButton);

        menuInGameSquarePane.setBorder(new EmptyBorder(50, 50, 50, 50));
        menuInGameSquarePane.setOpaque(false);

        menuInGameSquarePane.add(buttonContainer, BorderLayout.CENTER);
    }

    public JPanel getMenuInGameSquarePane() {
        return menuInGameSquarePane;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(200, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.MAGENTA);
        return button;
    }
}


