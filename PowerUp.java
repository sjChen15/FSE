//PowerUp.java
//object class
//if player touches the power up, they speed up

import java.awt.*;

public class PowerUp {

    //TODO: get an actual picture and delete the current grey square and fix the coordinates and shit
    private Image powerup; //the image of the PowerUp

    private int px,py; //x and y coordinates of the power up
    private Rect spUpRect; //the Rect around the power up
    private boolean available = true;   //true if the player has not picked it up yet

    private Cat cat; // the player

    //constructor, takes the position of the power up (px,py) and the player
    public PowerUp(int px, int py, Cat cat) {
        //this.powerup = powerup;
        this.px = px;
        this.py = py;
        spUpRect = new Rect(px,py,10,10);
        this.cat = cat;
    }

    //checks if the cat has collided with the power up
    public void checkPowerUp(){
        if(cat.getCollideRect("cat").overlaps(spUpRect) && available){
            cat.speedUp();
            available = false;
        }
    }

    //resets variables to initial values
    public void reset(){
        available = true;
    }

    //draws the PowerUp
    public void draw(Graphics g, GamePanel gamePanel){
        //only draws power up if it is still available
        if(available){
            g.setColor(Color.GRAY);
            g.fillRect(px,py,10,10);
        }
    }
}
