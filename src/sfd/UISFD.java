package sfd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

public class UISFD extends JFrame {

    //variabel
    private JPanel jContentPane = null;
    private JPanel jPanelNorth = null;
    private JTabbedPane jTabbedPane = null;

    //komponen di panel north
    private JLabel jLabel1 = null;
    private JTextField jTextField1 = null;
    private JButton jButton1 = null;
    private JButton jButton2 = null;
    private JToggleButton jToggleButton1 = null;
    private JToggleButton jToggleButton2 = null;
    private JToggleButton jToggleButton3 = null;
    private JToggleButton jToggleButton4 = null;

    //komponen di tab pane
    private JPanel jPanel1 = null;
    private JPanel jPanel2 = null;
    private Canvas canvas = null;
    private JScrollPane jScrollPane1 = null;
    private JScrollPane jScrollPane2 = null;
    private JTextArea jTextArea1 = null;
    private JTextArea jTextArea2 = null;

    //variabel Algoritma
    private Algorithm algorithm = new Algorithm();

    public UISFD() {
        initUI();
    }

    private void initUI() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("Water Flow Direction");
        setContentPane(getJContentPane());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //GETTER
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanelNorth(), BorderLayout.NORTH);
            jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JPanel getJPanelNorth() {
        if (jPanelNorth == null) {
            jPanelNorth = new JPanel();
            jPanelNorth.add(getJLabel1());
            jPanelNorth.add(getJTextField1());
            jPanelNorth.add(getJButton1());
            jPanelNorth.add(getJButton2());
            jPanelNorth.add(getJToggleButton1());
            jPanelNorth.add(getJToggleButton2());
            jPanelNorth.add(getJToggleButton3());
            jPanelNorth.add(getJToggleButton4());
        }
        return jPanelNorth;
    }

    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
            jTabbedPane.addTab("Data", getJPanel1());
            jTabbedPane.addTab("Flow Direction", getJPanel2());
            jTabbedPane.addTab("Visualization", getCanvas());
            jTabbedPane.setSelectedIndex(2);
        }
        return jTabbedPane;
    }

    //GETTER untuk komponen di panel north
    private JLabel getJLabel1() {
        if (jLabel1 == null) {
            jLabel1 = new JLabel("File Data");
        }
        return jLabel1;
    }

    private JTextField getJTextField1() {
        if (jTextField1 == null) {
            jTextField1 = new JTextField();
            jTextField1.setPreferredSize(new Dimension(500, 30));
        }
        return jTextField1;
    }

    private JButton getJButton1() {
        if (jButton1 == null) {
            jButton1 = new JButton("Browse");
            jButton1.setPreferredSize(new Dimension(90, 30));
            jButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    if (jfc.showOpenDialog(jContentPane) == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = jfc.getSelectedFile();
                        //System.out.println(selectedFile.getAbsolutePath());
                        jTextField1.setText(selectedFile.getAbsolutePath());
                        algorithm.setData(selectedFile);
                        canvas.setWater(algorithm.getWater());
                        canvas.repaint();
                        jTextArea1.setText(algorithm.getStringData());
                        jTextArea2.setText(algorithm.getStringResult());
                    }

                }
            });
        }
        return jButton1;
    }

    private JButton getJButton2() {
        if (jButton2 == null) {
            jButton2 = new JButton("Run");
            jButton2.setPreferredSize(new Dimension(90, 30));
            jButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    algorithm.flowDirectionD8();
                    canvas.setWater(algorithm.getWater());
                    canvas.repaint();
                    jTextArea2.setText(algorithm.getStringResult());
                }
            });
        }
        return jButton2;
    }

    private JToggleButton getJToggleButton1() {
        if (jToggleButton1 == null) {
            jToggleButton1 = new JToggleButton("View Graph");
            jToggleButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (jToggleButton1.isSelected()) {
                        jToggleButton1.setText("Hide Graph");
                        canvas.setShowGraph(true);
                        canvas.repaint();
                    } else {
                        jToggleButton1.setText("View Graph");
                        canvas.setShowGraph(false);
                        canvas.repaint();
                    }
                }
            });
        }
        return jToggleButton1;
    }

    private JToggleButton getJToggleButton2() {
        if (jToggleButton2 == null) {
            jToggleButton2 = new JToggleButton("View Direction");
            jToggleButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (jToggleButton2.isSelected()) {
                        jToggleButton2.setText("Hide Direction");
                        canvas.setShowDirection(true);
                        canvas.repaint();
                    } else {
                        jToggleButton2.setText("View Direction");
                        canvas.setShowDirection(false);
                        canvas.repaint();
                    }
                }
            });
        }
        return jToggleButton2;
    }
    
    private JToggleButton getJToggleButton3() {
        if (jToggleButton3 == null) {
            jToggleButton3 = new JToggleButton("View Water");
            jToggleButton3.setSelected(true);
            jToggleButton3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (jToggleButton3.isSelected()) {
                        jToggleButton3.setText("Hide Water");
                        canvas.setShowWater(true);
                        canvas.repaint();
                    } else {
                        jToggleButton3.setText("View Water");
                        canvas.setShowWater(false);
                        canvas.repaint();
                    }
                }
            });
        }
        return jToggleButton3;
    }
    
    private JToggleButton getJToggleButton4() {
        if (jToggleButton4 == null) {
            jToggleButton4 = new JToggleButton("View Map");
            jToggleButton4.setSelected(true);
            jToggleButton4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (jToggleButton4.isSelected()) {
                        jToggleButton4.setText("Hide Map");
                        canvas.setShowMap(true);
                        canvas.repaint();
                    } else {
                        jToggleButton4.setText("View Map");
                        canvas.setShowMap(false);
                        canvas.repaint();
                    }
                }
            });
        }
        return jToggleButton4;
    }

    //GETTER komponen di tab pane
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.setLayout(new BorderLayout());
            jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
        }
        return jPanel1;
    }

    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            jPanel2 = new JPanel();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.add(getJScrollPane2(), BorderLayout.CENTER);
        }
        return jPanel2;
    }

    private JTextArea getJTextArea1() {
        if (jTextArea1 == null) {
            jTextArea1 = new JTextArea();
        }
        return jTextArea1;
    }

    private JTextArea getJTextArea2() {
        if (jTextArea2 == null) {
            jTextArea2 = new JTextArea();
        }
        return jTextArea2;
    }

    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(getJTextArea1());
        }
        return jScrollPane1;
    }

    private JScrollPane getJScrollPane2() {
        if (jScrollPane2 == null) {
            jScrollPane2 = new JScrollPane();
            jScrollPane2.setViewportView(getJTextArea2());
        }
        return jScrollPane2;
    }

    private Canvas getCanvas() {
        if (canvas == null) {
            canvas = new Canvas();
            Handler handler = new Handler(this, canvas);
        }
        return canvas;
    }

    //--------------------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UISFD ui = new UISFD();
            }
        });
    }//end of main
}//end of class UISFD
