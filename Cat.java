package com.company;

//Cat.java
//the player object class

import java.awt.*;

public class Cat {
    //ints
    private int px,py; //position of the cat
    private int ox,oy;  //initial position of the cat
    private int jumpV; //the jumping velocity

    //ints
    private int catnum = 0; //counter for walking animation
    private int count = 0;  //slows the walking speed
    private int jumpCounter;    //counts how long the player has been given upward velocity
    private int catV = 4;   //the walking speed
    private int speedCounter = 0;   //counts how long the cat has been in speedy mode

    //images
    //NOTE ALL CAT PICTURES ARE 40x61 PIXELS
    private Image[] normCatsR,normCatsL, upsideCatsR, upsideCatsL; //lists of images going in different directions and gravities
    private Point[] catPoints= new Point[8]; //holds 8 points around the cat, used to check if cat has ran into a saw

    //booleans
    private boolean falling = true; //if true, not on
    private boolean dead = false; //true if the player has died
    private boolean normalGravity = true; //true is the gravity is normal
    private boolean direction = true; //true if going right
    private boolean standing = true; //true is the cat is not moving
    private boolean jumping = false; //true is the cat is jumping
    private boolean canGoRight = true; //true if the cat can go right
    private boolean canGoLeft = true; //true if the cat can go left
    private boolean canGoUp = true; //true if the cat can go up/jump

    //collide rects of the cat
    private Rect catRect,topRect,bottomRect,leftRect,rightRect;

    //constructor, takes the cats x and y position and lists of animations of the cat going in left or right in normal and anti gravity
    public Cat(int px, int py,Image[] nCatsR, Image[] nCatsL, Image[] uCatsR, Image[] uCatsL) {
        this.px = px;
        this.py = py;
        ox = px;
        oy = py;

        normCatsR = nCatsR;
        normCatsL = nCatsL;

        upsideCatsR = uCatsR;
        upsideCatsL = uCatsL;

        //creates the collide points and collide rects of the cat
        updateCatPoints();
        updateCollideRects();
    }

    //set falling as the boolean f
    public void setFalling(boolean f){
        falling = f;
        if(!falling){
            jumpV = 0;  //if the cat is not jumping, they can't have upwards velocity
        }
    }

    //set if the cat is dead or not
    public void setDead(boolean d){
        dead = d;
    }

    //set the normalGravity as true or false
    public void setNormalGravity(boolean g){
        normalGravity = g;
    }

    //set if the cat can go right or not
    public void setCanGoRight(boolean canGoRight) {
        this.canGoRight = canGoRight;
    }

    //set if the cat can go left or not
    public void setCanGoLeft(boolean canGoLeft) {
        this.canGoLeft = canGoLeft;
    }

    //set if the cat can go up or not
    public void setCanGoUp (boolean canGoUp) {this.canGoUp = canGoUp; }

    //set if the cat can jump or not
    public void setJumping(boolean j){ jumping = j;}

    //set if the cat is standing or not
    public void setStanding(boolean s){ standing = s;}

    //move the cat horizontally
    public void addX(int x){
        if( x < 0 ){
            direction = false; //set if they are going left or not
            if(canGoLeft && px>0){ //do not let the cat go outside the screen
                px+=x;
            }
        }
        else{
            direction = true;
            //TODO: change this if changing screen size
            if(canGoRight && px+41+x<1330){
                px+=x;
            }
        }
        //change catnum according to count to animate the walking
        count += 1;
        if(count == 10){
            catnum = 0;
        }
        else if (count == 20){
            catnum = 1;
            count = 0;
        }
        //update the collide points and rects
        updateCatPoints();
        updateCollideRects();
    }

    //move the cat vertically
    public void addY(int y){
        if(falling){ //only move vertically if the cat is in the air
            if(y>0){
                py+= y;
            }
            if(y<0 && canGoUp){ //if the cat is going up, make sure it cacn
                py+= y;
            }
            if(py+y<0 || py+y>600) { //if the cat falls out of the screen, it dies
                dead = true;
            }
        }
        //update the collide points and rects
        updateCatPoints();
        updateCollideRects();
    }

