public class Position {

    private final int x, y;
    private final Position prev;

    public Position(int x, int y, Position prev){
        this.x = x;
        this.y = y;
        this.prev = prev;
    }

    public Position(int x, int y){
        this(x, y, null);
    }

    public int getX(){return x;}
    public int getY(){return y;}
    public Position getPrev(){return prev;}
}
