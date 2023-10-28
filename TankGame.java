import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TankGame extends JFrame {

    public static Board board;
    public static TankGame game;
    public static Footer footer;

    /*
     * Let's get screen size using the Toolkit class and make it so that
     * the window is 0.9 the resolution of the screen.
     */
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int height = (int) (screenSize.getHeight() * 0.75);
    private static int width = (int) (screenSize.getWidth() * 0.75);
    // private static int width = height;

    /*
     * The size of walls is adjusted in order to fit well
     * in the screen (regardless of the resolution)
     */
    private static double imgSizeTank; // Size (lenght and width of icons in game: tanks)
    private static double imgSizeReward; // Size (lenght and width of icons in game: tanks)
    private static double imgSizeWall; // Size (lenght and width of icons in game: walls)

    public static int getGameWidth() {
        return width;
    }

    public static int getGameHeight() {
        return height;
    }

    public static double getImgSizeTank() {
        return imgSizeTank;
    }

    public static double getImgSizeReward() {
        return imgSizeReward;
    }

    public static double getImgSizeWall() {
        return imgSizeWall;
    }

    public static Dimension getScreenSize() {
        return screenSize;
    }

    public TankGame() {
        initUI();
    }

    private void initUI() {

        while ((getGameWidth() / (16f / 9f)) - (int) (getGameWidth() / (16f / 9f)) != 0) {
            TankGame.width--;
        }

        TankGame.height = (int) (getGameWidth() / (16f / 9f));

        imgSizeTank = (height / 13); // Size (lenght and width of icons in game: tanks)
        imgSizeReward = (height / 15); // Size (lenght and width of icons in game: reward)
        imgSizeWall = (height / 11); // Size (lenght and width of icons in game: walls)

        // Set up the JFrame
        this.setTitle("Tanketo++");
        // this.setSize(getGameWidth(), getGameHeight());
        this.setPreferredSize(new Dimension(getGameWidth(), (int) (getGameHeight() + screenSize.getHeight() * 0.15)));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Creating start panel:
        JPanel startPanel = new JPanel();
        JButton startButton = new JButton("START");
        startPanel.setSize(TankGame.getGameWidth(), TankGame.getGameHeight());
        startPanel.setPreferredSize(new Dimension(TankGame.getGameWidth(), TankGame.getGameHeight()));
        startPanel.add(startButton);
        startPanel.setBackground(Color.red);
        this.add(startPanel);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.remove(startPanel);
                board = new Board();
                footer = new Footer();
                game.add(board);
                game.add(footer);
                game.setVisible(false);
                game.setVisible(true);
            }
        });

        this.pack();
        this.setLocationRelativeTo(null);
    }

    public static void changeLevel(int currentGameLevel) {
        board.timer.cancel();
        game.remove(board);
        game.remove(footer);

        Screen panel = new Screen("Resources/nextlevel" + currentGameLevel + ".png");
        game.add(panel);
        game.pack();

        game.setVisible(false);
        game.setVisible(true);

        Timer screenTimer = new Timer();
        screenTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.remove(panel);
                board.initBoard();
                footer.initFooter();
                game.add(board);
                game.add(footer);
                game.pack();
                game.setVisible(false);
                game.setVisible(true);
                screenTimer.cancel();
            }
        }, 4 * 1000);

    }

    public static void restartGame() {
        game.remove(board);
        board.timer.cancel();
        game.remove(footer);

        Screen panel = new Screen("Resources/gameover.png");
        game.add(panel);
        game.pack();

        game.setVisible(false);
        game.setVisible(true);

        Timer timerRestart = new Timer();
        timerRestart.schedule(new TimerTask() {
            @Override
            public void run() {
                game.remove(panel);
                board = new Board();
                footer = new Footer();
                game.add(footer);
                game.add(board);
                game.pack();
                game.setVisible(false);
                game.setVisible(true);
                timerRestart.cancel();

            }
        }, 4 * 1000);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            game = new TankGame();
            game.setVisible(true);
        });
    }
}
