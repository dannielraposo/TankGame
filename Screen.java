import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel {

    public Image image;

    public Screen(String ImagePath) {
        setBackground(Color.white);
        setFocusable(true);
        setSize(TankGame.getGameWidth(), TankGame.getGameHeight());
        setPreferredSize(new Dimension(TankGame.getGameWidth(), TankGame.getGameHeight()));
        image = Toolkit.getDefaultToolkit().getImage(ImagePath);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image:
        Graphics2D gphbackimage = (Graphics2D) g.create();
        ImageIcon imageBackground = new ImageIcon(image);
        Image sciimageBackground = imageBackground.getImage();
        // Image sciimageBackgroundScaled = sciimageBackground.getScaledInstance((int) TankGame.getGameWidth(),
        // (int) TankGame.getGameHeight(),
        // java.awt.Image.SCALE_SMOOTH);
        
        gphbackimage.drawImage(sciimageBackground, 0, 0, this);
        gphbackimage.dispose();
    }
}