import java.awt.*;

public class Laser {
    private int tx,ty; //position of the top laser node
    private int bx,by; //position of the bottom laser node
    private int sx,sy; //position of the switch
    private boolean on = true; //true if switch has not been touched, if so, laser is on as well

    private Image s;//switch image
    private Image topLaser;//the things that the laser shoots out of
    private Image bottomLaser;

    private Rect laserRect;
    private Rect switchRect;

    private Color colour;

    public Laser(int tx, int ty, int bx, int by, int sx, int sy,Color color) {
        this.tx = tx;
        this.ty = ty;
        this.bx = bx;
        this.by = by;
        this.sx = sx;
        this.sy = sy;
        /*this.s = s;
        this.topLaser = topLaser;
        this.bottomLaser = bottomLaser;*/
        colour = color;
        laserRect = new Rect(tx-5,ty,10,by-ty);
        switchRect = new Rect(sx-20,sy-50,40,50);
    }

    public void checkSwitch(Cat cat){
        if(cat.getCollideRect("cat").overlaps(switchRect) && on){
            on = false;
        }
    }

    public void checkCat(Cat cat){
        if(cat.getCollideRect("cat").overlaps(laserRect) && on){
            cat.setDead(true);
        }
    }

    public void draw(Graphics g, GamePanel gamePanel){
        g.setColor(colour);
        if(on){
            g.fillRect(tx-5,ty,10,by-ty);
        }

        g.fillRect(sx-20,sy-50,40,50);

        g.setColor(Color.darkGray);
        g.fillRect(tx-6,ty,12,5);
        g.fillRect(bx-6,by-5,12,5);

    }
}
