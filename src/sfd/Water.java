package sfd;

import java.awt.Color;
import java.util.ArrayList;

public class Water {

    //INPUT---------------------------------------------------------------------
    private double[][] data = null;
    private int rows = 0;
    private int cols = 0;
    private ArrayList<Titik> listTitikPusat = new ArrayList<>();
    
    //coloring map
    private double MIN = 0;
    private double MAX = 0;
    private Color c1 = Color.decode("#c0392b");
    private Color c2 = Color.decode("#e74c3c");
    private Color c3 = Color.decode("#d35400");
    private Color c4 = Color.decode("#e67e22");
    private Color c5= Color.decode("#f39c12");
    private Color c6 = Color.decode("#f1c40f");
    private Color c7 = Color.decode("#27ae60");
    private Color c8= Color.decode("#2ecc71");
    private Color c9 = Color.decode("#ecf0f1");
    
    private Color[] colors = {c9,c8,c7,c6,c5,c4,c3,c2,c1};
    private double[] nilaiBatas = new double[colors.length];

    //OUTPUT--------------------------------------------------------------------
    private int[][] result = null;
    private ArrayList<Titik[]> graph = null;

    public Water(double[][] data, ArrayList<Titik> listTitikPusat, int[][] result, ArrayList<Titik[]> graph) {
        this.setData(data);
        this.listTitikPusat = listTitikPusat;
        this.result = result;
        this.graph = graph;
    }

    public double[][] getData() {
        return data;
    }

    public void setData(double[][] data) {
        if (data != null) {
            this.data = data;
            this.rows = data.length;
            this.cols = data[0].length;
            ///hitung MIN MAX
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            for(int i=0;i<data.length;i++){
                for(int j=0;j<data[i].length;j++){
                    double value = data[i][j];
                    if(value>max){
                        max = value;
                    }
                    if(value<min){
                        min=value;
                    }
                }
            }
            this.MAX = max;
            this.MIN = min;
            double satuan = (MAX-MIN)/nilaiBatas.length;
            for(int i=0;i<nilaiBatas.length;i++){
                nilaiBatas[i]=MIN+(i+1)*satuan;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public ArrayList<Titik> getListTitikPusat() {
        return listTitikPusat;
    }

    public void setListTitikPusat(ArrayList<Titik> listTitikPusat) {
        this.listTitikPusat = listTitikPusat;
    }

    public int[][] getResult() {
        return result;
    }

    public void setResult(int[][] result) {
        this.result = result;
    }

    public ArrayList<Titik[]> getGraph() {
        return graph;
    }

    public void setGraph(ArrayList<Titik[]> graph) {
        this.graph = graph;
    }

    public Color[] getColors() {
        return colors;
    }

    public double[] getNilaiBatas() {
        return nilaiBatas;
    }
    
    public void resetListTitikPusat() {
        listTitikPusat = new ArrayList<>();
    }

    public void insertTitikPusat(Titik titik) {
        if (data != null && !listTitikPusat.contains(titik) && titik.getX() >= 0 && titik.getX() < rows && titik.getY() >= 0 && titik.getY() < cols) {
            listTitikPusat.add(titik);
        }
    }

}
