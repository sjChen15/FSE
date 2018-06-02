import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Cat {
    //ints
    private int px,py;
    private int jumpV; //the jumping velocity

    //ints
    private int catnum = 0;
    private int count = 0;
    private int jumpCounter;

    //images
    //NOTE ALL CAT PICTURES ARE 40x61 PIXELS
    private Image[] normCatsR,normCatsL, upsideCatsR, upsideCatsL;
    private Point[] catPoints= new Point[8]; //holds 8 points around the cat, used to check if cat has ran into a saw

    //booleans
    private boolean falling = true; //if true, not on
    private boolean dead = false; //true if the player has died
    private boolean normalGravity = true;
    private boolean direction = true; //true if going right
    private boolean standing = true;
    private boolean jumping = false;
    private boolean canGoRight = true;
    private boolean canGoLeft = true;

    //rects
    private Rect catRect,topRect,bottomRect,leftRect,rightRect;

    public Cat(int px, int py,Image[] nCatsR, Image[] nCatsL, Image[] uCatsR, Image[] uCatsL) {
        this.px = px;
        this.py = py;

        normCatsR = nCatsR;
        normCatsL = nCatsL;

        upsideCatsR = uCatsR;
        upsideCatsL = uCatsL;

        updateCatPoints();
        updateCollideRects();
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

    public void setCanGoRight(boolean canGoRight) {
        this.canGoRight = canGoRight;
    }

    public void setCanGoLeft(boolean canGoLeft) {
        this.canGoLeft = canGoLeft;
    }

    public void setJumping(boolean j){ jumping = j;}

    public void setStanding(boolean s){ standing = s;}
    public void addX(int x){
        if( x < 0 ){
            direction = false;
            if(canGoLeft && px+x>0){
                px+=x;
            }
        }
        else{
            direction = true;
            if(canGoRight && px+x<800){
                px+=x;
            }
        }
        count += 1;
        if(count == 10){
            catnum = 0;
        }
        else if (count == 20){
            catnum = 1;
            count = 0;
        }
        updateCatPoints();
        updateCollideRects();
    }


    public void addY(int y){
        if(falling){
            py+= y;
            if(py+y<0 || py+y>600) {
                dead = true;
            }
        }
        updateCatPoints();
        updateCollideRects();
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

    public Rect getCollideRect(String pos){
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
        else if(pos.equals("cat")){
            return catRect;
        }
        System.out.println("check getCollideRect");
        return null;
    }

    public Point[] getCatPoints(){
        return catPoints;
    }

    public boolean isFalling(){
        return falling;
    }

    public boolean isJumping(){ return jumping;}
    //returns true if player is dead
    public boolean isDead(){
        return dead;
    }

    public void updateCollideRects(){
        catRect = new Rect(px,py,40,61);
        if(direction){
            topRect = new Rect(px+4,py,24,1);
            bottomRect = new Rect(px+4,py+60,24,1);
        }
        else{
            topRect = new Rect(px+12,py,24,1);
            bottomRect = new Rect(px+12,py+60,24,1);
        }
        leftRect = new Rect(px,py,1,61);
        rightRect = new Rect(px+39,py,1,61);
    }

    public void updateCatPoints(){
        catPoints[0] = new Point(px,py);
        catPoints[1] = new Point(px+20,py);
        catPoints[2] = new Point(px+40,py);
        catPoints[3] = new Point(px+40,py+30);
        catPoints[4] = new Point(px+40,py+61);
        catPoints[5] = new Point(px+20,py+61);
        catPoints[6] = new Point(px,py+61);
        catPoints[7] = new Point(px,py+30);
    }

    public void jump(){
        jumpCounter = 5;
        jumping = true;
        falling = true;
        if(normalGravity){
            jumpV = -6;
        }
        else{
            jumpV = 6;
        }
    }

    public void checkJumpV(){
        if(jumpCounter<1){
            if(normalGravity && jumpV<0){
                jumpV++;
            }
            if(!normalGravity && jumpV>0){
                jumpV--;
            }
        }
        if(jumpCounter>0){
            jumpCounter--;
        }
        addY(jumpV);
        updateCollideRects();
        updateCatPoints();
    }

    public void setOnPlat(Platform platform,boolean on) {
        if (on) {
            if (normalGravity) {
                py = platform.getY() - 61;
            } else {
                py = platform.getY() + platform.getHeight();
            }
            falling = false;
        }
        else {
            if (normalGravity) {
                py -= 4;
            }
            else {
                py += 4;
            }
            falling = true;
        }
        updateCollideRects();
        updateCatPoints();
    }

    public void setSideRect(Platform platform, String pos){
        if(pos.equals("left")){
            px = platform.getX()-41;
        }
        else if(pos.equals("right")){
            px = platform.getX()+platform.getWidth()+1;
        }
        else{
            System.out.println("check setSideRect");
        }
        updateCollideRects();
        updateCatPoints();
    }
    /*
    public void speedUp(){

    }*/


    public void draw(Graphics g, GamePanel gamePanel){
        if(normalGravity){
            if (direction) {
                if(falling){
                    g.drawImage(normCatsR[0], px, py, gamePanel);
                }
                else if(standing){
                    g.drawImage(normCatsR[2],px,py,gamePanel);
                }
                else {
                    g.drawImage(normCatsR[catnum], px, py, gamePanel);
                }
            }
            else{
                if(falling){
                    g.drawImage(normCatsL[0], px, py, gamePanel);
                }
                else if(standing){
                    g.drawImage(normCatsL[2],px,py,gamePanel);
                }
                else{
                    g.drawImage(normCatsL[catnum], px, py, gamePanel);
                }


            }
        }
        else{
            if (direction) {
                if(falling){
                    g.drawImage(upsideCatsR[0],px,py,gamePanel);
                }
                else if(standing){
                    g.drawImage(upsideCatsR[2],px,py,gamePanel);
                }
                else{
                    g.drawImage(upsideCatsR[catnum], px, py, gamePanel);
                }


            }
            else{
                if(falling){
                    g.drawImage(upsideCatsL[0],px,py,gamePanel);
                }
                else if(standing){
                    g.drawImage(upsideCatsL[2],px,py,gamePanel);
                }
                else{
                    g.drawImage(upsideCatsL[catnum], px, py, gamePanel);
                }

            }
        }
        g.setColor(Color.GREEN);
        g.drawRect((int)topRect.getX(),(int)topRect.getY(),(int)topRect.getWidth(),(int)topRect.getHeight());
        g.drawRect((int)bottomRect.getX(),(int)bottomRect.getY(),(int)bottomRect.getWidth(),(int)bottomRect.getHeight());
        g.drawRect((int)leftRect.getX(),(int)leftRect.getY(),(int)leftRect.getWidth(),(int)leftRect.getHeight());
        g.drawRect((int)rightRect.getX(),(int)rightRect.getY(),(int)rightRect.getWidth(),(int)rightRect.getHeight());

        g.setColor(Color.magenta);
        for(Point p:catPoints){
            g.drawRect((int)p.getX(),(int)p.getY(),1,1);
        }
    }
}