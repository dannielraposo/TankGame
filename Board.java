import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends JPanel {

	private boolean firstInit = true;
	public Timer timer;
	static private final int DELAY = 10;
	private int currentGameLevel = 1;
	private String backgroundImage;
	static public TankMain MainTank;
	static public List<Tank> EnemyTanks = new ArrayList<>();
	static public List<Missile> missiles = new ArrayList<>();
	static public List<Wall> walls = new ArrayList<>();
	static private Wall finishWall;
	static public int middle_point_horiz = (int) TankGame.getGameWidth() / 2;
	static public int middle_point_vert = (int) TankGame.getGameHeight() / 2;
	static public List<Reward> rewards = new ArrayList<>();

	public void generateTanksandTerrain(int GameLevel) {
		backgroundImage = "Resources/background" + GameLevel + ".png";
		EnemyTanks = new ArrayList<>();
		missiles = new ArrayList<>();
		walls = new ArrayList<>();

		if (GameLevel == 1) {
			MainTank = new TankMain(2 * (int) TankGame.getImgSizeWall(), 2 * (int) TankGame.getImgSizeWall(), 0);

			EnemyTanks.add(new TankBasic(3 * (int) TankGame.getImgSizeWall(),
					(int) TankGame.getGameHeight() - 3 * (int) TankGame.getImgSizeWall(), 270));
			// EnemyTanks.add(new TankBasic((int) TankGame.getGameWidth() - 3 * (int)
			// TankGame.getImgSizeWall(),
			// 3 * (int) TankGame.getImgSizeWall(), 180));
			// EnemyTanks.add(new TankBasic((int) TankGame.getGameWidth() - 3 * (int)
			// TankGame.getImgSizeWall(),
			// (int) TankGame.getGameHeight() - 3 * (int) TankGame.getImgSizeWall(), 180));
			generateTerrain(GameLevel);
		}

		else if (GameLevel == 2) {
			MainTank = new TankMain(2 * (int) TankGame.getImgSizeWall(), 2 * (int) TankGame.getImgSizeWall(), 0);

			EnemyTanks.add(new TankMoves(3 * (int) TankGame.getImgSizeWall(),
					(int) TankGame.getGameHeight() - 3 * (int) TankGame.getImgSizeWall(), 270));
			// EnemyTanks.add(new TankMoves((int) TankGame.getGameWidth() - 3 * (int)
			// TankGame.getImgSizeWall(),
			// 2 * (int) TankGame.getImgSizeWall(), 180));
			// EnemyTanks.add(new TankMoves((int) TankGame.getGameWidth() - 2 * (int)
			// TankGame.getImgSizeWall(),
			// (int) TankGame.getGameHeight() - 2 * (int) TankGame.getImgSizeWall(), 225));
			generateTerrain(GameLevel);

		}

		else if (GameLevel == 3) {
			MainTank = new TankMain(middle_point_horiz, middle_point_vert, 0);

			EnemyTanks.add(new TankBasic(middle_point_horiz + 7 * (int) TankGame.getImgSizeWall(),
					middle_point_vert, 0));
			EnemyTanks.add(new TankBasic(middle_point_horiz - 8 * (int) TankGame.getImgSizeWall(),
					middle_point_vert, 180));
			EnemyTanks.add(new TankBasic(middle_point_horiz + 7 * (int) TankGame.getImgSizeWall(),
					middle_point_vert - (int) TankGame.getImgSizeWall(), 0));
			EnemyTanks.add(new TankBasic(middle_point_horiz - 8 * (int) TankGame.getImgSizeWall(),
					middle_point_vert - (int) TankGame.getImgSizeWall(), 180));
			EnemyTanks.add(new TankBasic(middle_point_horiz + 7 * (int) TankGame.getImgSizeWall(),
					middle_point_vert + (int) TankGame.getImgSizeWall(), 0));
			EnemyTanks.add(new TankBasic(middle_point_horiz - 8 * (int) TankGame.getImgSizeWall(),
					middle_point_vert + (int) TankGame.getImgSizeWall(), 180));
			generateTerrain(GameLevel);
		}

		else if (GameLevel == 4) {
			MainTank = new TankMain(middle_point_horiz, middle_point_vert, 0);

			EnemyTanks.add(new TankBasic(middle_point_horiz + 7 * (int) TankGame.getImgSizeWall(),
					middle_point_vert, 0));
			EnemyTanks.add(new TankBasic(middle_point_horiz - 8 * (int) TankGame.getImgSizeWall(),
					middle_point_vert, 180));
			EnemyTanks.add(new TankBasic(middle_point_horiz + 7 * (int) TankGame.getImgSizeWall(),
					middle_point_vert - (int) TankGame.getImgSizeWall(), 0));
			EnemyTanks.add(new TankBasic(middle_point_horiz - 8 * (int) TankGame.getImgSizeWall(),
					middle_point_vert - (int) TankGame.getImgSizeWall(), 180));
			EnemyTanks.add(new TankBasic(middle_point_horiz + 7 * (int) TankGame.getImgSizeWall(),
					middle_point_vert + (int) TankGame.getImgSizeWall(), 0));
			EnemyTanks.add(new TankBasic(middle_point_horiz - 8 * (int) TankGame.getImgSizeWall(),
					middle_point_vert + (int) TankGame.getImgSizeWall(), 180));
			generateTerrain(GameLevel);
		}
	}

	public Board() {
		initBoard();
	}

	public void initBoard() {
		if (firstInit) {
			addKeyListener(new kbAdapter());
			addMouseListener(new mouseAdapter());
			setBackground(Color.white);
			setFocusable(true);
			setSize(TankGame.getGameWidth(), TankGame.getGameHeight());
			setPreferredSize(new Dimension(TankGame.getGameWidth(), TankGame.getGameHeight()));
			firstInit = false;
		}
		this.generateTanksandTerrain(currentGameLevel);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				heartbeat();
			}
		}, DELAY, DELAY);
	}

	// action performed every DELAY
	public void heartbeat() {

		if (EnemyTanks.size() == 0 && currentGameLevel != 4) {
			currentGameLevel++;
			TankGame.changeLevel(currentGameLevel);
		}

		else if (currentGameLevel != 4 && !(((MainTank.getPosx()
				+ TankGame.getImgSizeTank() * 60 / 512) > (finishWall.getPosx() + TankGame.getImgSizeWall()))
				|| ((MainTank.getPosx() + TankGame.getImgSizeTank() - TankGame.getImgSizeTank() * 60 / 512) < finishWall
						.getPosx())
				|| ((MainTank.getPosy() + TankGame.getImgSizeTank() * 60 / 512) > (finishWall.getPosy()
						+ TankGame.getImgSizeWall()))
				|| ((MainTank.getPosy() + TankGame.getImgSizeTank() - TankGame.getImgSizeTank() * 60 / 512) < finishWall
						.getPosy())))
			if (MainTank.getLives() == 0) {
				currentGameLevel++;
				TankGame.changeLevel(currentGameLevel);
			}

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
				case "TankMoves":
					((TankMoves) tank).update(MainTank);
					break;
				case "TankHard":
					((TankHard) tank).update(MainTank);
					break;
				case "TankTriple":
					((TankTriple) tank).update(MainTank);
					break;
			}
		}

		repaint(); // calls paintComponent() everytime

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // cleans the screen

		Graphics2D g2D;
		g2D = (Graphics2D) g.create();
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // makes the borders
																									// of the figures
		doDrawingBackground(g);
		doDrawingTanks(g);
		Toolkit.getDefaultToolkit().sync();
	}

	public void LostLife() {
		if (MainTank.getLives() != 0) {
			// JOptionPane.showMessageDialog(this, "Lifes Remaining: " +
			// MainTank.getLives(), "Info", JOptionPane.YES_NO_OPTION);
			// this.generateTanksandTerrain(currentGameLevel);
			// super.paint(getGraphics());
			// repaint();

			// showImage();
			// JOptionPane.showMessageDialog(this, "Game Over", "Game Over",
			// JOptionPane.YES_NO_OPTION);
			// System.exit(ABORT);
		}

	}

	private void doDrawingBackground(Graphics g) {

		// Draw background image:
		Graphics2D gphbackimage = (Graphics2D) g.create();
		ImageIcon imageBackground = new ImageIcon(backgroundImage);
		Image sciimageBackground = imageBackground.getImage();
		gphbackimage.drawImage(sciimageBackground, 0, 0, this);
		gphbackimage.dispose();

		// Draw walls:
		List<Wall> walls2delete = new ArrayList<>();
		for (Wall wall : walls) {
			if (!wall.getVisible()) {
				walls2delete.add(wall);
			} else {
				Graphics2D gphWall = (Graphics2D) g.create();

				gphWall.drawImage(wall.getImage(), wall.getPosx(), wall.getPosy(), this);
				gphWall.dispose();
			}
		}
		walls.removeAll(walls2delete);

		// Draw rewards:
		List<Reward> rewards2delete = new ArrayList<>();
		for (Reward reward : rewards) {
			if (!reward.getVisible()) {
				rewards2delete.add(reward);
			} else {
				Graphics2D gphReward = (Graphics2D) g.create();

				gphReward.drawImage(reward.getImage(), reward.getPosx(), reward.getPosy(), this);
				gphReward.dispose();
			}
		}
		rewards.removeAll(rewards2delete);
	}

	private void doDrawingTanks(Graphics g) {

		// DRAW MISSILES:
		List<Missile> missiles2delete = new ArrayList<>();

		for (Missile missile : missiles) {
			if (!missile.getVisible()) {
				missiles2delete.add(missile);
			} else {
				Graphics2D gphMissile = (Graphics2D) g.create();
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
		Graphics2D gphBase = (Graphics2D) g.create();
		gphBase.rotate(Math.toRadians(MainTank.getmovementAngle()), locationX, locationY);

		gphBase.drawImage(MainTank.getImageBase(), (int) (MainTank.getPosx()), (int) (MainTank.getPosy()), this);
		gphBase.dispose();
		// Turret rotation information
		Graphics2D gphCannon = (Graphics2D) g.create();
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
				Graphics2D gphEnemyBase = (Graphics2D) g.create();
				gphEnemyBase.rotate(Math.toRadians(tank.getmovementAngle()), locationX, locationY);
				gphEnemyBase.drawImage(tank.getImageBase(), (int) tank.getPosx(),
						(int) tank.getPosy(),
						this);
				gphEnemyBase.dispose();

				// Draw enemy tank cannon:
				Graphics2D gphEnemyCannon = (Graphics2D) g.create();
				gphEnemyCannon.rotate(Math.toRadians(tank.getshootAngle()), locationX, locationY);
				gphEnemyCannon.drawImage(tank.getImageCannon(), (int) tank.getPosx(),
						(int) tank.getPosy(),
						this);
				gphEnemyCannon.dispose();
			}
		}
		EnemyTanks.removeAll(tanks2delete);

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
						2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall((middle_point_horiz) - 4 * (int) TankGame.getImgSizeWall(),
						3 * (int) TankGame.getImgSizeWall(), "weak"));
				walls.add(new Wall((middle_point_horiz) - 4 * (int) TankGame.getImgSizeWall(),
						4 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall((middle_point_horiz) - 6 * (int) TankGame.getImgSizeWall(),
						4 * (int) TankGame.getImgSizeWall(), "weak"));
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

				break;

			case 2:

				walls.add(new Wall(middle_point_horiz - 2 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz + 2 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz + 2 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz - 2 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));

				walls.add(new Wall(middle_point_horiz - 4 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz + 4 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz + 4 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz - 4 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));

				walls.add(new Wall(middle_point_horiz - 6 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz + 6 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz + 6 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz - 6 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));

				walls.add(new Wall(middle_point_horiz,
						middle_point_vert, "reward"));

				break;
			case 3:

				walls.add(new Wall(middle_point_horiz + (int) TankGame.getImgSizeWall() / 2,
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2,
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz + (int) TankGame.getImgSizeWall() / 2 + (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz - (int) TankGame.getImgSizeWall() / 2 - (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz + (int) TankGame.getImgSizeWall() / 2 + 2 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz - (int) TankGame.getImgSizeWall() / 2 - 2 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz + (int) TankGame.getImgSizeWall() / 2 + 3 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz - (int) TankGame.getImgSizeWall() / 2 - 3 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz + (int) TankGame.getImgSizeWall() / 2 + 4 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz - (int) TankGame.getImgSizeWall() / 2 - 4 * (int) TankGame.getImgSizeWall(),
						middle_point_vert - 2 * (int) TankGame.getImgSizeWall(), "standard"));

				walls.add(new Wall(middle_point_horiz + (int) TankGame.getImgSizeWall() / 2,
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(middle_point_horiz - (int) TankGame.getImgSizeWall() / 2,
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz + (int) TankGame.getImgSizeWall() / 2 + (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz - (int) TankGame.getImgSizeWall() / 2 - (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz + (int) TankGame.getImgSizeWall() / 2 + 2 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz - (int) TankGame.getImgSizeWall() / 2 - 2 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz + (int) TankGame.getImgSizeWall() / 2 + 3 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz - (int) TankGame.getImgSizeWall() / 2 - 3 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz + (int) TankGame.getImgSizeWall() / 2 + 4 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));
				walls.add(new Wall(
						middle_point_horiz - (int) TankGame.getImgSizeWall() / 2 - 4 * (int) TankGame.getImgSizeWall(),
						middle_point_vert + 2 * (int) TankGame.getImgSizeWall(), "standard"));

				break;
			case 4:
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
				break;
			default:
		}
	}
}
