public class TankTriple extends TankHard {

    public TankTriple(int initialx, int initialy, int initialAngle) {
        super(initialx, initialy, initialAngle);
        this.setTankType("TankTriple");
        this.setLives(3);
        loadImage("Resources/base_lime.png", "Resources/cannon_lime.png");
        this.setSpeed(2);
        this.setSeesMainTank(false);
    }

    @Override
    public void fire(double shootAngle) {
        double dx1 = Math.cos(Math.toRadians(360 - shootAngle));
        double dy1 = -Math.sin(Math.toRadians(360 - shootAngle));
        Missile missile1 = new Missile(this.getPosx(), this.getPosy(), dx1, dy1, shootAngle, "enemy");

        double dx2 = Math.cos(Math.toRadians(360 - shootAngle + 20));
        double dy2 = -Math.sin(Math.toRadians(360 - shootAngle + 20));
        Missile missile2 = new Missile(this.getPosx(), this.getPosy(), dx2, dy2, shootAngle, "enemy");

        double dx3 = Math.cos(Math.toRadians(360 - shootAngle - 20));
        double dy3 = -Math.sin(Math.toRadians(360 - shootAngle - 20));
        Missile missile3 = new Missile(this.getPosx(), this.getPosy(), dx3, dy3, shootAngle, "enemy");

        Board.missiles.add(missile1);
        Board.missiles.add(missile2);
        Board.missiles.add(missile3);

    }
}