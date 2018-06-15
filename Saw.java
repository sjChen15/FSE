package com.company;
//Saw.java
//Saw object class
//moves back and forth, if the player touches the saw, they die

import java.awt.*;

public class Saw {
    private int x,y,minX,maxX; //initial x and y values, maximum and mininum x value
    private int ox,oy;  //original x and y value
    private int radius = 35;    //radius of the saw
    private int direction = 1; //positive 1 if it goes right first
    private int oDirection = 1; //initial direction
    private int sawV = 3; //velocity of saw
    private int picCounter = 0; //counter for the pictures
    private Image[] pics; //arrayList of saw pics, can change to
    private Cat cat; //the player

    //constructor, takes inital x and y coordinate, the minimum and maximum x coordinate, the saw images, the string of the direction and the player
    public Saw(int x, int y, int minX, int maxX, Image[] sawPics,String d, Cat cat) {
        this.x = x;
        this.y = y;
        ox = x;
        oy = y;
        this.minX = minX;
        this.maxX = maxX;
        //TODO: make actual saw pics
        pics = sawPics;
        //switches direction to right if d is not left
        if(d.equals("left")){
            direction*=-1;
            oDirection=direction;
        }
        this.cat = cat;
    }

    //moves the saw
    public void move(){
        //lets saw move left if it is less than the max x coordinate
        if(direction>0 && x<maxX){
            x+= sawV;
        }
        //lets saw move right if it is greater than the minimum x coordinate
        else if(direction<0 && x+radius*2>minX){
            x-=sawV;
        }
        //if the saw is over the maximum x coordinate or under the minimum, change the direction
        if(x>=maxX){
            direction*=-1;
            x-=sawV;
        }
        if(x+radius*2<=minX){
            direction*=-1;
            x+=sawV;
        }
        //change the saw picture according to the counter to make it look like the saw is rotating
        if(picCounter == 0){
            picCounter =1;
        }
        else{
            picCounter = 0;
        }
    }

    //checks if the player had collided with the saw
    public void checkCat(){
        Point[] points = cat.getCatPoints();
        for(Point p:points){
            //checks if any of the 8 points on the cat are in the radius of the saw
            if(Math.hypot((x+radius)-p.getX(),(y+radius)-p.getY())<radius){
                //if so the cat dies
                System.out.println("Saw death");
                cat.setDead(true);
            }
        }
    }

    //resets the saw variables to the initial values
    public void reset(){
        x = ox;
        y = oy;
        direction = oDirection;
    }

    //draws the saw image
    public void draw(Graphics g, GamePanel gamePanel){
        g.drawImage(pics[picCounter],x,y,gamePanel);
    }
}
