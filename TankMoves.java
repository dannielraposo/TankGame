import java.util.Random;

public class TankMoves extends Tank {

    private boolean movingRandomly;
    private int ranDist2move;
    private Wall wallMoving2;
    private boolean seesMainTank;
    private int clockCyclesAfterLastShot = 0;
    private int cannonrotationsign = 1;
    private int signofrotativemov = 1;

    public void setSeesMainTank(boolean seesMainTank) {
        this.seesMainTank = seesMainTank;
    }

    public boolean getSeesMainTank() {
        return this.seesMainTank;
    }

    public TankMoves(int initialx, int initialy, int initialAngle) {
        super(initialx, initialy, initialAngle, "TankMoves", 1);
        loadImage("Resources/base_pink.png", "Resources/cannon_pink.png");
        this.setSpeed(1);
        this.setSeesMainTank(false);
        this.movingRandomly = false;
    }

    public void fire(double shootAngle) {
        double dx = Math.cos(Math.toRadians(360 - shootAngle));
        double dy = -Math.sin(Math.toRadians(360 - shootAngle));
        Missile missile = new Missile(this.getPosx(), this.getPosy(), dx, dy, shootAngle, "enemy", false);
        Board.missiles.add(missile);
    }

    public void update(Tank mainTank) {
        if (this.getLives() == 0) {
            this.setVisible(false);
            return;
        }

        int angle2Main = (int) Math.toDegrees(
                Math.atan2(
                        (mainTank.getPosy() + TankGame.getImgSizeTank() / 2)
                                - (this.getPosy() + TankGame.getImgSizeTank() / 2),
                        (mainTank.getPosx() + TankGame.getImgSizeTank() / 2)
                                - (this.getPosx() + TankGame.getImgSizeTank() / 2)));
        if (angle2Main < 0) {
            angle2Main += 360;
        }

        // CHECK LINE OF SIGHT WITH MAIN TANK:
        double posxCheck = this.getPosx() + TankGame.getImgSizeTank() / 2;
        double posyCheck = this.getPosy() + TankGame.getImgSizeTank() / 2;

        double dx = Math.cos(Math.toRadians(360 - angle2Main));
        double dy = -Math.sin(Math.toRadians(360 - angle2Main));

        boolean lastSeen = getSeesMainTank();

        if (!Board.MainTank.getGhost()) {

            setSeesMainTank(true);
            while (!(posxCheck > mainTank.getPosx() && posxCheck < (mainTank.getPosx() + TankGame.getImgSizeWall())
                    && posyCheck > mainTank.getPosy()
                    && posyCheck < (mainTank.getPosy() + TankGame.getImgSizeWall()))) {
                for (Wall wall : Board.walls) {
                    if (posxCheck > wall.getPosx() && posxCheck < (wall.getPosx() + TankGame.getImgSizeWall())
                            && posyCheck > wall.getPosy() && posyCheck < (wall.getPosy() + TankGame.getImgSizeWall())) {
                        setSeesMainTank(false);
                        break;
                    }
                }
                if (!getSeesMainTank())
                    break;
                // posxCheck += dx * (TankGame.getImgSizeWall() - 20);
                // posyCheck += dy * (TankGame.getImgSizeWall() - 20);
                posxCheck += dx;
                posyCheck += dy;

                if (posxCheck < 0 || posxCheck > TankGame.getGameWidth() || posyCheck < 0
                        || posyCheck > TankGame.getGameHeight()) {
                    // setSeesMainTank(false);
                    break;
                }
            }
        } else {
            setSeesMainTank(false);

        }

        if (this.getSeesMainTank()) {
            this.setShootAngle(angle2Main);
            this.setMovementAngle(angle2Main);
            this.setDx(dx);
            this.setDy(dy);
            // Check if distance to MainTank is prudencial in order to mode:
            double newPosx = this.getPosx() + this.getDx() * this.getSpeed();
            double newPosy = this.getPosy() + this.getDy() * this.getSpeed();
            if (Math.sqrt(Math.pow(this.getPosx() - mainTank.getPosx(), 2)
                    + Math.pow(this.getPosy() - mainTank.getPosy(), 2)) > (2 * TankGame.getImgSizeTank() * 1.5)
                    && !this.collides(newPosx, newPosy)) {
                this.setPosx(newPosx);
                this.setPosy(newPosy);
            }

            else {
                dx = Math.cos(Math.toRadians(360 - (angle2Main + signofrotativemov * 90)));
                dy = -Math.sin(Math.toRadians(360 - (angle2Main + signofrotativemov * 90)));
                newPosx = this.getPosx() + dx * this.getSpeed();
                newPosy = this.getPosy() + dy * this.getSpeed();
                if (!this.collides(newPosx, newPosy)) {
                    this.setMovementAngle(angle2Main + signofrotativemov * 90);
                    this.setPosx(newPosx);
                    this.setPosy(newPosy);
                }
                else {signofrotativemov = signofrotativemov * -1;}
            }

            if (!lastSeen || (lastSeen && clockCyclesAfterLastShot == 100)) {
                this.fire(this.getshootAngle());
                clockCyclesAfterLastShot = 0;
            } else {
                clockCyclesAfterLastShot++;
            }

        } else {
            // Move cannon to search for MAINTANK:
            double newangle = this.getshootAngle() + 0.2;
            if (Math.abs(newangle - angle2Main) > 45)
                cannonrotationsign *= -1;
            this.setShootAngle(this.getshootAngle() + cannonrotationsign * 0.2);

            // Move position to search for MAINTANK:
            if (!movingRandomly) {
                int randDir2move = (new Random()).nextInt(8); // The tank has 8 angles to move with (0,45,90,135...)
                this.ranDist2move = (new Random()).nextInt(3) + 1; // It will get as close as 1,2,3 block lengths to the
                                                                   // wall
                this.wallMoving2 = null;

                dx = Math.cos(Math.toRadians(randDir2move * 45));
                dy = -Math.sin(Math.toRadians(randDir2move * 45));
                this.setDx(dx);
                this.setDy(dy);

                this.setMovementAngle(360 - randDir2move * 45);

                // Detect towards which block I am moving, to be able to calculate distance:
                posxCheck = this.getPosx() + TankGame.getImgSizeTank() / 2;
                posyCheck = this.getPosy() + TankGame.getImgSizeTank() / 2;
                while (!(posxCheck < 0 || posxCheck > TankGame.getGameWidth() || posyCheck < 0
                        || posyCheck > TankGame.getGameHeight())) {
                    for (Wall wall : Board.walls) {
                        if (posxCheck > wall.getPosx()
                                && posxCheck < (wall.getPosx() + TankGame.getImgSizeWall())
                                && posyCheck > wall.getPosy()
                                && posyCheck < (wall.getPosy() + TankGame.getImgSizeWall())) {
                            this.wallMoving2 = wall;
                            break;
                        }
                    }
                    if (this.wallMoving2 != null)
                        break;
                    posxCheck += dx * (TankGame.getImgSizeWall() - 5);
                    posyCheck += dy * (TankGame.getImgSizeWall() - 5);
                }

                double newPosx = this.getPosx() + this.getDx() * this.getSpeed() / 2;
                double newPosy = this.getPosy() + this.getDy() * this.getSpeed() / 2;

                if (Math.sqrt(Math
                        .pow(this.getPosx() + TankGame.getImgSizeTank() / 2
                                - (wallMoving2.getPosx() + TankGame.getImgSizeWall() / 2), 2)
                        + Math.pow(
                                this.getPosy() + TankGame.getImgSizeTank() / 2
                                        - (wallMoving2.getPosy() + TankGame.getImgSizeWall() / 2),
                                2)) > (this.ranDist2move * TankGame.getImgSizeWall())
                        && !this.collides(newPosx, newPosy)) {
                    this.setPosx(newPosx);
                    this.setPosy(newPosy);
                    this.movingRandomly = true;
                }
            }

            else {

                double newPosx = this.getPosx() + this.getDx() * this.getSpeed() / 2;
                double newPosy = this.getPosy() + this.getDy() * this.getSpeed() / 2;

                if (Math.sqrt(Math
                        .pow(this.getPosx() + TankGame.getImgSizeTank() / 2
                                - (wallMoving2.getPosx() + TankGame.getImgSizeWall() / 2), 2)
                        + Math.pow(
                                this.getPosy() + TankGame.getImgSizeTank() / 2
                                        - (wallMoving2.getPosy() + TankGame.getImgSizeWall() / 2),
                                2)) > (this.ranDist2move * TankGame.getImgSizeWall())
                        && !this.collides(newPosx, newPosy)) {
                    this.setPosx(newPosx);
                    this.setPosy(newPosy);
                }

                else {
                    this.movingRandomly = false;
                }
            }
        }
    }
}
