import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Footer extends JPanel {

    public String image;

    public Footer() {
        initFooter();

    }

    public void initFooter() {
        // setBackground(Color.red);
        setFocusable(true);
        setSize(TankGame.getGameWidth(), (int) (TankGame.getGameHeight() * 0.15));
        setPreferredSize(new Dimension(TankGame.getGameWidth(), (int) (TankGame.getGameHeight() * 0.15)));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D;
        g2D = (Graphics2D) g.create();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // // Draw background image:
        ImageIcon imageBackground = new ImageIcon("Resources/footer_background.png");
        Image sciimageBackground = imageBackground.getImage();
        Graphics2D gpScreenimage = (Graphics2D) g.create();
        gpScreenimage.drawImage(sciimageBackground, 0, 0, this);
        gpScreenimage.dispose();

        // Draw rewards:

        // TRIPLE SHOT:
        Graphics2D gph3shot = (Graphics2D) g.create();
        if (Board.MainTank.getTriple_Shot()) {
            ImageIcon imageicon = new ImageIcon("Resources/reward_3shot.png");
            Image image = imageicon.getImage();
            // image = image.getScaledInstance((int) (TankGame.getGameHeight() * 0.15),
            // (int) (TankGame.getGameHeight() * 0.15),
            // java.awt.Image.SCALE_SMOOTH);
            gph3shot.drawImage(image, TankGame.getGameWidth() - (int) (this.getHeight() * 0.15),
                    TankGame.getGameHeight(), (int) (this.getHeight() * 0.08),
                    (int) (this.getHeight() * 0.08), this);
        } else {
            ImageIcon imageicon = new ImageIcon("Resources/reward_3shot_border.png");
            Image image = imageicon.getImage();
            // image = image.getScaledInstance((int) (TankGame.getGameHeight() * 0.15),
            // (int) (TankGame.getGameHeight() * 0.15),
            // java.awt.Image.SCALE_SMOOTH);
            gph3shot.drawImage(image, TankGame.getGameWidth() - (int) (this.getHeight() * 0.15),
                    TankGame.getGameHeight(), (int) (this.getHeight() * 0.08),
                    (int) (this.getHeight() * 0.08), this);
        }
        gph3shot.dispose();

        // ENERGY:
        Graphics2D gphenergy = (Graphics2D) g.create();
        if (Board.MainTank.getDouble_speed()) {
            ImageIcon imageicon = new ImageIcon("Resources/reward_energy.png");
            Image image = imageicon.getImage();
            // image = image.getScaledInstance((int) (TankGame.getGameHeight() * 0.15),
            // (int) (TankGame.getGameHeight() * 0.15),
            // java.awt.Image.SCALE_SMOOTH);
            gphenergy.drawImage(image, TankGame.getGameWidth() - 2 * (int) (this.getHeight() * 0.15),
                    TankGame.getGameHeight(), (int) (this.getHeight() * 0.08),
                    (int) (this.getHeight() * 0.08), this);
        } else {
            ImageIcon imageicon = new ImageIcon("Resources/reward_energy_border.png");
            Image image = imageicon.getImage();
            // image = image.getScaledInstance((int) (TankGame.getGameHeight() * 0.15),
            // (int) (TankGame.getGameHeight() * 0.15),
            // java.awt.Image.SCALE_SMOOTH);
            gphenergy.drawImage(image, TankGame.getGameWidth() - 2 * (int) (this.getHeight() * 0.15),
                    TankGame.getGameHeight(), (int) (this.getHeight() * 0.08),
                    (int) (this.getHeight() * 0.08), this);
        }
        gphenergy.dispose();

        // GHOST:
        Graphics2D gphghost = (Graphics2D) g.create();
        if (Board.MainTank.getGhost()) {
            ImageIcon imageicon = new ImageIcon("Resources/reward_ghost.png");
            Image image = imageicon.getImage();
            // image = image.getScaledInstance((int) (TankGame.getGameHeight() * 0.15),
            // (int) (TankGame.getGameHeight() * 0.15),
            // java.awt.Image.SCALE_SMOOTH);
            gphghost.drawImage(image, TankGame.getGameWidth() - 3 * (int) (this.getHeight() * 0.15),
                    TankGame.getGameHeight(), (int) (this.getHeight() * 0.08),
                    (int) (this.getHeight() * 0.08), this);
        } else {
            ImageIcon imageicon = new ImageIcon("Resources/reward_ghost_border.png");
            Image image = imageicon.getImage();
            // image = image.getScaledInstance((int) (TankGame.getGameHeight() * 0.15),
            // (int) (TankGame.getGameHeight() * 0.15),
            // java.awt.Image.SCALE_SMOOTH);
            gphghost.drawImage(image, TankGame.getGameWidth() - 3 * (int) (this.getHeight() * 0.15),
                    TankGame.getGameHeight(), (int) (this.getHeight() * 0.08),
                    (int) (this.getHeight() * 0.08), this);
        }
        gphghost.dispose();

        // SHIELD:
        Graphics2D gphshield = (Graphics2D) g.create();
        if (Board.MainTank.getShield()) {
            ImageIcon imageicon = new ImageIcon("Resources/reward_shield.png");
            Image image = imageicon.getImage();
            // image = image.getScaledInstance((int) (TankGame.getGameHeight() * 0.15),
            // (int) (TankGame.getGameHeight() * 0.15),
            // java.awt.Image.SCALE_SMOOTH);
            gphshield.drawImage(image, TankGame.getGameWidth() - 4 * (int) (this.getHeight() * 0.15),
                    TankGame.getGameHeight(), (int) (this.getHeight() * 0.08),
                    (int) (this.getHeight() * 0.08), this);
        } else {
            ImageIcon imageicon = new ImageIcon("Resources/reward_shield_border.png");
            Image image = imageicon.getImage();
            // image = image.getScaledInstance((int) (TankGame.getGameHeight() * 0.15),
            // (int) (TankGame.getGameHeight() * 0.15),
            // java.awt.Image.SCALE_SMOOTH);
            gphshield.drawImage(image, TankGame.getGameWidth() - 4 * (int) (this.getHeight() * 0.15),
                    TankGame.getGameHeight(), (int) (this.getHeight() * 0.08),
                    (int) (this.getHeight() * 0.08), this);
        }
        gphshield.dispose();

        // Draw lives:
        for (int i = 0; i < Board.MainTank.getLives(); i++) {
            Graphics2D gphlive = (Graphics2D) g.create();
            ImageIcon imageicon = new ImageIcon("Resources/reward_1up.png");
            Image image = imageicon.getImage();
            gphlive.drawImage(image, i * (int) (this.getHeight() * 0.15),
                    TankGame.getGameHeight(),
                    (int) (this.getHeight() * 0.08),
                    (int) (this.getHeight() * 0.08), this);

            gphlive.dispose();
        }
    }
}
