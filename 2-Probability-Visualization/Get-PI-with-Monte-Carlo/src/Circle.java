import java.awt.*;

public record Circle(int x, int y, int r) {

    public boolean contain(Point p) {
        return Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2) <= r * r;
    }
}
