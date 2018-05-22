import java.awt.*;

public class Platform {
    private int px,py; //position of platform (top left corner)
    private int h,w; //width and height of platform
    private Image platPic;

    public Platform(int px, int py, int h, int w, Image platPic) {
        this.px = px;
        this.py = py;
        this.h = h;
        this.w = w;
        this.platPic = platPic;
    }

    public boolean ontop(Cat cat){
        if(cat.getY() == py){ //1 is the gravity
            if(cat.getX()>px && cat.getX()<px+w){
                return true;
            }

        }
        return false;
    }


    public void draw(Graphics g, GamePanel gamePanel) {
        g.drawImage(platPic,px,py,gamePanel);
    }
}
