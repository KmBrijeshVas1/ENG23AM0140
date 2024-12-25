import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBirdBeautiful extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int BIRD_SIZE = 30;
    private final int PIPE_WIDTH = 60;
    private final int PIPE_GAP = 150;
    private int birdY = HEIGHT / 2;
    private int birdVelocity = 0;
    private final int GRAVITY = 1;
    private final int JUMP = -12;
    private final Timer timer;
    private final Random random = new Random();
    private final ArrayList<Rectangle> pipes = new ArrayList<>();
    private int score = 0;
    private boolean gameOver = false;

    public FlappyBirdBeautiful() {
        timer = new Timer(20, this);
        addKeyListener(this);
        setFocusable(true);
        setPipes();
        timer.start();
    }

    private void setPipes() {
        pipes.clear();
        for (int i = 0; i < 4; i++) {
            int pipeHeight = random.nextInt(HEIGHT / 3) + 100;
            pipes.add(new Rectangle(WIDTH + i * 200, 0, PIPE_WIDTH, pipeHeight));
            pipes.add(new Rectangle(WIDTH + i * 200, pipeHeight + PIPE_GAP, PIPE_WIDTH, HEIGHT - pipeHeight - PIPE_GAP));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(new Color(135, 206, 235)); // Sky blue
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Ground
        g.setColor(new Color(222, 184, 135)); // Sandy ground
        g.fillRect(0, HEIGHT - 50, WIDTH, 50);

        // Bird
        g.setColor(Color.YELLOW);
        g.fillOval(100, birdY, BIRD_SIZE, BIRD_SIZE);

        // Pipes
        g.setColor(Color.GREEN);
        for (Rectangle pipe : pipes) {
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }

        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 10, 30);

        // Game Over
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", WIDTH / 2 - 150, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", WIDTH / 2 - 100, HEIGHT / 2 + 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        birdY += birdVelocity;
        birdVelocity += GRAVITY;

        for (int i = 0; i < pipes.size(); i++) {
            Rectangle pipe = pipes.get(i);
            pipe.x -= 5; // Move pipes left

            if (pipe.x + PIPE_WIDTH < 0) {
                pipes.remove(pipe);
                if (pipe.y == 0) { // Add new top and bottom pipes
                    int pipeHeight = random.nextInt(HEIGHT / 3) + 100;
                    pipes.add(new Rectangle(WIDTH, 0, PIPE_WIDTH, pipeHeight));
                    pipes.add(new Rectangle(WIDTH, pipeHeight + PIPE_GAP, PIPE_WIDTH, HEIGHT - pipeHeight - PIPE_GAP));
                    score++;
                }
            }
        }

        for (Rectangle pipe : pipes) {
            if (pipe.intersects(new Rectangle(100, birdY, BIRD_SIZE, BIRD_SIZE))) {
                gameOver = true;
            }
        }

        if (birdY > HEIGHT - 50 || birdY < 0) {
            gameOver = true;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            birdVelocity = JUMP; // Jump the bird
        }
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restartGame();
        }
    }

    private void restartGame() {
        birdY = HEIGHT / 2;
        birdVelocity = 0;
        score = 0;
        gameOver = false;
        setPipes();
        timer.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Beautiful Flappy Bird");
        FlappyBirdBeautiful game = new FlappyBirdBeautiful();
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

