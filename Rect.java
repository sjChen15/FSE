
class Rect{
    private double x,y,height,width;

    public Rect(double x1,double y1,double w,double h){
        x = x1;
        y  = y1;
        height = h;
        width = w;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getHeight(){
        return height;
    }
    public double getWidth(){
        return width;
    }

    public boolean overlaps(Rect r){
        return x < r.getX() + r.getWidth() && x + width > r.getX() && y < r.getY() + r.getHeight() && y + height > r.getY();
    }

}