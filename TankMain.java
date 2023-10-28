import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class TankMain extends Tank {

    private boolean Wpressed = false;
    private boolean Spressed = false;
    private boolean Apressed = false;
    private boolean Dpressed = false;

    // REWARDS:
    private boolean triple_shot = false;
    private boolean double_speed = false;
    private boolean shield = false;
    private boolean ghost = false;

    public void setGhost(boolean ghost) {
        this.ghost = ghost;
        if (ghost)
            loadImage("Resources/base_green_ghost.png", "Resources/cannon_green_ghost.png");
        if (!ghost)
            loadImage("Resources/base_green.png", "Resources/cannon_green.png");
    }

    public void setShield(boolean shield) {
        this.shield = shield;
        if (shield)
            loadImage("Resources/base_green_shield.png", "Resources/cannon_green.png");
        if (!shield)
            loadImage("Resources/base_green.png", "Resources/cannon_green.png");
    }

    public boolean getShield() {
        return this.shield;
    }

    public boolean getGhost() {
        return this.ghost;
    }

    public TankMain(int initialx, int initialy, double initialAngle) {
        super(initialx, initialy, initialAngle, "TankMain", 3);
        loadImage("Resources/base_green.png", "Resources/cannon_green.png");
        this.setSpeed(2);
    }

    public void update(Point mousePoint) {

        double angle = (double) Math.toDegrees(
                Math.atan2(mousePoint.y - (this.getPosy() + TankGame.getImgSizeTank() / 2),
                        mousePoint.x - (this.getPosx() + TankGame.getImgSizeTank() / 2)));
        if (angle < 0) {
            angle += 360;
        }
        this.setShootAngle(angle);

        double newPosx = this.getPosx() + this.getDx() * this.getSpeed();
        double newPosy = this.getPosy() + this.getDy() * this.getSpeed();

        if (this.double_speed) {
            newPosx = this.getPosx() + this.getDx() * 2 * this.getSpeed();
            newPosy = this.getPosy() + this.getDy() * 2 * this.getSpeed();
        }

        // Check collisions:
        if (this.collides(newPosx, newPosy) && !this.ghost)
            return;

        // Check collision with screen margins (for ghost mode)
        if (newPosx < 0 || (newPosx + TankGame.getImgSizeTank()) > TankGame.getGameWidth() || newPosy < 0
                || (newPosy + TankGame.getImgSizeTank()) > TankGame.getGameHeight()) {
            return;
        }

        this.setPosx(newPosx);
        this.setPosy(newPosy);

        // Check if takes a reward:
        for (Reward reward : Board.rewards) {
            if (!(((newPosx + TankGame.getImgSizeTank() * 60 / 512) > (reward.getPosx() + TankGame.getImgSizeReward()))
                    || ((newPosx + TankGame.getImgSizeTank() - TankGame.getImgSizeTank() * 60 / 512) < reward.getPosx())
                    || ((newPosy + TankGame.getImgSizeTank() * 60 / 512) > (reward.getPosy()
                            + TankGame.getImgSizeReward()))
                    || ((newPosy + TankGame.getImgSizeTank() - TankGame.getImgSizeTank() * 60 / 512) < reward
                            .getPosy()))) {

                reward.setVisible(false);
                switch (reward.getType()) {
                    case "reward_1up":
                        this.incrlives();
                        break;

                    case "reward_3shot":
                        this.triple_shot = true;
                        Timer timer3shot = new Timer();
                        timer3shot.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Board.MainTank.triple_shot = false;
                            }
                        }, 5 * 1000);
                        break;

                    case "reward_energy":
                        this.double_speed = true;
                        Timer timer2speed = new Timer();
                        timer2speed.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Board.MainTank.double_speed = false;
                            }
                        }, 5 * 1000);
                        break;
                    case "reward_ghost":
                        setGhost(true);
                        Timer timerghost = new Timer();
                        timerghost.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Board.MainTank.setGhost(false);
                                if (Board.MainTank.collides(Board.MainTank.getPosx(), Board.MainTank.getPosy())) {
                                    Board.MainTank.decrlives();
                                    System.out.println("stucked after ghost mode");
                                }
                            }
                        }, 5 * 1000);
                        break;
                    case "reward_shield":
                        setShield(true);
                        Timer timershield = new Timer();
                        timershield.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Board.MainTank.setShield(false);
                            }
                        }, 15 * 1000);
                        break;

                }
            }
        }

    }

    public void fire() {
        double dx = Math.cos(Math.toRadians(360 - getshootAngle()));
        double dy = -Math.sin(Math.toRadians(360 - getshootAngle()));
        Missile missile = new Missile(this.getPosx(), this.getPosy(), dx, dy, this.getshootAngle(), "main",
                this.double_speed);
        Board.missiles.add(missile);

        if (this.triple_shot) {
            double dx2 = Math.cos(Math.toRadians(360 - this.getshootAngle() + 20));
            double dy2 = -Math.sin(Math.toRadians(360 - this.getshootAngle() + 20));
            Missile missile2 = new Missile(this.getPosx(), this.getPosy(), dx2, dy2, this.getshootAngle(), "main",
                    this.double_speed);
            Board.missiles.add(missile2);

            double dx3 = Math.cos(Math.toRadians(360 - this.getshootAngle() - 20));
            double dy3 = -Math.sin(Math.toRadians(360 - this.getshootAngle() - 20));
            Missile missile3 = new Missile(this.getPosx(), this.getPosy(), dx3, dy3, this.getshootAngle(), "main",
                    this.double_speed);
            Board.missiles.add(missile3);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            this.setDy(-1);
            Wpressed = true;
            if (!Apressed && !Dpressed) {
                this.setMovementAngle(270);
                return;
            }
            if (Apressed) {
                this.setMovementAngle(225);
                return;
            }
            if (Dpressed) {
                this.setMovementAngle(315);
                return;
            }
        }

        if (key == KeyEvent.VK_S) {
            this.setDy(+1);
            Spressed = true;

            if (!Apressed && !Dpressed) {
                this.setMovementAngle(90);
                return;
            }
            if (Apressed) {
                this.setMovementAngle(135);
                return;
            }
            if (Dpressed) {
                this.setMovementAngle(45);
                return;
            }
        }

        if (key == KeyEvent.VK_D) {
            this.setDx(+1);
            Dpressed = true;
            if (!Wpressed && !Spressed) {
                this.setMovementAngle(0);
                return;
            }
            if (Wpressed) {
                this.setMovementAngle(315);
                return;
            }
            if (Spressed) {
                this.setMovementAngle(45);
                return;
            }

        }
        if (key == KeyEvent.VK_A) {
            this.setDx(-1);
            Apressed = true;
            if (!Wpressed && !Spressed) {
                this.setMovementAngle(180);
                return;
            }
            if (Wpressed) {
                this.setMovementAngle(225);
                return;
            }
            if (Spressed) {
                this.setMovementAngle(135);
                return;
            }
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_D) {
            this.setDx(0);
            Dpressed = false;
            if (!Wpressed && !Spressed) {
                return;
            }
            if (Wpressed) {
                this.setMovementAngle(270);
                return;
            }
            if (Spressed) {
                this.setMovementAngle(90);
                return;
            }
        }
        if (key == KeyEvent.VK_A) {
            this.setDx(0);
            Apressed = false;
            if (!Wpressed && !Spressed) {
                return;
            }
            if (Wpressed) {
                this.setMovementAngle(270);
                return;
            }
            if (Spressed) {
                this.setMovementAngle(90);
                return;
            }
        }
        if (key == KeyEvent.VK_S) {
            this.setDy(0);
            Spressed = false;
            if (!Apressed && !Dpressed) {
                return;
            }
            if (Apressed) {
                this.setMovementAngle(180);
                return;
            }
            if (Dpressed) {
                this.setMovementAngle(0);
                return;
            }
        }
        if (key == KeyEvent.VK_W) {
            this.setDy(0);
            Wpressed = false;
            if (!Apressed && !Dpressed) {
                return;
            }
            if (Apressed) {
                this.setMovementAngle(180);
                return;
            }
            if (Dpressed) {
                this.setMovementAngle(0);
                return;
            }
        }
    }

}
