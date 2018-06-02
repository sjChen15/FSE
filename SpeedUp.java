import java.awt.*;

public class SpeedUp {
    private Image powerup;

    private int px,py;
    private Rect spUpRect;
    private boolean available = true;

    public SpeedUp(Image powerup, int px, int py) {
        this.powerup = powerup;
        this.px = px;
        this.py = py;
        spUpRect = new Rect(px,py,10,10);
    }

    /*public void checkSpeedUp(Cat cat){
        if(cat.getCollideRect("cat").overlaps(spUpRect) && available){
            cat.speedUp();
            available = false;
        }
    }*/

    public void draw(Graphics g, GamePanel gamePanel){
        if(available){
            g.setColor(Color.GRAY);
            g.fillOval(px-5,py-5,10,10);
        }
    }
}
