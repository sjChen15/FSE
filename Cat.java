import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Cat {
    //ints
    private int px,py;
    private int jumpV; //the jumping velocity


    //images
    //NOTE ALL CAT PICTURES ARE 40x61 PIXELS
    private Image upsideDown;

    //ints
    private int catnum = 0;
    private int count = 0;
    private int jumpCounter;
  
    private ArrayList<Image> normCatsR = new ArrayList<Image>();
    private ArrayList<Image> normCatsL = new ArrayList<Image>();


    //booleans
    private boolean falling = true; //if true, not on
    private boolean dead = false; //true if the player has died
    private boolean normalGravity = true;
    private boolean direction = true; //true if going right
    private boolean jumping = false;
    private boolean canGoRight = true;
    private boolean canGoLeft = true;

    //rects
    private Rect catRect,topRect,bottomRect,leftRect,rightRect;

    public Cat(int px, int py, Image upsideDown, ArrayList<Image> nCatsR, ArrayList<Image>nCatsL) {
        this.px = px;
        this.py = py;
        this.upsideDown = upsideDown;

        catRect = new Rect(px,py,40,61);
        topRect = new Rect(px,py,40,1);
        bottomRect = new Rect(px,py+60,40,1);
        leftRect = new Rect(px,py,1,61);
        rightRect = new Rect(px+39,py,1,61);

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

    public void setCanGoRight(boolean canGoRight) {
        this.canGoRight = canGoRight;
    }

    public void setCanGoLeft(boolean canGoLeft) {
        this.canGoLeft = canGoLeft;
    }

    public void setJumping(boolean j){ jumping = j;}
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
        topRect = new Rect(px,py,40,1);
        bottomRect = new Rect(px,py+60,40,1);
        leftRect = new Rect(px,py,1,61);
        rightRect = new Rect(px+39,py,1,61);
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
    }

    public void draw(Graphics g, GamePanel gamePanel){
        if(normalGravity){
            if (direction) {
                g.drawImage(normCatsR.get(catnum), px, py, gamePanel);
            }
            else{
                g.drawImage(normCatsL.get(catnum), px, py, gamePanel);
            }
        }
        else{
            g.drawImage(upsideDown,px,py,gamePanel);
            /*if (direction) {
                g.drawImage(normCatsR.get(catnum), px, py, gamePanel);
            }
            else{
                g.drawImage(normCatsL.get(catnum), px, py, gamePanel);
            }*/
        }
        g.setColor(Color.GREEN);
        g.drawRect((int)topRect.getX(),(int)topRect.getY(),(int)topRect.getWidth(),(int)topRect.getHeight());
        g.drawRect((int)bottomRect.getX(),(int)bottomRect.getY(),(int)bottomRect.getWidth(),(int)bottomRect.getHeight());
        g.drawRect((int)leftRect.getX(),(int)leftRect.getY(),(int)leftRect.getWidth(),(int)leftRect.getHeight());
        g.drawRect((int)rightRect.getX(),(int)rightRect.getY(),(int)rightRect.getWidth(),(int)rightRect.getHeight());

        g.setColor(Color.magenta);
        g.drawRect((int)catRect.getX(),(int)catRect.getY(),(int)catRect.getWidth(),(int)catRect.getHeight());
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
    }
}