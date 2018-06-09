//Laser.java
//kills the player if they walk into the laser
//Laser also includes the switch that turns off the laser

import java.awt.*;

public class Laser {
    private int tx,ty; //position of the top laser node
    private int bx,by; //position of the bottom laser node
    private int sx,sy; //position of the switch
    private boolean on = true; //true if switch has not been touched, if so, laser is on as well

    //TODO: get actual good pictures, fix collide rects if needed, change all the stuff in draw
    private Image s;//switch image
    private Image topLaser;//the things that the laser shoots out of
    private Image bottomLaser;

    private Rect laserRect; //the Rect of the Laser
    private Rect switchRect; //the Rect of the switch that turns the laser off

    private Color colour;

    private Cat cat; //the player

    //constructor, takes the position of the top of the laser, the bottom of the laser, the position of the switch AND THE COLOR OF THE LASER and the player
    public Laser(int tx, int ty, int bx, int by, int sx, int sy,Color color,Cat cat) {
        this.tx = tx;
        this.ty = ty;
        this.bx = bx;
        this.by = by;
        this.sx = sx;
        this.sy = sy;
        colour = color;
        //make the laser Rect and switch Rec using the constructor info
        laserRect = new Rect(tx-5,ty,10,by-ty);
        switchRect = new Rect(sx-20,sy-50,40,50);
        this.cat = cat;
    }

    //checks if the cat has collided with the switch
    public void checkSwitch(){
        if(cat.getCollideRect("cat").overlaps(switchRect) && on){
            on = false; //if so, the laser is off
        }
    }

    //checks if the player has touched the laser
    public void checkCat(){
        if(cat.getCollideRect("cat").overlaps(laserRect) && on){
            cat.setDead(true); //if so the player dies
        }
    }

    //resets the variables to initial values
    public void reset(){
        on = true;
    }

    //draws the laser and the switch
    public void draw(Graphics g, GamePanel gamePanel){
        g.setColor(colour);
        //only draws laser if it is on
        if(on){
            g.fillRect(tx-5,ty,10,by-ty);
        }

        g.fillRect(sx-20,sy-50,40,50);

        g.setColor(Color.darkGray);
        g.fillRect(tx-6,ty,12,5);
        g.fillRect(bx-6,by-5,12,5);

    }
}
