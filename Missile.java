import java.awt.Image;
import javax.swing.ImageIcon;

public class Missile {

    private final int MISSILE_SPEED = 2;
    private double posx;
    private double posy;
    private double dx;
    private double dy;
    private double angle;
    private boolean visible;
    private boolean bounced;
    private String missileType;
    private boolean doublespeed;

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

    private void loadImage() {
        String imageName = "Resources/missile_green.png";
        if (missileType == "enemy") {
            imageName = "Resources/missile_black.png";
        }
        ImageIcon imageMissile = new ImageIcon(imageName); // load the image to a imageIcon
        Image scimageMissile = imageMissile.getImage(); // transform it
        this.image = scimageMissile.getScaledInstance((int) TankGame.getImgSizeTank(), (int) TankGame.getImgSizeTank(),
                java.awt.Image.SCALE_SMOOTH);
    }

    public Image getImage() {
        return image;
    }

    public String getMissileType() {
        return missileType;
    }

    public Missile(double posx, double posy, double dx, double dy, double angle, String missileType,
            boolean doublespeed) {

        // double dxu = dx / Math.sqrt(dx * dx + dy * dy);
        // double dyu = dy / Math.sqrt(dx * dx + dy * dy); YA SON UNITARIOS, VIENEN DEL
        // COS Y SEN DEL ANGULO, NO TANGENTE

        this.posx = posx + dx * TankGame.getImgSizeTank() / 2;
        this.posy = posy + dy * TankGame.getImgSizeTank() / 2;
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
        this.visible = true;
        this.bounced = false;
        this.missileType = missileType;
        this.doublespeed = doublespeed;
        loadImage();
    }

    public void move() {
        if (this.getVisible()) {

            double newPosx = this.getPosx() + MISSILE_SPEED * this.getDx();
            double newPosy = this.getPosy() + MISSILE_SPEED * this.getDy();

            if (this.doublespeed) {
                newPosx = this.getPosx() + 2 * MISSILE_SPEED * this.getDx();
                newPosy = this.getPosy() + 2 * MISSILE_SPEED * this.getDy();
            }

            double visualPosx = newPosx + TankGame.getImgSizeTank() / 2;
            double visualPosy = newPosy + TankGame.getImgSizeTank() / 2;

            // Check collision with MAIN tank
            if ((visualPosx > Board.MainTank.getPosx() + TankGame.getImgSizeTank() * 60 / 512)
                    && (visualPosx < (Board.MainTank.getPosx() + TankGame.getImgSizeTank()
                            - TankGame.getImgSizeTank() * 60 / 512))
                    && (visualPosy > Board.MainTank.getPosy() + TankGame.getImgSizeTank() * 60 / 512)
                    && (visualPosy < (Board.MainTank.getPosy() + TankGame.getImgSizeTank()
                            - TankGame.getImgSizeTank() * 60 / 512))) {
                this.setVisible(false);
                if (this.getMissileType() == "enemy") {
                    Sound.MISSILEHIT.play();
                    Board.MainTank.decrlives();
                }
                return;

            }

            // Check collision with enemy tanks
            for (Tank tank : Board.EnemyTanks) {
                if ((visualPosx > tank.getPosx() + TankGame.getImgSizeTank() * 60 / 512)
                        && (visualPosx < (tank.getPosx() + TankGame.getImgSizeTank()
                                - TankGame.getImgSizeTank() * 60 / 512))
                        && (visualPosy > tank.getPosy() + TankGame.getImgSizeTank() * 60 / 512)
                        && (visualPosy < (tank.getPosy() + TankGame.getImgSizeTank()
                                - TankGame.getImgSizeTank() * 60 / 512))) {
                    this.setVisible(false);
                    if (this.getMissileType() == "main") {
                        Sound.MISSILEHIT.play();
                        tank.decrlives();
                    }
                    return;

                }
            }

            // Check collision with walls
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
                    } else if (wall.getType() == "weak") {
                        if (this.getMissileType() == "enemy") {
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
                        } else {
                            wall.setVisible(false);
                            this.setVisible(false);
                            this.setPosx(newPosx);
                            this.setPosy(newPosy);
                        }
                    } else if (wall.getType().startsWith("reward")) {
                        if (this.getMissileType() == "enemy") {
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
                        } else {
                            Sound.WALLDESTROY.play();
                            this.setVisible(false);
                            wall.setVisible(false);

                            Board.rewards.add(
                                    new Reward(
                                            (int) (wall.getPosx() + TankGame.getImgSizeWall() / 2
                                                    - TankGame.getImgSizeReward() / 2),
                                            (int) (wall.getPosy() + TankGame.getImgSizeWall() / 2
                                                    - TankGame.getImgSizeReward() / 2),
                                            wall.getType()));

                            this.setPosx(newPosx);
                            this.setPosy(newPosy);
                        }
                    }
                }

                else {
                    this.setPosx(newPosx);
                    this.setPosy(newPosy);
                }

            }
        }
    }
}