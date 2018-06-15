//Door.java
//object that brings the player to the next level

import javax.swing.*;
import java.awt.*;

public class Door {
    private int px,py;
    private Image door;
    private Cat cat;
    private Rect doorRect;
    //constructor takes the position of the door, the image of the door and the player
    public Door(int px, int py,Image door,Cat cat) {
        this.px = px;
        this.py = py;
        this.door = door;

        this.cat = cat;

        doorRect = new Rect(px,py,35,70);
    }

    //returns true if the player has reached the next level
    public boolean nextLevel(){
        if(cat.getCollideRect("cat").overlaps(doorRect)){
            return true;
        }
        return false;
    }

    //draws the door
    public void draw(Graphics g, GamePanel gamePanel){
        g.drawImage(new ImageIcon(getClass().getResource("Door.png")).getImage(),px,py,gamePanel);
    }
}
