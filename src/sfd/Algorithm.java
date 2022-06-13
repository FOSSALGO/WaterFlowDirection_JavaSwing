package sfd;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Algorithm {

    private Water water = null;

    public void setData(File filedata) {
        double[][] data = null;
        ArrayList<Titik> listTitikPusat = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filedata);
            Scanner sc = new Scanner(fis, "UTF-8");

            // baca nCols
            int nCols = 0;
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitLine = line.split("\\s+");//menghapus spasi
                String value = splitLine[1];//membaca data pada kolom-1 di array splitLine
                nCols = Integer.parseInt(value);
            }

            //baca nRows
            int nRows = 0;
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitLine = line.split("\\s+");//menghapus spasi
                String value = splitLine[1];//membaca data pada kolom-1 di array splitLine
                nRows = Integer.parseInt(value);
            }

            //baca data
            if (nRows > 0 && nCols > 0) {
                double[][] newData = new double[nRows][];
                sc.nextLine();
                sc.nextLine();
                sc.nextLine();
                sc.nextLine();

                //membaca data baris demi baris
                for (int i = 0; i < nRows; i++) {
                    if (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        String[] splitLine = line.split("\\s+");
                        newData[i] = new double[splitLine.length];
                        for (int j = 0; j < newData[i].length; j++) {
                            String value = splitLine[j];
                            double dValue = 0;
                            try {
                                dValue = Double.parseDouble(value);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            newData[i][j] = dValue;
                        }
                    }
                }
                data = newData;
            }
            if (data != null) {
                water = new Water(data, listTitikPusat, null, null);
            }
            sc.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of setData()    

    public String getStringData() {
        String sData = "NULL";
        if (this.water != null && this.water.getData() != null) {
            double[][] data = this.water.getData();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    sb.append(data[i][j] + "\t");
                }
                sb.append("\n");
            }
            sData = sb.toString();
        }
        return sData;
    }

    public void resetListTitikPusat() {
        water.resetListTitikPusat();
    }

    public void insertTitikPusat(Titik titik) {
        water.insertTitikPusat(titik);
    }

    public void flowDirectionD8() {
        int[][] result = null;
        ArrayList<Titik[]> graph = null;
        double[][] data = water.getData();
        ArrayList<Titik> listTitikPusat = water.getListTitikPusat();
        if (data != null && listTitikPusat != null && !listTitikPusat.isEmpty()) {
            int rows = data.length;
            int cols = data[0].length;
            result = new int[rows][cols];
            //set nilai deafault untuk array result = -1
            for (int i = 0; i < result.length; i++) {
                Arrays.fill(result[i], -1);
            }

            //inisialisasi graph
            graph = new ArrayList<>();

            //membuat antrian titik untuk dievaluasi arah alirannya
            Queue<Titik> antrianTitik = new LinkedList<>();

            //masukkan semua titik pusat yang ada di list ke dalam antrian
            for (Titik titik : listTitikPusat) {
                if (titik.getX() >= 0 && titik.getX() < rows && titik.getY() >= 0 && titik.getY() < cols && !antrianTitik.contains(titik)) {
                    result[titik.getX()][titik.getY()] = 1;
                    antrianTitik.add(titik);
                }
            }

            //melakukan loop pencarian D8
            while (!antrianTitik.isEmpty()) {
                Titik center = antrianTitik.poll();//baca titik center
                int i = center.getX();
                int j = center.getY();

                //ALGORITMA D8
                int arah = 0;
                double MIN = Double.MAX_VALUE;//inisialisasi titik terndah dengan nilai MAX

                //D1
                int I = i - 1;
                int J = j;
                if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] < MIN) {
                    MIN = data[I][J];
                    arah = 1;
                }

                //D2
                I = i - 1;
                J = j + 1;
                if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] < MIN) {
                    MIN = data[I][J];
                    arah = 2;
                }

                //D3
                I = i;
                J = j + 1;
                if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] < MIN) {
                    MIN = data[I][J];
                    arah = 3;
                }

                //D4
                I = i + 1;
                J = j + 1;
                if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] < MIN) {
                    MIN = data[I][J];
                    arah = 4;
                }

                //D5
                I = i + 1;
                J = j;
                if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] < MIN) {
                    MIN = data[I][J];
                    arah = 5;
                }

                //D6
                I = i + 1;
                J = j - 1;
                if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] < MIN) {
                    MIN = data[I][J];
                    arah = 6;
                }

                //D7
                I = i;
                J = j - 1;
                if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] < MIN) {
                    MIN = data[I][J];
                    arah = 7;
                }

                //D8
                I = i - 1;
                J = j - 1;
                if (I >= 0 && I < rows && J >= 0 && J < cols && data[I][J] < MIN) {
                    MIN = data[I][J];
                    arah = 8;
                }

                //SET ARAH ALIRAN
                if (MIN >= data[i][j]) {
                    arah = 0;
                    result[i][j] = arah;
                } else {
                    result[i][j] = arah;
                    Titik origin = center;
                    Titik destination = getTitik(i, j, arah);
                    //graph.add(new Titik[]{origin,destination});
                    Titik[] od = {origin, destination};
                    graph.add(od);
                    if (result[destination.getX()][destination.getY()] == -1 && !antrianTitik.contains(destination)) {
                        result[destination.getX()][destination.getY()] = 1;//tandai result
                        antrianTitik.offer(destination);//tambahkan titik destination ke dalam antrian titik
                    }
                }
            }
        }
        //set result di water
        water.setResult(result);
        water.setGraph(graph);
    }

    private Titik getTitik(int cx, int cy, int arah) {
        int x = -1;
        int y = -1;
        if (arah == 0) {
            x = cx;
            y = cy;
        } else if (arah == 1) {
            x = cx - 1;
            y = cy;
        } else if (arah == 2) {
            x = cx - 1;
            y = cy + 1;
        } else if (arah == 3) {
            x = cx;
            y = cy + 1;
        } else if (arah == 4) {
            x = cx + 1;
            y = cy + 1;
        } else if (arah == 5) {
            x = cx + 1;
            y = cy;
        } else if (arah == 6) {
            x = cx + 1;
            y = cy - 1;
        } else if (arah == 7) {
            x = cx;
            y = cy - 1;
        } else if (arah == 8) {
            x = cx - 1;
            y = cy - 1;
        }
        Titik destination = new Titik(x, y);
        return destination;
    }

    public String getStringResult() {
        String sResult = "NULL";
        if (water != null && water.getResult() != null) {
            int[][] result = water.getResult();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    sb.append(result[i][j] + "\t");
                }
                sb.append("\n");
            }
            sResult = sb.toString();
        }
        return sResult;
    }

    public Water getWater() {
        return this.water;
    }

}//end of class
