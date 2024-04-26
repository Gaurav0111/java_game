import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongGame extends JFrame implements ActionListener, KeyListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PADDLE_WIDTH = 10;
    private final int PADDLE_HEIGHT = 80;
    private final int BALL_SIZE = 20;
    private final int PADDLE_SPEED = 5;
    private int paddle1Y, paddle2Y;
    private int ballX, ballY, ballSpeedX, ballSpeedY;
    private int player1Score, player2Score;
    private Timer timer;

    public PongGame() {
        setTitle("Pong Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        paddle1Y = paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballSpeedX = ballSpeedY = 3;
        player1Score = player2Score = 0;

        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(10, this);
        timer.start();

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw paddles
        g.setColor(Color.WHITE);
        g.fillRect(20, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - 30, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw ball
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // Draw scores
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(Integer.toString(player1Score), WIDTH / 2 - 50, 50);
        g.drawString(Integer.toString(player2Score), WIDTH / 2 + 25, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move ball
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Check collision with paddles
        if (ballX <= 30 && ballY + BALL_SIZE >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballSpeedX *= -1;
        } else if (ballX + BALL_SIZE >= WIDTH - 40 && ballY + BALL_SIZE >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballSpeedX *= -1;
        }

        // Check collision with top and bottom walls
        if (ballY <= 0 || ballY + BALL_SIZE >= HEIGHT) {
            ballSpeedY *= -1;
        }

        // Check if ball goes out of bounds
        if (ballX <= 0) {
            player2Score++;
            resetBall();
        } else if (ballX + BALL_SIZE >= WIDTH) {
            player1Score++;
            resetBall();
        }

        repaint();
    }

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballSpeedX = ballSpeedY = 3;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W && paddle1Y > 0) {
            paddle1Y -= PADDLE_SPEED;
        } else if (key == KeyEvent.VK_S && paddle1Y < HEIGHT - PADDLE_HEIGHT) {
            paddle1Y += PADDLE_SPEED;
        } else if (key == KeyEvent.VK_UP && paddle2Y > 0) {
            paddle2Y -= PADDLE_SPEED;
        } else if (key == KeyEvent.VK_DOWN && paddle2Y < HEIGHT - PADDLE_HEIGHT) {
            paddle2Y += PADDLE_SPEED;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new PongGame();
    }
}
