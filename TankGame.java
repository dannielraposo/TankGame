import java.awt.EventQueue;

import javax.swing.JFrame;

public class TankGame extends JFrame {

    public static int height = 700;
    public static int width = height * 16 / 9;
    public static double imgSizeTank = (height * 0.1); // Size (lenght and width of icons in game: tanks)
    public static double imgSizeWall = (height * 0.1); // Size (lenght and width of icons in game: walls)

    public static int getGameWidth() {
        return width;
    }

    public static int getGameHeight() {
        return height;
    }

    public static double getImgSizeTank() {
        return imgSizeTank;
    }

    public static double getImgSizeWall() {
        return imgSizeWall;
    }

    public TankGame() {
        initUI();
    }

    private void initUI() {
        // Set up the JFrame
        this.setTitle("Tanketo++");
        // this.setSize(getGameWidth(), getGameHeight());
        // this.setPreferredSize(new Dimension(getGameWidth(), getGameHeight()));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add image component to the frame
        this.add(new Board());
        this.pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            TankGame game = new TankGame();
            game.setVisible(true);
        });
    }
}
