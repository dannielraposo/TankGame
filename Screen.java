import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel {

    public String image;

    public Screen(String ImagePath) {
        setBackground(Color.white);
        setFocusable(true);
        setSize(TankGame.getGameWidth(), TankGame.getGameHeight());
        setPreferredSize(new Dimension(TankGame.getGameWidth(),
                (int) (TankGame.getGameHeight() + TankGame.screenSize.getHeight() * 0.15)));
        image = ImagePath;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image:
        ImageIcon imageBackground = new ImageIcon(image); // load the image to a imageIcon
        Image sciimageBackground = imageBackground.getImage(); // transform it
        Graphics2D gpScreenimage = (Graphics2D) g.create();
        gpScreenimage.drawImage(sciimageBackground, 0, 0, TankGame.getGameWidth(), (int) (TankGame.getGameHeight() + TankGame.screenSize.getHeight() * 0.15), this);
        gpScreenimage.dispose();
    }
}