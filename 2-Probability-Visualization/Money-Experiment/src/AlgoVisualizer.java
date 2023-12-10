import java.awt.*;
import java.util.Arrays;

public class AlgoVisualizer {

    private static final int DELAY = 40;
    private final int[] money;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight){

        // initialize data
        money = new int[100];
        Arrays.fill(money, 100);

        // initialize visualization
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Money Problem", sceneWidth, sceneHeight);
            new Thread(this::run).start();
        });
    }

    public void run(){

        while(true){

            // improvement 2：sort or not
            Arrays.sort(money);
            frame.render(money);
            AlgoVisHelper.pause(DELAY);

            // improvement 1：frame
            for(int k = 0 ; k < 50 ; k ++){
                for(int i = 0 ; i < money.length; i ++){
                    // improvement 3：allow money to be negative
//                    if(money[i] > 0){
                        int j = (int)(Math.random() * money.length);
                        money[i] -= 1;
                        money[j] += 1;
//                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        int sceneWidth = 1000;
        int sceneHeight = 800;

        new AlgoVisualizer(sceneWidth, sceneHeight);
    }
}