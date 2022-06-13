package sfd;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Canvas extends JPanel {

    private int cellSize = 40;
    private double translateX;
    private double translateY;
    private double scale;

    //Water
    private Water water = null;

    //Show Graph
    private boolean showGraph = false;

    //Show Direction
    private boolean showDirection = false;

    //Show Water
    private boolean showWater = true;
    
    //Show Map
    private boolean showMap = true;

    public Canvas() {
        translateX = 0;
        translateY = 0;
        scale = 1;
        setOpaque(false);
        setDoubleBuffered(true);
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public double getTranslateX() {
        return translateX;
    }

    public void setTranslateX(double translateX) {
        this.translateX = translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setWater(Water water) {
        this.water = water;
        this.repaint();
    }

    public Water getWater() {
        return this.water;
    }

    public boolean isShowGraph() {
        return showGraph;
    }

    public void setShowGraph(boolean showGraph) {
        this.showGraph = showGraph;
    }

    public boolean isShowDirection() {
        return showDirection;
    }

    public void setShowDirection(boolean showDirection) {
        this.showDirection = showDirection;
    }

    public boolean isShowWater() {
        return showWater;
    }

    public void setShowWater(boolean showWater) {
        this.showWater = showWater;
    }

    public boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
    }
    
    private double convertArahToSudutRotasi(int arah) {
        double sudutRotasi = -90;
        if (arah == 1) {
            sudutRotasi = -90;
        } else if (arah == 2) {
            sudutRotasi = -45;
        } else if (arah == 3) {
            sudutRotasi = 0;
        } else if (arah == 4) {
            sudutRotasi = 45;
        } else if (arah == 5) {
            sudutRotasi = 90;
        } else if (arah == 6) {
            sudutRotasi = 135;
        } else if (arah == 7) {
            sudutRotasi = 180;
        } else if (arah == 8) {
            sudutRotasi = -135;
        }
        return sudutRotasi;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        //----------------------------------------------------------------------
        AffineTransform tx = new AffineTransform();
        tx.translate(translateX, translateY);
        tx.scale(scale, scale);
        g2d.setTransform(tx);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //----------------------------------------------------------------------
        //draw water
        if (water != null) {
            //draw data---------------------------------------------------------
            double[][] data = water.getData();
            Color[] colors = water.getColors();
            double[] nilaiBatas = water.getNilaiBatas();
            if (showMap && data != null && colors != null && nilaiBatas != null && colors.length == nilaiBatas.length) {
                int rows = data.length;
                int cols = data[0].length;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        double value = data[i][j];
                        //pilih warna base on value
                        Color c = Color.WHITE;
                        for (int k = 0; k < nilaiBatas.length; k++) {
                            c = colors[k];
                            if (value <= nilaiBatas[k]) {
                                break;
                            }
                        }
                        g2d.setColor(c);
                        g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    }
                }
            }

            //draw result-------------------------------------------------------
            int[][] result = water.getResult();
            if (result != null && showWater == true) {
                int rows = result.length;
                int cols = result[0].length;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        int value = result[i][j];
                        if (value >= 0) {
                            g2d.setComposite(AlphaComposite.SrcOver.derive(0.5F));
                            g2d.setColor(Color.decode("#3498db"));
                            g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        }
                    }
                }
            }

            //draw titik pusat--------------------------------------------------
            ArrayList<Titik> listTitikPusat = water.getListTitikPusat();
            if (listTitikPusat != null && showWater == true) {
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.8F));
                g2d.setColor(Color.decode("#2980b9"));
                for (Titik titik : listTitikPusat) {
                    int i = titik.getX();
                    int j = titik.getY();
                    g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }

            //draw arrow--------------------------------------------------------
            if (result != null && showDirection == true) {
                int rows = result.length;
                int cols = result[0].length;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        int value = result[i][j];
                        double sudutRotasi = convertArahToSudutRotasi(value);
                        if (value > 0) {
                            //draw panah----------------------------------------
                            int ci = i * cellSize + (int) (cellSize * 0.5);
                            int cj = j * cellSize + (int) (cellSize * 0.5);

                            int arrowLength = (int) (cellSize * 0.8);
                            int half = (int) (arrowLength * 0.5);
                            int a = -half;
                            int b = half;

                            int t = (int) (arrowLength * 0.2);//tinggi anak panah atau size anak panah
                            int px = b;
                            int py = 0;
                            int qx = b - t;
                            int qy = -t;
                            int rx = b - t;
                            int ry = t;

                            int[] xPoints = {px, qx, rx};
                            int[] yPoints = {py, qy, ry};

                            //save transform------------------------------------
                            AffineTransform originalTransform = g2d.getTransform();

                            g2d.translate(cj, ci);
                            g2d.rotate(Math.toRadians(sudutRotasi), 0, 0);

                            g2d.setComposite(AlphaComposite.SrcOver.derive(1.0F));
                            g2d.setColor(Color.decode("#e67e22"));
                            g2d.setStroke(new BasicStroke(2));
                            g2d.drawLine(a, 0, b, 0);
                            g2d.fillPolygon(xPoints, yPoints, 3);

                            // restore transform----------------------------------
                            g2d.setTransform(originalTransform);

                        } else if (value == 0) {
                            int ci = i * cellSize + (int) (cellSize * 0.5);
                            int cj = j * cellSize + (int) (cellSize * 0.5);
                            g2d.setComposite(AlphaComposite.SrcOver.derive(1.0F));
                            g2d.setColor(Color.decode("#e67e22"));
                            g2d.setStroke(new BasicStroke(2));
                            int r = (int) (cellSize * 0.2);
                            int halfR = (int) (r * 0.5);
                            g2d.drawOval(cj - halfR, ci - halfR, r, r);
                        }
                    }
                }
            }

            //draw graph--------------------------------------------------------
            if (showGraph) {
                ArrayList<Titik[]> graph = water.getGraph();
                if (graph != null) {
                    for (Titik[] od : graph) {
                        Titik origin = od[0];
                        Titik destination = od[1];

                        int cx1 = origin.getX() * cellSize + (int) (0.5 * cellSize);
                        int cy1 = origin.getY() * cellSize + (int) (0.5 * cellSize);
                        int cx2 = destination.getX() * cellSize + (int) (0.5 * cellSize);
                        int cy2 = destination.getY() * cellSize + (int) (0.5 * cellSize);
                        g2d.setColor(Color.decode("#e67e22"));
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawLine(cy1, cx1, cy2, cx2);
                        int r = (int) (cellSize / 8.0);
                        g2d.fillOval(cy1 - (int) (0.5 * r), cx1 - (int) (0.5 * r), r, r);
                        g2d.fillOval(cy2 - (int) (0.5 * r), cx2 - (int) (0.5 * r), r, r);
                    }
                }
            }

        }//end of draw water        

        //----------------------------------------------------------------------
        g2d.dispose();
    }
}
