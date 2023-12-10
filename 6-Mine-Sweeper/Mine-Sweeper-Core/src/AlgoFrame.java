import java.awt.*;
import javax.swing.*;

public class AlgoFrame extends JFrame{

    private final int canvasWidth,canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setVisible(true);
    }

    public AlgoFrame(String title){

        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    // data
    private MineSweeperData data;
    public void render(MineSweeperData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            // double cache
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            // anti aliasing
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            // drawing
            int w = canvasWidth/data.M();
            int h = canvasHeight/data.N();

            for(int i = 0 ; i < data.N() ; i ++)
                for(int j = 0 ; j < data.M() ; j ++){

                    if(data.open[i][j]){
                        if(data.isMine(i, j))
                            AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.mineImageURL);
                        else
                            AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.numberImageURL(data.getNumber(i, j)));
                    }
                    else{
                        if(data.flags[i][j])
                            AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.flagImageURL);
                        else
                            AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.blockImageURL);
                    }
                }

        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
