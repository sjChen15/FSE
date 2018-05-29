import java.awt.*;
import java.util.ArrayList;

public class Saw {
    private int x,y,minX,maxX;
    private int radius = 40;
    private int direction = 1; //positive 1 if it goes right first
    private int sawV = 3; //velocity of saw
    private int picCounter = 0;
    private Rect sawRect;
    private Image[] pics; //arrayList of saw pics, can change to

    public Saw(int x, int y, int minX, int maxX, Image[] sawPics,String d) {
        this.x = x;
        this.y = y;
        this.minX = minX;
        this.maxX = maxX;
        pics = sawPics;
        if(d.equals("left")){
            direction*=-1;
        }
        //sawRect = new Rect()
    }

    public void move(){
        if(direction>0 && x<maxX){
            x+= sawV;
        }
        else if(direction<0 && x+80>minX){
            x-=sawV;
        }
        if(x>=maxX){
            direction*=-1;
            x-=sawV;
        }
        if(x+80<=minX){
            direction*=-1;
            x+=sawV;
        }
        if(picCounter == 0){
            picCounter =1;
        }
        else{
            picCounter = 0;
        }
    }

    public void checkCat(Cat cat){
        Point[] points = cat.getCatPoints();
        for(Point p:points){
            if(Math.hypot((x+40)-p.getX(),(y+40)-p.getY())<=40){
                cat.setDead(true);
            }
        }
    }

    public void draw(Graphics g, GamePanel gamePanel){
        g.setColor(Color.BLUE);
        g.drawImage(pics[picCounter],x,y,gamePanel);
        g.fillOval(x,y,5,5);
    }
}
