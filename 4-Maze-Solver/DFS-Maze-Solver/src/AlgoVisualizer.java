import java.awt.*;

public class AlgoVisualizer {

    private static final int DELAY = 5;
    private static final int blockSide = 8;

    private final MazeData data;
    private AlgoFrame frame;
    private static final int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(String mazeFile){

        // initialize data
        data = new MazeData(mazeFile);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // initialize visualization
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);

            new Thread(this::run).start();
        });
    }

    public void run(){

        setData(-1, -1, false);

        if(!go(data.getEntranceX(), data.getEntranceY()))
            System.out.println("The maze has NO solution!");

        setData(-1, -1, false);
    }

    // go from (x,y), return true if solved
    private boolean go(int x, int y){

        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index in go function!");

        data.visited[x][y] = true;
        setData(x, y, true);

        if(x == data.getExitX() && y == data.getExitY())
            return true;

        for(int i = 0 ; i < 4 ; i ++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if(data.inArea(newX, newY) &&
                    data.getMaze(newX,newY) == MazeData.ROAD &&
                    !data.visited[newX][newY])
                if(go(newX, newY))
                    return true;
        }

        // back track
        setData(x, y, false);

        return false;
    }

    private void setData(int x, int y, boolean isPath){
        if(data.inArea(x, y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {
        String mazeFile = "maze_101_101.txt";
        new AlgoVisualizer(mazeFile);
    }
}