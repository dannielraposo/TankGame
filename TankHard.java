public class TankHard extends TankMoves {

    public TankHard(int initialx, int initialy, int initialAngle) {
        super(initialx, initialy, initialAngle);
        this.setTankType("TankHard");
        this.setLives(2);
        loadImage("Resources/base_brown.png", "Resources/cannon_brown.png");
        this.setSpeed(2);
        this.setSeesMainTank(false);
    }
}