import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

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
            frame.addKeyListener(new AlgoKeyListener());

            new Thread(this::run).start();
        });
    }

    private void run(){

        setRoadData(-1, -1);

        RandomQueue<Position> queue = new RandomQueue<Position>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY()+1);
        queue.add(first);
        data.visited[first.x()][first.y()] = true;
        data.openMist(first.x(), first.y());

        while(queue.size() != 0){
            Position curPos = queue.remove();

            for(int i = 0 ; i < 4  ; i ++){
                int newX = curPos.x() + d[i][0]*2;
                int newY = curPos.y() + d[i][1]*2;

                if(data.inArea(newX, newY) && !data.visited[newX][newY]){
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    data.openMist(newX, newY);
                    setRoadData(curPos.x() + d[i][0], curPos.y() + d[i][1]);
                }
            }
        }

        setRoadData(-1, -1);
    }

    private void setRoadData(int x, int y){
        if(data.inArea(x, y))
            data.maze[x][y] = MazeData.ROAD;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private boolean go(int x, int y){

        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index in go function!");

        data.visited[x][y] = true;
        setPathData(x, y, true);

        if(x == data.getExitX() && y == data.getExitY())
            return true;

        for(int i = 0 ; i < 4 ; i ++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if(data.inArea(newX, newY) &&
                    data.maze[newX][newY] == MazeData.ROAD &&
                    !data.visited[newX][newY])
                if(go(newX, newY))
                    return true;
        }

        // back track
        setPathData(x, y, false);

        return false;
    }

    private void setPathData(int x, int y, boolean isPath){
        if(data.inArea(x, y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter{

        @Override
        public void keyReleased(KeyEvent event){
            if(event.getKeyChar() == ' '){
                for(int i = 0 ; i < data.N() ; i ++)
                    Arrays.fill(data.visited[i], false);

                new Thread(() -> {
                    go(data.getEntranceX(), data.getEntranceY());
                }).start();
            }
        }
    }

    public static void main(String[] args) {
        int N = 101;
        int M = 101;
        new AlgoVisualizer(N, M);
    }
}