import java.awt.*;

public class AlgoVisualizer {

    private static final int DELAY = 20;

    private final TwoWaysQuickSortData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, TwoWaysQuickSortData.Type dataType){

        // initialize data
        data = new TwoWaysQuickSortData(N, sceneHeight, dataType);

        // initialize visualization
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Two Ways Quick Sort Visualization", sceneWidth, sceneHeight);

            new Thread(this::run).start();
        });
    }

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){
        this(sceneWidth, sceneHeight, N, TwoWaysQuickSortData.Type.Default);
    }

    public void run(){

        setData(-1, -1, -1, -1, -1, -1);

        quickSort2Ways(0, data.N()-1);

        setData(-1, -1, -1, -1, -1, -1);
    }

    private void quickSort2Ways(int l, int r){

        if( l > r )
            return;

        if( l == r ) {
            setData(l, r, l, -1, -1, -1);
            return;
        }

        setData(l, r, -1, -1, -1, -1);

        int p = partition(l, r);
        quickSort2Ways(l, p-1 );
        quickSort2Ways(p+1, r);
    }

    private int partition(int l, int r){

        int p = (int)(Math.random()*(r-l+1)) + l;
        setData(l, r, -1, p, -1, -1);

        data.swap(l, p);
        int v = data.get(l);
        setData(l, r, -1, l, -1, -1);

        // arr[l+1...i) <= v; arr(j...r] >= v
        int i = l+1, j = r;
        setData(l, r, -1, l, i, j);
        while( true ){
            while( i <= r && data.get(i) < v ){
                i ++;
                setData(l, r, -1, l, i, j);
            }

            while( j >= l+1 && data.get(j) > v ){
                j --;
                setData(l, r, -1, l, i, j);
            }

            if( i > j )
                break;

            data.swap( i, j );
            i ++;
            j --;
            setData(l, r, -1, l, i, j);
        }

        data.swap(l, j);
        setData(l, r, j, -1, -1, -1);

        return j;
    }

    private void setData(int l, int r, int fixedPivot, int curPivot, int curL, int curR){
        data.l = l;
        data.r = r;
        if(fixedPivot != -1)
            data.fixedPivots[fixedPivot] = true;
        data.curPivot = curPivot;
        data.curL = curL;
        data.curR = curR;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 100;

        new AlgoVisualizer(sceneWidth, sceneHeight, N, TwoWaysQuickSortData.Type.Default);
        // new AlgoVisualizer(sceneWidth, sceneHeight, N, TwoWaysQuickSortData.Type.NearlyOrdered);
        // new AlgoVisualizer(sceneWidth, sceneHeight, N, TwoWaysQuickSortData.Type.Identical);

    }
}