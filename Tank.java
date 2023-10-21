import java.awt.Image;
import javax.swing.ImageIcon;

public class Tank {

	private boolean visible;
	private double posx;
	private double posy;
	private String tankType;
	private int lives;
	private double dx;
	private double dy;
	private double speed;
	private Image image_base;
	private Image image_cannon;
	private double shootAngle;
	private double movementAngle;

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

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setTankType(String tankType) {
		this.tankType = tankType;
	}

	public String getTankType() {
		return tankType;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getLives() {
		return lives;
	}

	public void incrlives() {
		this.lives += 1;
	}

	public void decrlives() {
		this.lives -= 1;
		if (getTankType() == "TankHard" && getLives() == 1) {
			this.setVisible(false);
			Board.EnemyTanks
					.add(new TankMoves((int) this.getPosx(), (int) this.getPosy(), (int) this.getmovementAngle()));
		}

		else if (getTankType() == "TankTriple" && getLives() == 2) {
			this.setVisible(false);
			Board.EnemyTanks.add(new TankHard((int) this.getPosx(), (int) this.getPosy(), (int) this.getmovementAngle()));
		}

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

	public void setSpeed(double speed) {
		this.speed = speed * TankGame.getScreenSize().getWidth() / 1920;
	}

	public double getSpeed() {
		return speed;
	}

	public void incrspeed() {
		this.speed = speed + 1;
	}

	public void decspeed() {
		if (speed > 0)
			this.speed = speed - 1;
	}

	public Image getImageBase() {
		return image_base;
	}

	public Image getImageCannon() {
		return image_cannon;
	}

	public void loadImage(String fileBase, String fileCannon) {
		ImageIcon imageIconBase = new ImageIcon(fileBase); // load the image to a imageIcon
		Image scimageBase = imageIconBase.getImage(); // transform it
		this.image_base = scimageBase.getScaledInstance((int) TankGame.getImgSizeTank(),
				(int) TankGame.getImgSizeTank(),
				java.awt.Image.SCALE_SMOOTH);

		ImageIcon imageIconCannon = new ImageIcon(fileCannon); // load the image to a imageIcon
		Image scimageCannon = imageIconCannon.getImage(); // transform it
		this.image_cannon = scimageCannon.getScaledInstance((int) TankGame.getImgSizeTank(),
				(int) TankGame.getImgSizeTank(),
				java.awt.Image.SCALE_SMOOTH);
	}

	public double getshootAngle() {
		return shootAngle;
	}

	public void setShootAngle(double shootAngle) {
		this.shootAngle = shootAngle;
	}

	public double getmovementAngle() {
		return movementAngle;
	}

	public void setMovementAngle(double movementAngle) {
		this.movementAngle = movementAngle;
	}

	// Constructor of the class Tank:
	public Tank(int initialx, int initialy, double initialAngle, String tankType, int lives) {
		this.posx = initialx;
		this.posy = initialy;
		this.shootAngle = initialAngle;
		this.movementAngle = initialAngle;
		this.tankType = tankType;
		this.lives = lives;
		this.visible = true;
	}

	public boolean collides(double newPosx, double newPosy) {
		for (Wall wall : Board.walls) {
			if (!(((newPosx + TankGame.getImgSizeTank() * 60 / 512) > (wall.getPosx() + TankGame.getImgSizeWall()))
					|| ((newPosx + TankGame.getImgSizeTank() - TankGame.getImgSizeTank() * 60 / 512) < wall.getPosx())
					|| ((newPosy + TankGame.getImgSizeTank() * 60 / 512) > (wall.getPosy() + TankGame.getImgSizeWall()))
					|| ((newPosy + TankGame.getImgSizeTank() - TankGame.getImgSizeTank() * 60 / 512) < wall
							.getPosy()))) {
				return true;
			}
		}
		for (Tank tank : Board.EnemyTanks) {
			if (tank != this) {
				if (!(((newPosx + TankGame.getImgSizeTank() * 60 / 512) > (tank.getPosx() + TankGame.getImgSizeTank()
						- TankGame.getImgSizeTank() * 60 / 512))
						|| ((newPosx + TankGame.getImgSizeTank() - TankGame.getImgSizeTank() * 60 / 512) < tank
								.getPosx() + TankGame.getImgSizeTank() * 60 / 512)
						|| ((newPosy + TankGame.getImgSizeTank() * 60 / 512) > (tank.getPosy()
								+ TankGame.getImgSizeTank() - TankGame.getImgSizeTank() * 60 / 512))
						|| ((newPosy + TankGame.getImgSizeTank() - TankGame.getImgSizeTank() * 60 / 512) < tank
								.getPosy() + TankGame.getImgSizeTank() * 60 / 512))) {
					return true;
				}
			}
		}

		return false;
	}
}