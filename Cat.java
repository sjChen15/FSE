package com.company;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Cat {
    //ints
    private int px,py;
    private int jumpV; //the jumping velocity
    private int catnum = 0;
    private int count = 0;

    //images
    private Image normal;
    private Image upsideDown;
    private ArrayList<Image> normCatsR = new ArrayList<Image>();
    private ArrayList<Image> normCatsL = new ArrayList<Image>();

    //booleans
    private boolean falling = true; //if true, not on
    private boolean dead = false; //true if the player has died
    private boolean normalGravity = true;
    private boolean direction = true;

    public Cat(int px, int py, Image normal, Image upsideDown, ArrayList<Image> nCatsR, ArrayList<Image>nCatsL) {
        this.px = px;
        this.py = py;
        this.normal = normal;
        this.upsideDown = upsideDown;
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
        py += y;
        if(py+y<0 || py+y>600) {
            dead = true;
        }
    }

    public int getX(){
        return px;
    }

    public int getY(){
        return py;
    }

    public boolean isFalling(){
        return falling;
    }

    //returns true if player is dead
    public boolean isDead(){
        return dead;
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
            if (direction) {
                g.drawImage(normCatsR.get(catnum), px - 10, py - 60, gamePanel);
            }
            else{
                g.drawImage(normCatsL.get(catnum), px - 10, py - 60, gamePanel);

            }
        }
        else{
            g.drawImage(upsideDown,px-10,py,gamePanel);
        }

    }


    public void setOntop(Platform platform,boolean on) {
        if(on) {
            py = platform.getY();
            falling = false;
        }
        else{
            py--;
            falling=true;
        }
    }
}
