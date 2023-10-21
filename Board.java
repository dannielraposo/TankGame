import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

	static private Timer timer;
	static private final int DELAY = 10;
	private String backgroundImage;
	static public TankMain MainTank;
	static public List<Tank> EnemyTanks = new ArrayList<>();
	static public List<Missile> missiles = new ArrayList<>();
	static public List<Wall> walls = new ArrayList<>();

	public void generateTanksandTerrain(int GameLevel) {
		backgroundImage = "Resources/background" + GameLevel + ".png";

		if (GameLevel == 1) {
			MainTank = new TankMain(2 * (int) TankGame.getImgSizeWall(), 2 * (int) TankGame.getImgSizeWall(), 0);

			EnemyTanks.add(new TankBasic(3 * (int) TankGame.getImgSizeWall(),
					(int) TankGame.getGameHeight() - 3 * (int) TankGame.getImgSizeWall(), 270));
			EnemyTanks.add(new TankBasic((int) TankGame.getGameWidth() - 3 * (int) TankGame.getImgSizeWall(),
					3 * (int) TankGame.getImgSizeWall(), 180));
			EnemyTanks.add(new TankFast((int) TankGame.getGameWidth() - 3 * (int) TankGame.getImgSizeWall(),
					(int) TankGame.getGameHeight() - 3 * (int) TankGame.getImgSizeWall(), 180));
			generateTerrain(GameLevel);
		}

		if (GameLevel == 2) {
			MainTank = new TankMain(0, 0, 0);
		}

		if (GameLevel == 3) {
			MainTank = new TankMain(0, 0, 0);
		}

	}

	public Board() {
		initBoard();
	}

	private void initBoard() {

		addKeyListener(new kbAdapter());
		addMouseListener(new mouseAdapter());
		setBackground(Color.white);
		setFocusable(true);
		setSize(TankGame.getGameWidth(), TankGame.getGameHeight());
		setPreferredSize(new Dimension(TankGame.getGameWidth(), TankGame.getGameHeight()));

		this.generateTanksandTerrain(1);
		timer = new Timer(DELAY, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D;
		g2D = (Graphics2D) g.create();
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		doDrawingBackground(g2D);
		doDrawingTanks(g2D);
		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawingBackground(Graphics g2D) {

		Graphics2D gphbackimage = (Graphics2D) g2D.create();
		ImageIcon imageBackground = new ImageIcon(backgroundImage);
		Image sciimageBackground = imageBackground.getImage();

		gphbackimage.drawImage(sciimageBackground, 0, 0, this);
		gphbackimage.dispose();

		List<Wall> walls2delete = new ArrayList<>();
		for (Wall wall : walls) {
			if (!wall.getVisible()) {
				walls2delete.add(wall);
			} else {
				Graphics2D gphWall = (Graphics2D) g2D.create();

				gphWall.drawImage(wall.getImage(), wall.getPosx(), wall.getPosy(), this);
				gphWall.dispose();
			}
		}
		walls.removeAll(walls2delete);
	}

	private void doDrawingTanks(Graphics g2D) {

		// DRAW MISSILES:
		List<Missile> missiles2delete = new ArrayList<>();

		for (Missile missile : missiles) {
			if (!missile.getVisible()) {
				missiles2delete.add(missile);
			} else {
				Graphics2D gphMissile = (Graphics2D) g2D.create();
				gphMissile.rotate(Math.toRadians(missile.getAngle()), missile.getPosx() + TankGame.getImgSizeTank() / 2,
						missile.getPosy() + TankGame.getImgSizeTank() / 2);
				gphMissile.drawImage(missile.getImage(), (int) (missile.getPosx()), (int) (missile.getPosy()), this);
				gphMissile.dispose();
			}
		}
		missiles.removeAll(missiles2delete);

		// DRAW MAIN TANK:
		double locationX = MainTank.getPosx() + TankGame.getImgSizeTank() / 2;
		double locationY = MainTank.getPosy() + TankGame.getImgSizeTank() / 2;

		// Base rotation information
		Graphics2D gphBase = (Graphics2D) g2D.create();
		gphBase.rotate(Math.toRadians(MainTank.getmovementAngle()), locationX, locationY);

		gphBase.drawImage(MainTank.getImageBase(), (int) (MainTank.getPosx()), (int) (MainTank.getPosy()), this);
		gphBase.dispose();
		// Turret rotation information
		Graphics2D gphCannon = (Graphics2D) g2D.create();
		gphCannon.rotate(Math.toRadians(MainTank.getshootAngle()), locationX, locationY);

		gphCannon.drawImage(MainTank.getImageCannon(), (int) (MainTank.getPosx()), (int) (MainTank.getPosy()), this);
		gphCannon.dispose();

		// DRAW ENEMY TANKS:
		List<Tank> tanks2delete = new ArrayList<>();

		for (Tank tank : EnemyTanks) {
			if (!tank.getVisible()) {
				tanks2delete.add(tank);
			} else {
				locationX = tank.getPosx() + TankGame.getImgSizeTank() / 2;
				locationY = tank.getPosy() + TankGame.getImgSizeTank() / 2;

				// Draw enemy tank base:
				Graphics2D gphEnemyBase = (Graphics2D) g2D.create();
				gphEnemyBase.rotate(Math.toRadians(tank.getmovementAngle()), locationX, locationY);
				gphEnemyBase.drawImage(tank.getImageBase(), (int) tank.getPosx(),
						(int) tank.getPosy(),
						this);
				gphEnemyBase.dispose();

				// Draw enemy tank cannon:
				Graphics2D gphEnemyCannon = (Graphics2D) g2D.create();
				gphEnemyCannon.rotate(Math.toRadians(tank.getshootAngle()), locationX, locationY);
				gphEnemyCannon.drawImage(tank.getImageCannon(), (int) tank.getPosx(),
						(int) tank.getPosy(),
						this);
				gphEnemyCannon.dispose();
			}
		}
		EnemyTanks.removeAll(tanks2delete);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePoint, this);
		MainTank.update(mousePoint);

		for (Missile missile : missiles) {
			missile.move();
		}

		for (Tank tank : EnemyTanks) {
			switch (tank.getTankType()) {
				case "TankBasic":
					((TankBasic) tank).update(MainTank);
					break;
				case "TankFast":
					((TankFast) tank).update(MainTank);
					break;
			}
		}

		repaint();
	}

	private class mouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			MainTank.fire();
		}
	}

	private class kbAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			MainTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			MainTank.keyPressed(e);
		}
	}

	public void generateTerrain(int gameLevel) {

		walls = new ArrayList<>();
		int middle_point_horiz = (int) TankGame.getGameWidth() / 2;
		int middle_point_vert = (int) TankGame.getGameHeight() / 2;

		/*
		 * Rectangle of standard walls to create arena: we use the middle point in the
		 * X and Y axis to draw the same number of blocks and have a block centered in
		 * the middle.
		 */

		/* Horizontal Walls */
		for (int i = 0; i < ((int) ((TankGame.getGameWidth() / TankGame.getImgSizeWall()) / 2) + 2); i++) {
			walls.add(new Wall((middle_point_horiz - (int) TankGame.getImgSizeWall() / 2)
					+ (i * ((int) TankGame.getImgSizeWall())), 0, "standard"));
			walls.add(new Wall((middle_point_horiz - (int) TankGame.getImgSizeWall() / 2)
					- (i * ((int) TankGame.getImgSizeWall())), 0, "standard"));
			walls.add(new Wall(
					(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2)
							+ (i * ((int) TankGame.getImgSizeWall())),
					(int) TankGame.getGameHeight() - (int) TankGame.getImgSizeWall(), "standard"));
			walls.add(new Wall(
					(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2)
							- (i * ((int) TankGame.getImgSizeWall())),
					(int) TankGame.getGameHeight() - (int) TankGame.getImgSizeWall(), "standard"));
		}

		/* Vertical Walls */
		for (int i = 0; i < ((int) ((TankGame.getGameHeight() / TankGame.getImgSizeWall()) / 2)); i++) {
			walls.add(new Wall(0,
					(middle_point_vert - (int) TankGame.getImgSizeWall() / 2) + (i * ((int) TankGame.getImgSizeWall())),
					"standard"));
			walls.add(new Wall(0,
					(middle_point_vert - (int) TankGame.getImgSizeWall() / 2) - (i * ((int) TankGame.getImgSizeWall())),
					"standard"));
			walls.add(new Wall((int) TankGame.getGameWidth() - (int) TankGame.getImgSizeWall(),
					(middle_point_vert - (int) TankGame.getImgSizeWall() / 2) + (i * ((int) TankGame.getImgSizeWall())),
					"standard"));
			walls.add(new Wall((int) TankGame.getGameWidth() - (int) TankGame.getImgSizeWall(),
					(middle_point_vert - (int) TankGame.getImgSizeWall() / 2) - (i * ((int) TankGame.getImgSizeWall())),
					"standard"));

		}

		switch (gameLevel) {

			case 1:
				walls.add(new Wall((middle_point_horiz) - 4 * (int) TankGame.getImgSizeWall(),
						(int) TankGame.getImgSizeWall(), "weak"));
				walls.add(new Wall((middle_point_horiz) - 4 * (int) TankGame.getImgSizeWall(),
						2 * (int) TankGame.getImgSizeWall(), "weak"));
				walls.add(new Wall((middle_point_horiz) - 4 * (int) TankGame.getImgSizeWall(),
						3 * (int) TankGame.getImgSizeWall(), "weak"));
				walls.add(new Wall((middle_point_horiz) - 4 * (int) TankGame.getImgSizeWall(),
						4 * (int) TankGame.getImgSizeWall(), "weak"));
				walls.add(new Wall((middle_point_horiz) - 6 * (int) TankGame.getImgSizeWall(),
						4 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall((middle_point_horiz) - 8 * (int) TankGame.getImgSizeWall(),
						4 * (int) TankGame.getImgSizeWall(), "standard"));

				walls.add(new Wall(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2,
						(int) TankGame.getGameHeight() - 2 * (int) TankGame.getImgSizeWall(), "weak"));
				walls.add(new Wall(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2,
						(int) TankGame.getGameHeight() - 3 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2,
						(int) TankGame.getGameHeight() - 4 * (int) TankGame.getImgSizeWall(), "standard"));

				walls.add(new Wall(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2,
						(int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2,
						2 * (int) TankGame.getImgSizeWall(), "weak"));
				walls.add(new Wall(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2,
						3 * (int) TankGame.getImgSizeWall(), "standard"));

				walls.add(new Wall((int) TankGame.getGameWidth() - 2 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - (int) TankGame.getImgSizeWall() / 2, "standard"));
				walls.add(new Wall((int) TankGame.getGameWidth() - 3 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - (int) TankGame.getImgSizeWall() / 2, "weak"));
				walls.add(new Wall((int) TankGame.getGameWidth() - 4 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - (int) TankGame.getImgSizeWall() / 2, "weak"));
				walls.add(new Wall((int) TankGame.getGameWidth() - 5 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - (int) TankGame.getImgSizeWall() / 2, "standard"));
				walls.add(new Wall((int) TankGame.getGameWidth() - 6 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - (int) TankGame.getImgSizeWall() / 2, "standard"));
				walls.add(new Wall((int) TankGame.getGameWidth() - 7 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - (int) TankGame.getImgSizeWall() / 2, "standard"));
				walls.add(new Wall((int) TankGame.getGameWidth() - 8 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - (int) TankGame.getImgSizeWall() / 2, "standard"));
				// walls.add(new Wall(300, 300, "reward"));

				// walls.add(new Wall(600, 300, "weak"));

			case 2:

			case 3:

			default:
		}
	}
}
