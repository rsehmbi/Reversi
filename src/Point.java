// Point class to hold the co-ordiantes of board
public class Point {
    // Marks the x and y location on the board
    private int x;
    private int y;

    // Default Constructor
    Point()
    {
        // Sets to -1 and -1, because this location is invalid.
        this.x = -1;
        this.y = -1;
    }

    // Parametrized constructor in java
    Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    // Getters and Setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    @Override
    public boolean equals(Object o){
        return o.hashCode()==this.hashCode();
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(x+""+y);
    }


}
