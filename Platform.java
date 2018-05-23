import java.awt.*;

public class Platform {
    private int px,py; //position of platform (top left corner)
    private int h,w; //width and height of platform
    private Image platPic;
    private Rectangle platRect;

    public Platform(int px, int py, int w, int h,Image platPic) {
        this.px = px;
        this.py = py;
        this.h = h;
        this.w = w;
        this.platPic = platPic;
        platRect= new Rectangle(px,py,w,h);
    }

    public int getX(){
        return px;
    }

    public int getY(){
        return py;
    }

    public boolean ontop(Cat cat){

        if(platRect.contains(cat.getX(),cat.getY())){ //1 is the gravity
            return true;
        }
        return false;
    }


    public void draw(Graphics g, GamePanel gamePanel) {
        g.drawImage(platPic,px,py,gamePanel);
    }
}
