public class TankBasic extends Tank {

    private boolean seesMainTank;
    private int clockCyclesAfterLastShot = 0;
    private int cannonrotationsign = 1;

    public void setSeesMainTank(boolean seesMainTank) {
        this.seesMainTank = seesMainTank;
    }

    public boolean getSeesMainTank() {
        return this.seesMainTank;
    }

    public TankBasic(int initialx, int initialy, int initialAngle) {
        super(initialx, initialy, initialAngle, "TankBasic", 1, "Resources/base_blue.png",
                "Resources/cannon_blue.png");
        this.setSeesMainTank(false);
    }

    public void fire(double shootAngle) {
        double dx = Math.cos(Math.toRadians(360 - shootAngle));
        double dy = -Math.sin(Math.toRadians(360 - shootAngle));
        Missile missile = new Missile(this.getPosx(), this.getPosy(), dx, dy, shootAngle);
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

        setSeesMainTank(true);
        while (!(posxCheck > mainTank.getPosx() && posxCheck < (mainTank.getPosx() + TankGame.getImgSizeTank())
                && posyCheck > mainTank.getPosy() && posyCheck < (mainTank.getPosy() + TankGame.getImgSizeTank()))) {
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
