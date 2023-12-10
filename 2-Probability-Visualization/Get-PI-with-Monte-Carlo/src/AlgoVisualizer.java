import java.awt.*;

public class AlgoVisualizer {

    private static final int DELAY = 40;

    private final MonteCarloPiData data;
    private AlgoFrame frame;
    private final int N;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        if(sceneWidth != sceneHeight)
            throw new IllegalArgumentException("This demo must be run in a square window!");

        this.N = N;
        Circle circle = new Circle(sceneWidth/2, sceneHeight/2, sceneWidth/2);
        data = new MonteCarloPiData(circle);

        // initialize visualization
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Monte Carlo", sceneWidth, sceneHeight);

            new Thread(this::run).start();
        });
    }

    public void run(){

        for(int i = 0 ; i < N ; i ++){

            if( i % 100 == 0) {
                frame.render(data);
                AlgoVisHelper.pause(DELAY);
                System.out.println(data.estimatePi());
            }

            int x = (int)(Math.random() * frame.getCanvasWidth());
            int y = (int)(Math.random() * frame.getCanvasHeight());
            data.addPoint(new Point(x, y));
        }

    }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 20000;

        new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
