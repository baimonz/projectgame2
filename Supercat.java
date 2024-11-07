
import java.awt.event.KeyEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Supercat extends JFrame {

    JButton start,exit = new JButton();
    Game game;

    public Supercat() {
        // Image setup
        Image imgBg = new ImageIcon(getClass().getResource("pic/startpage.jpg")).getImage();
        Image cat = new ImageIcon(getClass().getResource("pic/cat1.png")).getImage();
        Image star = new ImageIcon(getClass().getResource("pic/star.png")).getImage();
        Image gameover = new ImageIcon(getClass().getResource("pic/gameover.png")).getImage();

        // Initialize the start button
        Icon btstart = new ImageIcon(getClass().getResource("pic/start.png"));
        start = new JButton(btstart);
        start.setBounds(300, 180, 200, 89);
        start.setBorderPainted(false);
        start.setContentAreaFilled(false);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        
        Icon exitIcon = new ImageIcon(getClass().getResource("pic/exit2.png"));
        exit = new JButton(exitIcon);
        exit.setBounds(330, 270,140, 78);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.addActionListener(e -> System.exit(0));

        game = new Game(imgBg, cat, star, gameover);  
        game.setLayout(null);
        game.add(exit);
        game.add(start);
        add(game);

        addKeyListener(game);
        setFocusable(true);
    }

    private void startGame() {
        start.setVisible(false);
        exit.setVisible(false);
        game.gameStarted = true;

    }

    private void restartGame() {
        game.resetGame();
        startGame();
    }

    public static void main(String[] args) {
        JFrame f = new Supercat();
        f.setTitle("Super cat");
        f.setIconImage(Toolkit.getDefaultToolkit().getImage(Supercat.class.getResource("pic/icon.png")));
        f.setSize(800, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
