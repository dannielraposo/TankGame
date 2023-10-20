import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private final int DELAY = 10;
	private String backgroundImage;
	private TankMain MainTank;
	private TankFast[] EnemyTanksBasic;
	static List<Missile> missiles = new ArrayList<>();
	static List<Wall> walls = new ArrayList<>();

	public static List<Wall> getWalls() {
		return walls;
	}

	public void generateTanksandTerrain(int GameLevel) {
		backgroundImage = "Resources/background" + GameLevel + ".png";

		if (GameLevel == 1) {
			MainTank = new TankMain(600, 200, 0);

			EnemyTanksBasic = new TankFast[1];
			EnemyTanksBasic[0] = new TankFast(500, 500, 270);
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
		for (int i = 0; i < EnemyTanksBasic.length; i++) {
			locationX = EnemyTanksBasic[i].getPosx() + TankGame.getImgSizeTank() / 2;
			locationY = EnemyTanksBasic[i].getPosy() + TankGame.getImgSizeTank() / 2;

			// Draw enemy tank base:
			Graphics2D gphEnemyBase = (Graphics2D) g2D.create();
			gphEnemyBase.rotate(Math.toRadians(EnemyTanksBasic[i].getmovementAngle()), locationX, locationY);
			gphEnemyBase.drawImage(EnemyTanksBasic[i].getImageBase(), (int) EnemyTanksBasic[i].getPosx(),
					(int) EnemyTanksBasic[i].getPosy(),
					this);
			gphEnemyBase.dispose();

			// Draw enemy tank cannon:
			Graphics2D gphEnemyCannon = (Graphics2D) g2D.create();
			gphEnemyCannon.rotate(Math.toRadians(EnemyTanksBasic[i].getshootAngle()), locationX, locationY);
			gphEnemyCannon.drawImage(EnemyTanksBasic[i].getImageCannon(), (int) EnemyTanksBasic[i].getPosx(),
					(int) EnemyTanksBasic[i].getPosy(),
					this);
			gphEnemyCannon.dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePoint, this);
		MainTank.update(mousePoint);

		for (Missile missile : missiles) {
			missile.move();
		}

		for (int i = 0; i < EnemyTanksBasic.length; i++) {
			EnemyTanksBasic[i].update(MainTank);
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

		for (int i = 0; i < ((int) ((TankGame.getGameWidth() / TankGame.getImgSizeWall()) / 2)); i++) {
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
		for (int i = 0; i < ((int) ((TankGame.getGameHeight() / TankGame.getImgSizeWall()) / 2) + 1); i++) {
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
				walls.add(new Wall(middle_point_horiz, middle_point_vert, "standard"));
				walls.add(new Wall((int) TankGame.getGameWidth() / 3, 0, "weak"));
				walls.add(new Wall((int) TankGame.getGameWidth() / 3, (int) TankGame.getImgSizeWall(), "weak"));
				walls.add(new Wall((int) TankGame.getGameWidth() / 3, 2 * (int) TankGame.getImgSizeWall(), "weak"));
				walls.add(new Wall((int) TankGame.getGameWidth() / 3, 3 * (int) TankGame.getImgSizeWall(), "weak"));
				// walls.add(new Wall(200, 0, "standard"));
				// walls.add(new Wall(300, 0, "standard"));

				walls.add(new Wall(300, 300, "reward"));

				walls.add(new Wall(600, 300, "weak"));

			case 2:

			case 3:

			default:
		}
	}
}
