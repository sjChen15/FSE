//BreakingPlat
//belongs to the Platform object class
//its special feature is that it breaks after the player stands on it for sometime


import java.awt.*;

public class BreakingPlat extends Platform{
    private Image[] breakingPlats;  //the list of the different pictures of the breakingPlat

    private int px,py,w,h;  //the x and y position of the platform and its width and height
    private int breakingPoint = 120;    //if 0, the platform is no longer there
    private int collideW = 4; //the width of the collide rects

    private boolean broken = false; //if true, the platform is no longer there

    //TODO: get good pictures, change size if needed, delete what is in draw
    private Rect[] collideRects= new Rect[4];   //the list that holds the collide Rects
    private Rect topRect,bottomRect,leftRect,rightRect; //the four rectangles around the platform tat check for collisions

    private Cat cat; //the player

    //constructor, takes the breakingPlat's position, width, height, images and the player
    public BreakingPlat(int px, int py, int w, int h, Image[] bPlats, Cat cat) {
        super(px, py, w, h, bPlats[0],cat);
        this.px = px;
        this.py = py;
        this.w = w;
        this.h = h;

        breakingPlats = bPlats;

        this.cat = cat;

        //the collide Rects are constructed using the constructor information
        topRect = new Rect(px,py-collideW,w,collideW);
        collideRects[0] = topRect;
        bottomRect = new Rect(px,py+h,w,collideW);
        collideRects[1] = bottomRect;
        leftRect = new Rect(px-collideW,py,collideW,h);
        collideRects[2] = leftRect;
        rightRect = new Rect(px+w,py,collideW,h);
        collideRects[3] = rightRect;
    }

    //returns true if the cat is on the platform
    public boolean onPlat(){
        if(!broken){//ensures the platform is still there
            //checks if the cat is 'on top' of the platform relative to each gravity
            if(cat.getNormalGravity() && topRect.overlaps(cat.getCollideRect("bottom"))){
                breakingPoint-=1; //if the cat is on the platform, the platform breaks more
                return true;
            }
            if(!cat.getNormalGravity() && bottomRect.overlaps(cat.getCollideRect("top"))){
                breakingPoint-=1; //if the cat is on the platform, the platform breaks more
                return true;
            }
        }
        return false;
    }

    //returns true if the player is colliding with the left side of the platform
    public boolean leftCollidePlat(){
        if(!broken){ //ensures the platform isn't broken
            return cat.getCollideRect("cat").overlaps(leftRect);
        }
        return false;
    }

    //returns true if the player is colliding with the right side of the platform
    public boolean rightCollidePlat(){
        if(!broken){//ensures the platform isn't broken
            return cat.getCollideRect("cat").overlaps(rightRect);
        }
        return false;
    }

    //returns true if the player is hitting the bottom of the platform
    public boolean bottomCollidePlat(){
        if(!broken){ //check if the platform is still there
            if(cat.getNormalGravity() && bottomRect.overlaps(cat.getCollideRect("top"))){
                return true;
            }
            if(!cat.getNormalGravity() &&topRect.overlaps(cat.getCollideRect("bottom"))){
                return true;
            }
        }

        return false;
    }

    //checks if the platform is broken or not
    public void checkBreakingPoint(){
        if(breakingPoint<=0){ //if there is no counter left, the platform is broken
            broken = true;
        }
    }

    //resets the variables to the initial values
    public void reset(){
        broken = false;
        breakingPoint = 120;
    }

    //draws the breakingPlatform
    public void draw(Graphics g, GamePanel gamePanel) {
        if(breakingPoint>=80){  //platform still looks normal
            g.drawImage(breakingPlats[0],px,py,gamePanel);
        }
        else if(breakingPoint>=40){ //platform loses grass
            g.drawImage(breakingPlats[1],px,py,gamePanel);
        }
        else if(breakingPoint>0){   //platform looks cracked
            g.drawImage(breakingPlats[2],px,py,gamePanel);
        }
        //if there is no counter, no picture is drawn
        g.setColor(Color.ORANGE);
        g.drawRect(px,py-collideW,w,collideW);
        g.drawRect(px,py+h,w,collideW);
        g.drawRect(px-collideW,py,collideW,h);
        g.drawRect(px+w,py,collideW,h);
    }
}