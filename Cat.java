import java.awt.*;
import java.awt.image.ImageObserver;

public class Cat {
    //ints
    private int px,py;
    private int jumpV; //the jumping velocity

    //images
    private Image normal;
    private Image upsideDown;

    //booleans
    private boolean falling = true; //if true, not on
    private boolean dead = false; //true if the player has died
    private boolean normalGravity = true;

    public Cat(int px, int py, Image normal, Image upsideDown) {
        this.px = px;
        this.py = py;
        this.normal = normal;
        this.upsideDown = upsideDown;
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
        if(px+x>0 && px+x<600){
            px+=x;

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
            g.drawImage(normal,px-10,py-60,gamePanel);
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
