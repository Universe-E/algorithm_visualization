import java.awt.*;

public class AlgoVisualizer {

    private static final int DELAY = 40;

    private final CircleData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight){

        int R = Math.min(sceneWidth, sceneHeight)/2 - 2;
        data = new CircleData(sceneWidth/2, sceneHeight/2, R, R/2, 2);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Fractal Visualizer", sceneWidth,sceneHeight);
            new Thread(this::run).start();
        });
    }

    private void run(){

        setData();

    }

    private void setData(){

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {
        int sceneWidth = 800;
        int sceneHeight = 800;
        new AlgoVisualizer(sceneWidth, sceneHeight);
    }
}