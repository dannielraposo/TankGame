import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.JFrame;

public class TankGame extends JFrame {

    /*Let's get screen size using the Toolkit class and make it so that
     *the window is 0.9 the resolution of the screen.
    */
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int height = (int) (screenSize.getHeight()*0.9);
    private static int width = (int) (screenSize.getWidth()*0.9);
    /* The size of walls is adjusted in order to fit well 
    in the screen (regardless of the resolution) */
    private static double imgSizeTank = (height * 0.08); // Size (lenght and width of icons in game: tanks)
    private static double imgSizeWall = (height * 0.092); // Size (lenght and width of icons in game: walls)

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

    public static Dimension getScreenSize() {
        return screenSize;
    }

    public TankGame() {
        initUI();
    }

    private void initUI() {
        // Set up the JFrame
        this.setTitle("Tanketo++");
        // this.setSize(getGameWidth(), getGameHeight());
        // this.setPreferredSize(new Dimension(getGameWidth(), getGameHeight()));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        // Add image component to the frame
        this.add(new Board());
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            TankGame game = new TankGame();
            game.setVisible(true);
        });
    }
}
