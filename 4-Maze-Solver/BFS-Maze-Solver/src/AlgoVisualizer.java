import java.awt.*;
import java.util.LinkedList;

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

    private void run(){

        setData(-1, -1, false);

        LinkedList<Position> queue = new LinkedList<Position>();
        Position entrance = new Position(data.getEntranceX(), data.getEntranceY());
        queue.addLast(entrance);
        data.visited[entrance.getX()][entrance.getY()] = true;

        boolean isSolved = false;

        while(queue.size() != 0){
            Position curPos = queue.pop();
            setData(curPos.getX(), curPos.getY(), true);

            if(curPos.getX() == data.getExitX() && curPos.getY() == data.getExitY()){
                isSolved = true;
                findPath(curPos);
                break;
            }

            for(int i = 0 ; i < 4  ; i ++){
                int newX = curPos.getX() + d[i][0];
                int newY = curPos.getY() + d[i][1];

                if(data.inArea(newX, newY)
                        && !data.visited[newX][newY]
                        && data.getMaze(newX, newY) == MazeData.ROAD){
                    queue.addLast(new Position(newX, newY, curPos));
                    data.visited[newX][newY] = true;
                }
            }

        }

        if(!isSolved)
            System.out.println("The maze has no Solution!");

        setData(-1, -1, false);
    }

    private void findPath(Position des){

        Position cur = des;
        while(cur != null){
            data.result[cur.getX()][cur.getY()] = true;
            cur = cur.getPrev();
        }
    }

    private void setData(int x, int y, boolean isPath){
        if(data.inArea(x, y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        String mazeFile = "maze_101_101.txt";
        System.out.println(mazeFile);
        new AlgoVisualizer(mazeFile);
    }
}