    //return normalGravity
    public boolean getNormalGravity(){
        return normalGravity;
    }

    //return the collide rect that is indicated by the String pos
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
        return null;
    }

    //return a list of collide points
    public Point[] getCatPoints(){
        return catPoints;
    }

    //return the cat's speed
    public int getCatV(){return catV;}

    //return falling
    public boolean isFalling(){
        return falling;
    }

    //return jumping
    public boolean isJumping(){ return jumping;}

    //returns true if player is dead
    public boolean isDead(){
        return dead;
    }

    //updates the collide rects according to the cat's position
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

    //updates the collide points according to the cat's position
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

    //makes the cat jump
    public void jump(){
        jumpCounter = 8; //give the player 5 loops to add jump velocity
        jumping = true;
        falling = true;
        //set the jumpV according to the gravity
        if(normalGravity){
            jumpV = -4;
        }
        else{
            jumpV = 4;

        }
    }

    //if the cat is jumping, decrease the jump velocity
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

    //check the speedCounter
    public void checkSpeedUp(){
        if(speedCounter>0){
            speedCounter --;
        }
        if(speedCounter==0){
            catV = 4;
        }
    }

    //takes a platform and sets the cat off or on that platform
    public void setOnPlat(Platform platform,boolean on) {
        if (on) { //means cat is falling onto the platform so it needs to stop at the y coordinate of the platform
            if (normalGravity) {
                py = platform.getY() - 61;
            } else {
                py = platform.getY() + platform.getHeight();
            }
            falling = false;
        }
        else { //means the cat is on a platform already, so it needs to be taken off the platform
            if (normalGravity) {
                py -= 4;
            }
            else {
                py += 4;
            }
            falling = true;
        }
        //update the collide rects and points
        updateCollideRects();
        updateCatPoints();
    }

    //if the cat is hitting the bottom of a platform, the cat can't go into the platform
    public void setUnderPlat(Platform plat){
        if(normalGravity){
            py = plat.getY()+plat.getHeight()+1;
            System.out.println("getY "+plat.getY());
        }
        else{
            py = plat.getY()-1;
        }
    }

    //set the cat on a side of the platform specified by the String pos
    public void setSideRect(Platform platform, String pos){
        if(pos.equals("left")){
            if(platform.getX()-41>0){
                px = platform.getX()-41;
            }
            else{
                px=0;
            }

        }
        else if(pos.equals("right")){
            if(platform.getX()+platform.getWidth()+1<760){
                px = platform.getX()+platform.getWidth()+1;
            }
            else{
                px=759;
            }
        }
        else{
            System.out.println("check setSideRect");
        }
        //update the collide rects and points
        updateCollideRects();
        updateCatPoints();
    }

    //increase the cat velocity if the power has been picked up
    public void speedUp(){
        speedCounter = 100;
        catV = 10;
    }

    //reset the variables to intial values
    public void reset(){
        dead = false;
        px = ox;
        py = oy;
        catnum = 0;
        count = 0;
        catV = 4;
        speedCounter = 0;
        falling = true;
        normalGravity = true;
        direction = true; //true if going right
        standing = true;
        jumping = false;
        canGoRight = true;
        canGoLeft = true;
        canGoUp = true;

    }

    //draw the cat
    public void draw(Graphics g, GamePanel gamePanel){
        if(normalGravity){ //pictures are based on the gravity
            if (direction) { //the direction the cat is walking
                if(falling){ //if the cat is falling, there is no animation
                    g.drawImage(normCatsR[0], px, py, gamePanel);
                }
                else if(standing){ //if the cat is standing still, there is no animation
                    g.drawImage(normCatsR[2],px,py,gamePanel);
                }
                else { //else animate the walking in this position
                    g.drawImage(normCatsR[catnum], px, py, gamePanel);
                }
            }
            //and repeat these specification with the different direction and gravity
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

        //TODO: delete these after
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