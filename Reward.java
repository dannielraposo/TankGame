import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Reward {

    private int posx;
    private int posy;
    private String type;
    private boolean visible;
    private Image image;

    public Reward(int posx, int posy, String type) {
        this.posx = posx;
        this.posy = posy;
        this.type = type;
        this.visible = true;
        loadImage(type);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        
                    }
                }, 5 * 100);
            }
        }, 2 * 1000);
    }

  
    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public Image getImage() {
        return this.image;
    }

    public String getType() {
        return type;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return visible;
    }

    private void loadImage(String type) {
        ImageIcon imageIcon = new ImageIcon("Resources/" + this.type + ".png"); // load the image to a imageIcon
        Image scimage = imageIcon.getImage(); // transform it
        this.image = scimage.getScaledInstance((int) TankGame.getImgSizeReward(), (int) TankGame.getImgSizeReward(),
                java.awt.Image.SCALE_SMOOTH);
    }

}
