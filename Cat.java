import java.awt.*;
import java.awt.image.ImageObserver;

public class Cat {
    private int px,py;
    private int jumpV; //the jumping velocity
    private Image player;
    private boolean falling = true; //if true, not on


    public Cat(int px, int py, Image player) {
        this.px = px;
        this.py = py;
        this.player = player;
    }

    public void setFalling(boolean f){
        falling = f;
        if(!falling){
            jumpV = 0;
        }
    }

    public void addX(int x){
        if(px+x>0 && px+x<600){
            px+=x;

        }
    }

    public void addY(int y){
        if(py+y>0 && py+y<600){
            py+=y;
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

    public void jump(){
        if(!falling){
            falling = true;
            jumpV = -5;
        }
    }

    public void checkJumpV(){
        if(falling) {
            if(jumpV>0){
                jumpV += 1;
                addY(jumpV);
            }

        }

    }

    public void draw(Graphics g, GamePanel gamePanel){
        g.drawImage(player,px-10,py-60,gamePanel);

    }


    public void setOntop(Platform platform,boolean on) {
        if(on) {
            py = platform.getY();
        }
        else{
            py--;
        }
    }
}
