import java.awt.*;
import javax.swing.*;

public class AlgoFrame extends JFrame{

    private final int canvasWidth, canvasHeight;

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
    private CircleData data;
    public void render(CircleData data){
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
            drawCircle(g2d, data.getStartX(), data.getStartY(), data.getStartR(), 0);
        }

        private void drawCircle(Graphics2D g, int x, int y, int r, int depth){

            if( depth == data.getDepth() || r < 1 )
                return;

            if(depth%2 == 0)
                AlgoVisHelper.setColor(g, AlgoVisHelper.Red);
            else
                AlgoVisHelper.setColor(g, AlgoVisHelper.LightBlue);

            AlgoVisHelper.fillCircle(g, x, y, r);
            drawCircle(g, x, y, r-data.getStep(), depth+1);
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
