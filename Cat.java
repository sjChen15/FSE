import java.awt.*;

public class Cat {
    private int px,py;
    private Image player;
    private boolean falling = true;

    public Cat(int px, int py, Image player) {
        this.px = px;
        this.py = py;
        this.player = player;
    }

    public void setFalling(boolean f){
        falling = f;
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
    public void draw(Graphics g, GamePanel gamePanel){
        g.drawImage(player,px+20,py-60,gamePanel);
    }




}
