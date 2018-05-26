

import java.awt.*;

public class Platform {
    private int px,py; //position of platform (top left corner)
    private int h,w; //width and height of platform
    
  //platform tings
    private Image platPic;
    private Rectangle[] collideRects= new Rectangle[4];
    private Rectangle platRect; //the actual platform
    private Rectangle topRect;
    private Rectangle bottomRect;
    private Rectangle leftRect;
    private Rectangle rightRect;

    public Platform(int px, int py, int w, int h,Image platPic) {
        this.px = px;
        this.py = py;
        this.h = h;
        this.w = w;

        this.platPic = platPic;
      
        platRect= new Rectangle(px,py,w,h);
        topRect = new Rectangle(px,py-3,w,3);
        collideRects[0] = topRect;
        bottomRect = new Rectangle(px,py+h,w,3);
        collideRects[1] = bottomRect;
        leftRect = new Rectangle(px-3,py,3,h);
        collideRects[2] = leftRect;
        rightRect = new Rectangle(px+w,py,3,h);
        collideRects[3] = rightRect;
    }

    public int getX(){
        return px;
    }
    public int getY(){
        return py;
    }
    public int getH(){
        return h;
    }
    public int getW(){
        return w;
    }

    //only works with top and bottom rects for now
    //returns cat's rectangle that has collided
    public Rectangle getCollideRect(Cat cat){
        for(Rectangle rect: collideRects){
            if(rect.contains(cat.getCollideRect("bottom"))){
                return cat.getCollideRect("bottom");
            }
            else if(rect.contains(cat.getCollideRect("bottom"))){
                return cat.getCollideRect("bottom");
            }
        }
        return null;
    }
  
    public boolean onPlat(Cat cat){
        if(cat.getNormalGravity() && topRect.contains(cat.getCollideRect("bottom"))){
            return true;
        }
        if(!cat.getNormalGravity() && bottomRect.contains(cat.getCollideRect("top"))){
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