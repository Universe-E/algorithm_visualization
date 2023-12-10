import java.awt.*;
import java.util.Arrays;

public class AlgoVisualizer {

    private static final int DELAY = 40;

    private final MergeSortData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        // initialize data
        data = new MergeSortData(N, sceneHeight);

        // initialize visualization
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Merge Sort Visualization", sceneWidth, sceneHeight);

            new Thread(this::run).start();
        });
    }

    public void run(){

        setData(-1, -1, -1);

        for (int sz = 1; sz < data.N(); sz *= 2)
            for (int i = 0; i < data.N() - sz; i += sz+sz)
                // merge arr[i...i+sz-1] and arr[i+sz...i+2*sz-1]
                merge(i, i+sz-1, Math.min(i+sz+sz-1,data.N()-1));

        this.setData(0, data.N()-1, data.N()-1);
    }

    private void merge(int l, int mid, int r){

        int[] aux = Arrays.copyOfRange(data.numbers, l, r+1);

        // initialization，i begin from l；j begin from mid+1
        int i = l, j = mid+1;
        for( int k = l ; k <= r; k ++ ){

            if( i > mid ){// complete left part
                data.numbers[k] = aux[j-l]; j ++;
            }
            else if( j > r ){// complete right part
                data.numbers[k] = aux[i-l]; i ++;
            }
            else if( aux[i-l] < aux[j-l] ){
                data.numbers[k] = aux[i-l]; i ++;
            }
            else{
                data.numbers[k] = aux[j-l]; j ++;
            }
            setData(l, r, k);
        }
    }

    private void setData(int l, int r, int mergeIndex){
        data.l = l;
        data.r = r;
        data.mergeIndex = mergeIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 100;

        new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}