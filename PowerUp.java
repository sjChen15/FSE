//PowerUp.java
//object class
//if player touches the power up, they speed up

import javax.swing.*;
import java.awt.*;

public class PowerUp {

    private int px,py; //x and y coordinates of the power up
    private Rect spUpRect; //the Rect around the power up
    private boolean available = true;   //true if the player has not picked it up yet

    private Cat cat; // the player

    //constructor, takes the position of the power up (px,py) and the player
    public PowerUp(int px, int py, Cat cat) {
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
            g.drawImage(new ImageIcon(getClass().getResource("star.png")).getImage(),px,py,gamePanel);
        }
    }
}
