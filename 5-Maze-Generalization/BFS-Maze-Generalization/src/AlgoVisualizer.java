import java.awt.*;
import java.util.LinkedList;

public class AlgoVisualizer {

    private static final int DELAY = 5;
    private static final int blockSide = 8;

    private final MazeData data;
    private AlgoFrame frame;
    private static final int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(int N, int M){

        // initialize data
        data = new MazeData(N, M);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // initialize visualization
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Random Maze Generation Visualization", sceneWidth, sceneHeight);

            new Thread(this::run).start();
        });
    }

    private void run(){

        setData(-1, -1);

        LinkedList<Position> queue = new LinkedList<Position>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY()+1);
        queue.addLast(first);
        data.visited[first.x()][first.y()] = true;

        while(queue.size() != 0){
            Position curPos = queue.pop();

            for(int i = 0 ; i < 4  ; i ++){
                int newX = curPos.x() + d[i][0]*2;
                int newY = curPos.y() + d[i][1]*2;

                if(data.inArea(newX, newY)
                        && !data.visited[newX][newY]
                        && data.maze[newX][newY] == MazeData.ROAD){
                    queue.addLast(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    setData(curPos.x() + d[i][0], curPos.y() + d[i][1]);
                }
            }
        }
        setData(-1, -1);
    }

    private void setData(int x, int y){
        if(data.inArea(x, y))
            data.maze[x][y] = MazeData.ROAD;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {
        int N = 101;
        int M = 101;
        new AlgoVisualizer(N, M);
    }
}