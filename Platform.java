//Platform.java
//the Platform object class
//the player walks on Platforms

import java.awt.*;

public class Platform {
    private int px,py; //position of platform (top left corner)
    private int h,w; //width and height of platform
    private int collideW = 3; //width of the collide rects

    private Image platPic; //image of platform

    private Rect[] collideRects= new Rect[4]; //the list that holds the collide Rects
    private Rect topRect,bottomRect,leftRect,rightRect; //the four rectangles around the platform tat check for collisions

    private Cat cat;

    //constructor, takes the platform's position, width, height, picture and player
    public Platform(int px, int py, int w, int h,Image platPic, Cat cat) {
        this.px = px;
        this.py = py;
        this.h = h;
        this.w = w;

        this.platPic = platPic;

        this.cat = cat;

        //create collide rects with constructor information
        topRect = new Rect(px,py-collideW,w,collideW);
        collideRects[0] = topRect;
        bottomRect = new Rect(px,py+h,w,collideW);
        collideRects[1] = bottomRect;
        leftRect = new Rect(px-collideW,py,collideW,h);
        collideRects[2] = leftRect;
        rightRect = new Rect(px+w,py,collideW,h);
        collideRects[3] = rightRect;
    }

    //returns the x position of the platform
    public int getX(){
        return px;
    }
    //returns the y position of the platform
    public int getY(){
        return py;
    }
    //returns the height of the platform
    public int getHeight(){
        return h;
    }
    //returns the width of the platform
    public int getWidth(){
        return w;
    }

    //returns true if the player is on the platform
    public boolean onPlat(){
        //checks if the cat is 'on top' of the platform relative to each gravity
        if(cat.getNormalGravity() && topRect.overlaps(cat.getCollideRect("bottom"))){
            return true;
        }
        if(!cat.getNormalGravity() && bottomRect.overlaps(cat.getCollideRect("top"))){
            return true;
        }
        return false;
    }

    //checks if the player is colliding with the left side of the platform
    public boolean leftCollidePlat(){
        if(cat.getCollideRect("cat").overlaps(leftRect)){
            return true;
        }
        return false;
    }

    //checks if the player is colliding with the right side of the platform
    public boolean rightCollidePlat(){
        if(cat.getCollideRect("cat").overlaps(rightRect)){
            return true;
        }
        return false;
    }

    //returns true if the player is hitting the bottom of the platform
    public boolean bottomCollidePlat(){
        if(cat.getNormalGravity() &&bottomRect.overlaps(cat.getCollideRect("top"))){
            return true;
        }
        if(!cat.getNormalGravity() &&topRect.overlaps(cat.getCollideRect("bottom"))){
            return true;
        }
        return false;
    }

    //draws the platform
    public void draw(Graphics g, GamePanel gamePanel) {
        g.drawImage(platPic,px,py,gamePanel);
        g.setColor(Color.ORANGE);
        g.drawRect(px,py-collideW,w,collideW);
        g.drawRect(px,py+h,w,collideW);
        g.drawRect(px-collideW,py,collideW,h);
        g.drawRect(px+w,py,collideW,h);
    }
}