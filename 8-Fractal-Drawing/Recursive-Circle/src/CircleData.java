public class CircleData {

    private final int startX, startY, startR, depth, step;

    public  CircleData(int x, int y, int r, int d, int step){
        this.startX = x;
        this.startY = y;
        this.startR = r;
        this.depth = d;
        this.step = step;
    }

    public int getStartX(){ return startX; }
    public int getStartY(){ return startY; }
    public int getStartR(){ return startR; }
    public int getDepth(){ return depth; }
    public int getStep(){ return step; }
}
