package com.company;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;


public class Cat {
    //ints
    private int px,py;
    private int jumpV; //the jumping velocity


    //images
    //NOTE ALL CAT PICTURES ARE 40x61 PIXELS
    private Image normal;
    private Image upsideDown;
  
    private int catnum = 0;
    private int count = 0;
  
    private ArrayList<Image> normCatsR = new ArrayList<Image>();
    private ArrayList<Image> normCatsL = new ArrayList<Image>();


    //booleans
    private boolean falling = true; //if true, not on
    private boolean dead = false; //true if the player has died
    private boolean normalGravity = true;
    private boolean direction = true;

    //rects
    private Rectangle topRect;
    private Rectangle bottomRect;
    private Rectangle leftRect;
    private Rectangle rightRect;

    

    

    public Cat(int px, int py, Image normal, Image upsideDown, ArrayList<Image> nCatsR, ArrayList<Image>nCatsL) {      
        this.px = px;
        this.py = py;
        this.normal = normal;
        this.upsideDown = upsideDown;

        topRect = new Rectangle(px+10,py,20,1);
        bottomRect = new Rectangle(px+10,py+60,20,1);
        leftRect = new Rectangle(px,py,1,61);
        rightRect = new Rectangle(px+39,py,1,61);

        this.normCatsR = nCatsR;
        this.normCatsL = nCatsL;

    }

    public void setFalling(boolean f){
        falling = f;
        if(!falling){
            jumpV = 0;
        }
    }

    public void setDead(boolean d){
        dead = d;
    }
    public void setCoords(int x, int y){
        px = x;
        py = y;
    }

    public void setNormalGravity(boolean g){
        normalGravity = g;
    }

    public void addX(int x){
        if( x < 0 ){
            direction = false;
        }
        else{
            direction = true;
        }
        if(px+x>0 && px+x<600){
            px+=x;

        }
        count += 1;
        if(count == 10){
            catnum = 0;
        }
        else if (count == 20){
            catnum = 1;
            count = 0;
        }
    }

    public void addY(int y){
        if(falling){
            py+= y;
            if(py+y<0 || py+y>600) {
                dead = true;
            }
        }

    }

    public int getX(){
        return px;
    }

    public int getY(){
        return py;
    }

    public boolean getNormalGravity(){
        return normalGravity;
    }

    public Rectangle getCollideRect(String pos){
        if(pos.equals("top")){
            return topRect;
        }
        else if(pos.equals("bottom")){
            return bottomRect;
        }
        else if(pos.equals("left")){
            return leftRect;
        }
        else if(pos.equals("right")){
            return rightRect;
        }
        System.out.println("check getCollideRect");
        return null;
    }

    public boolean isFalling(){
        return falling;
    }

    //returns true if player is dead
    public boolean isDead(){
        return dead;
    }


    public void updateCollideRects(){
        topRect = new Rectangle(px+10,py,20,1);
        bottomRect = new Rectangle(px+10,py+60,20,1);
        leftRect = new Rectangle(px,py,1,61);
        rightRect = new Rectangle(px+39,py,1,61);
    }


    public void jump(){
        if(!falling){
            falling = true;
            jumpV = -15;
        }
    }

    public void checkJumpV(){
        if(jumpV<0){
            jumpV += 1;
        }
        addY(jumpV);
    }

    public void draw(Graphics g, GamePanel gamePanel){
        if(normalGravity){
            g.drawImage(normal,px,py,gamePanel);
        }
        else{
            g.drawImage(upsideDown,px,py,gamePanel);
        }
        g.setColor(Color.GREEN);
        g.drawRect(px+10,py,20,1);
        g.drawRect(px+10,py+60,20,1);
        g.drawRect(px,py,1,61);
        g.drawRect(px+39,py,1,61);

    }

    public void setOnPlat(Platform platform,boolean on) {
        if(on) {
            if(normalGravity){
                py = platform.getY()-61;
            }
            else{
                py = platform.getY()+platform.getH();
            }
            falling = false;
        }
        else {
            if (normalGravity) {
                py-=3;

            }
            else {
                py+=3;

            }
            falling = true;
        }
            if(platform.getCollideRect(this).equals(topRect)){
                py += platform.getY()+platform.getH()+4; //extra 3 so it stops colliding
            }
            else if(platform.getCollideRect(this).equals(bottomRect)){
                py += platform.getY()-4;
            }

            falling = true;
        }
    }
}
