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

        mergeSort(0, data.N()-1);

        setData(0, data.N()-1, data.N()-1);
    }

    private void mergeSort(int l, int r){

        if( l >= r )
            return;

        setData(l, r, -1);

        int mid = (l+r)/2;
        mergeSort(l, mid);
        mergeSort(mid+1, r);
        merge(l, mid, r);
    }

    private void merge(int l, int mid, int r){

        int[] aux = Arrays.copyOfRange(data.numbers, l, r+1);

        // initialization，i begin from l；j begin from mid+1
        int i = l, j = mid+1;
        for( int k = l ; k <= r; k ++ ){

            if( i > mid ){  // complete left part
                data.numbers[k] = aux[j-l]; j ++;
            }
            else if( j > r ){   // complete left part
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