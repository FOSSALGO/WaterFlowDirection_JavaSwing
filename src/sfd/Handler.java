package sfd;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JFrame;

public class Handler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private JFrame jFrame;
    private Canvas canvas;

    //------------
    private int lastOffsetX;
    private int lastOffsetY;

    //SCALE
    final private double MIN_SCALE = 0.1;
    final private double MAX_SCALE = 20;

    public Handler(JFrame jFrame, Canvas canvas) {
        this.jFrame = jFrame;
        this.canvas = canvas;
        this.canvas.addMouseListener(this);
        this.canvas.addMouseMotionListener(this);
        this.canvas.addMouseWheelListener(this);
        this.canvas.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double x1 = e.getX() - canvas.getTranslateX();
        double y1 = e.getY() - canvas.getTranslateY();

        double scale = canvas.getScale();
        double cellSizeScale = canvas.getCellSize() * scale;
        double x = Math.floor(x1 / cellSizeScale);
        double y = Math.floor(y1 / cellSizeScale);

        int i = (int) y;
        int j = (int) x;
        
        //insert titik pusat
        Water water = canvas.getWater();
        if(water!=null){
            double[][]data = water.getData();
            if(data!=null && i>=0 &&i<data.length && j>=0&&j<data[0].length){
                Titik titik = new Titik(i,j);
                water.insertTitikPusat(titik);
            }
        }
        canvas.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // capture titik start x dan y
        lastOffsetX = e.getX();
        lastOffsetY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // defenisikan posisi x dan y yang baru
        // hitung translasi x dan y
        int newX = e.getX() - lastOffsetX;
        int newY = e.getY() - lastOffsetY;

        // increment last offset oleh even drag mouse
        lastOffsetX += newX;
        lastOffsetY += newY;

        // update posisi canvas
        canvas.setTranslateX(canvas.getTranslateX() + newX);
        canvas.setTranslateY(canvas.getTranslateY() + newY);

        // schedule a repaint.
        canvas.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
         if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            if (canvas.getScale() >= MIN_SCALE && canvas.getScale() <= MAX_SCALE) {
                double oldScale = canvas.getScale();

                double newScale = (0.1 * e.getWheelRotation());
                newScale = canvas.getScale() + newScale;
                newScale = Math.max(0.00001, newScale);

                //cek max dan min scale
                if (newScale < MIN_SCALE) {
                    newScale = MIN_SCALE;
                } else if (newScale > MAX_SCALE) {
                    newScale = MAX_SCALE;
                }

                double x1 = e.getX() - canvas.getTranslateX();
                double y1 = e.getY() - canvas.getTranslateY();

                double x2 = (x1 * newScale) / oldScale;
                double y2 = (y1 * newScale) / oldScale;

                double newTranslateX = canvas.getTranslateX() + x1 - x2;
                double newTranslateY = canvas.getTranslateY() + y1 - y2;

                //set nilai skala dan translasi yang baru
                canvas.setTranslateX(newTranslateX);
                canvas.setTranslateY(newTranslateY);
                canvas.setScale(newScale);
                canvas.repaint();
            }
        }
    }

}
