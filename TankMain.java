import java.awt.Point;
import java.awt.event.KeyEvent;

public class TankMain extends Tank {

    public boolean Wpressed = false;
    public boolean Spressed = false;
    public boolean Apressed = false;
    public boolean Dpressed = false;

    public TankMain(int initialx, int initialy, double initialAngle) {
        super(initialx, initialy, initialAngle, "Main Tank", "Resources/base_green.png",
                "Resources/cannon_green.png");
        this.setSpeed(5);
    }

    public void update(Point mousePoint) {

        double angle = (double) Math.toDegrees(
                Math.atan2(mousePoint.y - (this.getPosy() + TankGame.getImgSizeTank() / 2),
                        mousePoint.x - (this.getPosx() + TankGame.getImgSizeTank() / 2)));
        if (angle < 0) {
            angle += 360;
        }
        this.setShootAngle(angle);

        double newPosx = this.getPosx() + this.getDx();
        double newPosy = this.getPosy() + this.getDy();

        if (this.collides(newPosx, newPosy))
            return;

        this.setPosx(newPosx);
        this.setPosy(newPosy);

    }

    public void fire() {
        double dx = Math.cos(Math.toRadians(360 - getshootAngle()));
        double dy = -Math.sin(Math.toRadians(360 - getshootAngle()));
        Missile missile = new Missile(this.getPosx(), this.getPosy(), dx, dy, this.getshootAngle());
        Board.missiles.add(missile);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            this.setDy(-this.getSpeed());
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
            this.setDy(+this.getSpeed());
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
            this.setDx(+this.getSpeed());
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
            this.setDx(-this.getSpeed());
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
