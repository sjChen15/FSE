

import java.awt.*;

public class Platform {
    private int px,py; //position of platform (top left corner)
    private int h,w; //width and height of platform
    
  //platform tings
    private Image platPic;
    private Rect[] collideRects= new Rect[4];
    private Rect topRect,bottomRect,leftRect,rightRect;

    public Platform(int px, int py, int w, int h,Image platPic) {
        this.px = px;
        this.py = py;
        this.h = h;
        this.w = w;

        this.platPic = platPic;

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
        if(cat.getNormalGravity() && topRect.overlaps(cat.getCollideRect("bottom"))){
            return true;
        }
        if(!cat.getNormalGravity() && bottomRect.overlaps(cat.getCollideRect("top"))){
            return true;
        }
        return false;
    }

    public boolean leftCollidePlat(Cat cat){
        if(cat.getCollideRect("cat").overlaps(leftRect)){
            return true;
        }
        return false;
    }

    public boolean rightCollidePlat(Cat cat){
        if(cat.getCollideRect("cat").overlaps(rightRect)){
            return true;
        }
        return false;
    }
      
    public void draw(Graphics g, GamePanel gamePanel) {
        g.drawImage(platPic,px,py,gamePanel);
        g.setColor(Color.ORANGE);
        g.drawRect(px,py-2,w,2);
        g.drawRect(px,py+h,w,2);
        g.drawRect(px-2,py,2,h);
        g.drawRect(px+w,py,2,h);
    }
}