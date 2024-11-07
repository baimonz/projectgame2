
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements KeyListener, Runnable {

    Cat cat = new Cat(50, 320);
    Car car1 = new Car(800, 300);
    Ghost ghost = new Ghost(700, 360);

    int score = 0;
    Image heart;
    Thread gameThread;

    Image imgBg, imgcat, imgcar, star, gameover, imggame1, imggame2, imgghost, imgapple, imgwin;
    int starX = 400;
    int starY = 200;
    int appleX = 350;
    int appleY = 200;

    boolean isGameOver = false;
    boolean gameStarted = false;
    boolean isGame2 = false;
    boolean isGame1 = false;

    int health = 5;
    JButton startAgainButton, exitButton;

    public Game(Image imgBg, Image imgcat, Image star, Image gameover) {
        this.imgBg = imgBg;
        this.imgcat = imgcat;
        this.gameover = gameover;
        this.star = star;

        imgcar = new ImageIcon(getClass().getResource("pic/car.png")).getImage();
        imggame1 = new ImageIcon(getClass().getResource("pic/game1.jpg")).getImage();
        imggame2 = new ImageIcon(getClass().getResource("pic/game2.png")).getImage();
        imgapple = new ImageIcon(getClass().getResource("pic/apple.png")).getImage();
        imgghost = new ImageIcon(getClass().getResource("pic/ghost.png")).getImage();
        imgwin = new ImageIcon(getClass().getResource("pic/win.png")).getImage();

        heart = new ImageIcon(getClass().getResource("pic/heart.png")).getImage();

        gameThread = new Thread(this);
        gameThread.start();

        // Button Play Again
        Icon buttonagain = new ImageIcon(getClass().getResource("pic/again.png"));
        startAgainButton = new JButton(buttonagain);
        startAgainButton.setVisible(false);
        startAgainButton.setBounds(290, 250, 200, 131);
        startAgainButton.setBorderPainted(false);
        startAgainButton.setContentAreaFilled(false);
        startAgainButton.addActionListener(e -> resetGame());

        // Button Exit
        Icon exitIcon = new ImageIcon(getClass().getResource("pic/exit.png"));
        exitButton = new JButton(exitIcon);
        exitButton.setVisible(false);
        exitButton.setBounds(650, 380, 120, 58);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(e -> System.exit(0));

        // Add buttons to JPanel
        this.setLayout(null);
        this.add(startAgainButton);
        this.add(exitButton);
    }

    // Start the game
    public void startGame() {
        gameStarted = true;
        isGameOver = false;
        cat.setX(50); // Reset cat position
        car1.setX(800); // Reset car1 position
        starX = 400;
    }
    

    // Handle painting the screen and buttons
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameStarted) {
            g.drawImage(imgBg, 0, 0, getWidth(), getHeight(), this); // Show main menu background

        } else if (isGameOver) {
            if (isGame2) {
                if (score < 1900) {
                    drawGame2(g);
                    g.drawImage(gameover, getWidth() / 2 - 100, getHeight() / 2 - 150, 200, 150, this);
                    startAgainButton.setVisible(true);
                    exitButton.setVisible(false);
                } else {
                    drawGame2(g);
                    g.drawImage(imgwin, getWidth() / 2 - 160, getHeight() / 2 - 210, 300, 250, this);
                    startAgainButton.setVisible(true);
                    exitButton.setVisible(true);
                }
            } else {
                drawGame(g);
                g.drawImage(gameover, getWidth() / 2 - 100, getHeight() / 2 - 150, 200, 150, this);
                startAgainButton.setVisible(true);
                exitButton.setVisible(true);
            }
        } else {
            if (score >= 1900) {
                isGameOver = true;
            } else if (score > 800) {
                isGame2 = true;
                drawGame2(g);
            } else {
                drawGame(g);
            }
        }
    }

    // Draw the first game screen
    public void drawGame(Graphics g) {
        g.drawImage(imggame1, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(imgcat, cat.getX(), cat.getY(), 80, 80, this);
        g.drawImage(imgcar, car1.getX(), car1.getY(), 150, 150, this);
        g.drawImage(star, starX, starY, 50, 50, this);

        for (int i = 0; i < health; i++) {
            g.drawImage(heart, 10 + (i * 30), 10, 20, 20, this);
        }

        g.setColor(Color.BLUE);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        g.drawString("Score: " + score, 550, 30);
    }

    // Draw the second game screen
    public void drawGame2(Graphics g) {
        g.drawImage(imggame2, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(imgcat, cat.getX(), cat.getY(), 80, 80, this);
        g.setColor(Color.red);
        g.drawImage(imgghost, ghost.getX(), ghost.getY(), 50, 50, this);
        g.drawImage(imgapple, appleX, appleY, 50, 50, this);

        for (int i = 0; i < health; i++) {
            g.drawImage(heart, 10 + (i * 30), 10, 20, 20, this);
        }

        g.setColor(Color.BLUE);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        g.drawString("Score: " + score, 550, 30);
    }

    // Reset the game
    public void resetGame() {
        isGameOver = false;
        score = 0;
        cat.setX(50);
        cat.setY(320);
        car1.setX(800);
        car1.setY(320);
        gameStarted = true;
        health = 5;
        startAgainButton.setVisible(false);
        exitButton.setVisible(false);
        repaint();
    }

    public void updateStarPosition() {
        starX -= 10;
        if (starX < -50) {
            starX = getWidth();

        }
    }

    public void updateApplePosition() {
        appleX -= 15;
        if (appleX < -80) {
            appleX = getWidth();
        }
    }
    public boolean collidedWithCar = false;
    public boolean collidedWithGhost = false;

    public void checkCollisions() {
        Rectangle catRect = new Rectangle(cat.getX() + 15, cat.getY() + 10, 50, 60);
        Rectangle starRect = new Rectangle(starX + 10, starY + 10, 30, 30);

        // Check collision with car only in Game 1
        if (!isGame2) {
            Rectangle CarRec2 = new Rectangle(car1.getX() + 20, car1.getY() + 50, 120, 60);
            boolean collisionWithCar = catRect.intersects(CarRec2);

            if (collisionWithCar) {
                if (!collidedWithCar) {
                    health--;
                    collidedWithCar = true;
                    System.out.println("Collision with car! Health: " + health);
                }

                if (health <= 0) {
                    isGameOver = true;
                    isGame1 = true;
                    System.out.println("Game Over!");
                }
            } else {
                collidedWithCar = false;
            }
        }

        // Check collision with star in both levels
        if (catRect.intersects(starRect)) {
            score += 100;
            System.out.println("Score : " + score + " jaaa ;-)");
            starX = getWidth();
        }

        // Check collision with ghost and apple only in Game 2
        if (isGame2) {
            Rectangle ghostRect = new Rectangle(ghost.getX(), ghost.getY(), 50, 50);
            Rectangle catRect2 = new Rectangle(cat.getX() + 15, cat.getY() + 25, 50, 50);
            Rectangle appleRect = new Rectangle(appleX, appleY + 10, 40, 40);

            boolean collisionWithGhost = catRect2.intersects(ghostRect);
            boolean collisionWithApple = catRect2.intersects(appleRect);

            if (collisionWithGhost) {
                if (!collidedWithGhost) {
                    health--;
                    collidedWithGhost = true;
                    System.out.println("Collision with ghost! Health: " + health);
                }

                if (health <= 0) {
                    isGameOver = true;
                    System.out.println("Game Over!");
                }
            } else {
                collidedWithGhost = false;
            }

            if (collisionWithApple) {
                score += 100;
                System.out.println("Picked up apple! Score: " + score);
                appleX = getWidth();
            }
        }
    }

    // Game loop
    @Override
    public void run() {
        while (true) {
            if (gameStarted && !isGameOver) {
                car1.updateCarPosition();
                updateStarPosition();
                checkCollisions();
                cat.updateJump(10);
                repaint();
            }
            if (isGame2 && !isGameOver) {
                updateApplePosition();
                cat.updateJump(5);
                ghost.updateGhostPosition();
                checkCollisions();
                repaint();
            }

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Key event handling
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameStarted || isGameOver) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            cat.setX(cat.getX() - 5);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            cat.setX(cat.getX() + 5);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            cat.startJump();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
