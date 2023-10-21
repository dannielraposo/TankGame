public class TankFast extends Tank {

    private boolean seesMainTank;
    private int clockCyclesAfterLastShot = 0;
    private int cannonrotationsign = 1;

    public void setSeesMainTank(boolean seesMainTank) {
        this.seesMainTank = seesMainTank;
    }

    public boolean getSeesMainTank() {
        return this.seesMainTank;
    }

    public TankFast(int initialx, int initialy, int initialAngle) {
        super(initialx, initialy, initialAngle, "TankFast", 1, "Resources/base_pink.png",
                "Resources/cannon_pink.png");
        this.setSpeed(2);
        this.setSeesMainTank(false);
    }

    public void fire(double shootAngle) {
        double dx = Math.cos(Math.toRadians(360 - shootAngle));
        double dy = -Math.sin(Math.toRadians(360 - shootAngle));
        Missile missile = new Missile(this.getPosx(), this.getPosy(), dx, dy, shootAngle);
        Board.missiles.add(missile);
    }

    public void update(Tank mainTank) {
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
        double posxCheck = this.getPosx() + TankGame.getImgSizeWall() / 2;
        double posyCheck = this.getPosy() + TankGame.getImgSizeWall() / 2;

        double dx = Math.cos(Math.toRadians(360 - angle2Main));
        double dy = -Math.sin(Math.toRadians(360 - angle2Main));

        this.setDx(dx);
        this.setDy(dy);

        boolean lastSeen = getSeesMainTank();

        setSeesMainTank(true);
        while (!(posxCheck > mainTank.getPosx() && posxCheck < (mainTank.getPosx() + TankGame.getImgSizeWall())
                && posyCheck > mainTank.getPosy() && posyCheck < (mainTank.getPosy() + TankGame.getImgSizeWall()))) {
            for (Wall wall : Board.walls) {
                if (posxCheck > wall.getPosx() && posxCheck < (wall.getPosx() + TankGame.getImgSizeWall())
                        && posyCheck > wall.getPosy() && posyCheck < (wall.getPosy() + TankGame.getImgSizeWall())) {
                    setSeesMainTank(false);
                    break;
                }
            }
            posxCheck += dx * (TankGame.getImgSizeWall() - 5);
            posyCheck += dy * (TankGame.getImgSizeWall() - 5);

            if (posxCheck < 0 || posxCheck > TankGame.getGameWidth() || posyCheck < 0
                    || posyCheck > TankGame.getGameHeight()) {
                // setSeesMainTank(false);

                break;
            }
        }

        if (this.getSeesMainTank()) {
            this.setShootAngle(angle2Main);
            this.setMovementAngle(angle2Main);
            //Check if distance to MainTank is prudencial in order to mode:
            double newPosx = this.getPosx() + this.getDx() * this.getSpeed();
            double newPosy = this.getPosy() + this.getDy() * this.getSpeed();
            if(Math.sqrt(Math.pow(this.getPosx() - mainTank.getPosx(), 2) + Math.pow(this.getPosy()-mainTank.getPosy(), 2)) > (2* TankGame.getImgSizeTank()*1.5)
            && !this.collides(newPosx, newPosy)){
            this.setPosx(newPosx);
            this.setPosy(newPosy);
            }


            if (!lastSeen || (lastSeen && clockCyclesAfterLastShot == 100)) {
                this.fire(this.getshootAngle());
                clockCyclesAfterLastShot = 0;
            } else {
                clockCyclesAfterLastShot++;
            }

        } else {
            double newangle = this.getshootAngle() + 0.2;
            if (Math.abs(newangle - angle2Main) > 45)
                cannonrotationsign *= -1;
            this.setShootAngle(this.getshootAngle() + cannonrotationsign * 0.2);
        }
    }
}
