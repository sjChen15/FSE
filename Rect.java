//Rect.java
//Rect object class
//checks does the same things as Rectangle
//except has an extra method called overlaps

class Rect{
    private double x,y,height,width; //the position and height and width

    //constructor, takes the x and y position and width and height
    public Rect(double x1,double y1,double w,double h){
        x = x1;
        y  = y1;
        height = h;
        width = w;
    }

    //returns x position
    public double getX(){
        return x;
    }

    //returns y position
    public double getY(){
        return y;
    }

    //returns height
    public double getHeight(){
        return height;
    }

    //returns width
    public double getWidth(){
        return width;
    }

    //checks if two rects are overlaping
    public boolean overlaps(Rect r){
        return x < r.getX() + r.getWidth() && x + width > r.getX() && y < r.getY() + r.getHeight() && y + height > r.getY();
    }

}