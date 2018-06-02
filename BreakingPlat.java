import java.awt.*;

public class BreakingPlat extends Platform{
    private Image[] breakingPlats;

    private int px,py,w,h;
    private int breakingPoint = 120;

    private boolean broken = false;

    private Rect[] collideRects= new Rect[4];
    private Rect topRect,bottomRect,leftRect,rightRect;


    public BreakingPlat(int px, int py, int w, int h, Image[] bPlats) {
        super(px, py, w, h, bPlats[0]);
        this.px = px;
        this.py = py;
        this.w = w;
        this.h = h;

        breakingPlats = bPlats;

        topRect = new Rect(px,py-3,w,3);
        collideRects[0] = topRect;
        bottomRect = new Rect(px,py+h,w,3);
        collideRects[1] = bottomRect;
        leftRect = new Rect(px-3,py,3,h);
        collideRects[2] = leftRect;
        rightRect = new Rect(px+w,py,3,h);
        collideRects[3] = rightRect;
    }

    public int getX(){
        return px;
    }
    public int getY(){
        return py;
    }
    public int getHeight(){
        return h;
    }
    public int getWidth(){
        return w;
    }

    public boolean onPlat(Cat cat){
        if(!broken){
            if(cat.getNormalGravity() && topRect.overlaps(cat.getCollideRect("bottom"))){
                breakingPoint-=1;
                return true;
            }
            if(!cat.getNormalGravity() && bottomRect.overlaps(cat.getCollideRect("top"))){
                breakingPoint-=1;
                return true;
            }
        }
        return false;
    }

    public boolean leftCollidePlat(Cat cat){
        if(!broken){
            return cat.getCollideRect("cat").overlaps(leftRect);
        }
        return false;
    }

    public boolean rightCollidePlat(Cat cat){
        if(!broken){
            return cat.getCollideRect("cat").overlaps(rightRect);
        }
        return false;
    }

    public void checkBreakingPoint(){
        if(breakingPoint<=0){
            broken = true;
        }
    }
    public void draw(Graphics g, GamePanel gamePanel) {
        if(breakingPoint>=80){
            g.drawImage(breakingPlats[0],px,py,gamePanel);
        }
        else if(breakingPoint>=40){
            g.drawImage(breakingPlats[1],px,py,gamePanel);
        }
        else if(breakingPoint>0){
            g.drawImage(breakingPlats[2],px,py,gamePanel);
        }

        g.setColor(Color.ORANGE);
        g.drawRect(px,py-2,w,2);
        g.drawRect(px,py+h,w,2);
        g.drawRect(px-2,py,2,h);
        g.drawRect(px+w,py,2,h);
    }
}