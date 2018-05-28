import java.awt.*;

public class Saw {
    private int x,y,minX,maxX;
    private int radius = 40;
    private int direction = 1; //positive 1 if it goes right first
    private int sawV = 3; //velocity of saw

    public Saw(int x, int y, int minX, int maxX, String d) {
        this.x = x;
        this.y = y;
        this.minX = minX;
        this.maxX = maxX;
        if(d.equals("left")){
            direction*=-1;
        }
    }

    public void move(){
        if(direction>0 && x+sawV<maxX){
            x+= sawV;
        }
        else if(direction<0 && x-sawV>minX){
            x-=sawV;
        }
        if(x==maxX || x==minX){
            direction*=-1;
        }
    }

    public void draw(Graphics g, GamePanel gamePanel){
        g.setColor(Color.BLUE);
        g.fillOval(x,y,radius*2,radius*2);
    }
}
