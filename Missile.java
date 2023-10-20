import java.awt.Image;
import javax.swing.ImageIcon;

public class Missile {

    private final int MISSILE_SPEED = 6;
    private double posx;
    private double posy;
    private double dx;
    private double dy;
    private double angle;
    private boolean visible;
    private boolean bounced;

    public double getPosx() {
        return posx;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public double getPosy() {
        return posy;
    }

    public void setPosy(double posy) {
        this.posy = posy;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return visible;
    }

    protected Image image;

    private void loadImage(String imageName) {
        ImageIcon imageMissile = new ImageIcon(imageName); // load the image to a imageIcon
        Image scimageMissile = imageMissile.getImage(); // transform it
        this.image = scimageMissile.getScaledInstance((int)TankGame.getImgSizeTank(), (int)TankGame.getImgSizeTank(),
                java.awt.Image.SCALE_SMOOTH);
    }

    public Image getImage() {
        return image;
    }

    public Missile(double posx, double posy, double dx, double dy, double angle) {
        this.posx = posx;
        this.posy = posy;
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
        this.visible = true;
        this.bounced = false;
        loadImage("Resources/missile_black.png");
    }

    public void move() {

        double newPosx = this.getPosx() + MISSILE_SPEED * this.getDx();
        double newPosy = this.getPosy() + MISSILE_SPEED * this.getDy();

        double visualPosx = newPosx + TankGame.getImgSizeTank() / 2;
        double visualPosy = newPosy + TankGame.getImgSizeTank() / 2;

        for (Wall wall : Board.walls) {
            if (visualPosx > wall.getPosx() && visualPosx < (wall.getPosx() + TankGame.getImgSizeWall())
                    && visualPosy > wall.getPosy() && visualPosy < (wall.getPosy() + TankGame.getImgSizeWall())) {

                if (wall.getType() == "standard") {

                    if (this.bounced) {
                        this.setVisible(false);
                    } else {
                        this.bounced = true;

                        double distleft = Math.abs(visualPosx - wall.getPosx());
                        double distright = Math.abs(visualPosx - (wall.getPosx() + TankGame.getImgSizeWall()));
                        double disttop = Math.abs(visualPosy - wall.getPosy());
                        double distbottom = Math.abs(visualPosy - (wall.getPosy() + TankGame.getImgSizeWall()));

                        if (distleft < distright && distleft < disttop && distleft < distbottom) {
                            // System.out.println("colision left wall");
                            this.setDx(-this.getDx());
                            this.setAngle((double) Math.toDegrees(Math.atan2(this.getDy(), this.getDx())));
                        } else if (distright < distleft && distright < disttop && distright < distbottom) {
                            // System.out.println("colision right wall");
                            this.setDx(-this.getDx());
                            this.setAngle((double) Math.toDegrees(Math.atan2(this.getDy(), this.getDx())));

                        } else if (disttop < distright && disttop < distleft && disttop < distbottom) {
                            // System.out.println("colision top wall");
                            this.setDy(-this.getDy());
                            this.setAngle((double) Math.toDegrees(Math.atan2(this.getDy(), this.getDx())));

                        } else if (distbottom < distright && distbottom < disttop && distbottom < distleft) {
                            // System.out.println("colision bottom wall");
                            this.setDy(-this.getDy());
                            this.setAngle((double) Math.toDegrees(Math.atan2(this.getDy(), this.getDx())));
                        }
                    }
                }
                if (wall.getType() == "weak") {
                    wall.setVisible(false);
                    this.setVisible(false);
                    this.setPosx(newPosx);
                    this.setPosy(newPosy);
                }
                if (wall.getType() == "reward") {
                    wall.setVisible(false);
                    this.setVisible(false);

                    this.setPosx(newPosx);
                    this.setPosy(newPosy);
                }
            }

            else {
                this.setPosx(newPosx);
                this.setPosy(newPosy);
            }

        }
    }
}