package FormatConverter.View;

import FormatConverter.DAL.DBConnection;
import FormatConverter.util.ResultSetToExcel;
import FormatConverter.util.ExcelToSql;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.FlatBorderPainter;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;


public class db {
    JFrame jf;
    JPanel jp;
    JLabel jl1, jl2, jl3, jl4, jl5;
    JTextField jtf1, jtf2, jtf3, jtf4, jtf5;
    JButton jb1, jb2, jb3, jb4, jb5;
    JComboBox jcb;
    private String Database, username, password, url;
    private int port;

    public db() {
        try {
            UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
            //SubstanceLookAndFeel.setSkin(new NebulaSkin());
            SubstanceLookAndFeel.setCurrentBorderPainter(new FlatBorderPainter());
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }
        jp = new JPanel();
        jp.setLayout(null);
        jl1 = new JLabel("数据库名称：");
        jl2 = new JLabel("端口：");
        jl3 = new JLabel("用户名：");
        jl4 = new JLabel("密码：");
        jl5 = new JLabel("IP地址：");

        Database = "TEST";
        port = 3306;
        username = "root";
        password = "zhanglei0501";
        url = "127.0.0.1";

        jtf1 = new JTextField(Database);
        jtf2 = new JTextField(Integer.toString(port));
        jtf3 = new JTextField(username);
        jtf4 = new JPasswordField(password);
        jtf5 = new JTextField(url);

        jb1 = new JButton("连接");
        jb2 = new JButton("退出");
        jb3 = new JButton("导出数据表");
        jb4 = new JButton("导入Excel表");
        jb5 = new JButton("刷新");


        jcb = new JComboBox();

        jl1.setBounds(10, 10, 80, 30);
        jl2.setBounds(49, 50, 80, 30);
        jl3.setBounds(36, 90, 80, 30);
        jl4.setBounds(49, 130, 80, 30);
        jl5.setBounds(40, 170, 80, 30);
        jtf1.setBounds(100, 10, 200, 30);
        jtf2.setBounds(100, 50, 200, 30);
        jtf3.setBounds(100, 90, 200, 30);
        jtf4.setBounds(100, 130, 200, 30);
        jtf5.setBounds(100, 170, 200, 30);
        jb1.setBounds(70, 210, 80, 30);
        jb2.setBounds(170, 210, 80, 30);
        jb5.setBounds(620,30,60,20);
        jb3.setBounds(380, 130, 110, 30);
        jb4.setBounds(500, 130, 110, 30);

        jcb.setBounds(370,30,240,20);
        jp.add(jl1);
        jp.add(jl2);
        jp.add(jl3);
        jp.add(jl4);
        jp.add(jl5);
        jp.add(jtf1);
        jp.add(jtf2);
        jp.add(jtf3);
        jp.add(jtf4);
        jp.add(jtf5);
        jp.add(jb1);
        jp.add(jb2);
        jp.add(jb3);
        jp.add(jb4);
        jp.add(jb5);
        jp.add(jcb);

        jb1.setActionCommand("Connect");
        jb2.setActionCommand("Exit");
        jb5.setActionCommand("Refresh");
        jb3.setActionCommand("RsToExcel");
        jb4.setActionCommand("ExcelToSql");
        action act1 = new action();
        action act2 = new action();
        action act3 = new action();
        action act4 = new action();
        action act5 = new action();
        jb1.addActionListener(act1);
        jb2.addActionListener(act2);
        jb3.addActionListener(act3);
        jb4.addActionListener(act4);
        jb5.addActionListener(act5);
        jf = new JFrame("连接服务器");
        jf.setVisible(true);
        jf.setBounds(600, 300, 360, 300);
        //jf.setLayout(null);
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


        jf.add(jp);


    }

    public static void main(String[] args) {
        db wd = new db();
    }

    class action implements ActionListener {
        boolean isConnected = false;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand() == "Exit") {
                System.exit(0);
            } else if (e.getActionCommand() == "Connect") {
                DBConnection dbc = new DBConnection(jtf5.getText(), Integer.parseInt(jtf2.getText()), jtf1.getText(), jtf3.getText(), jtf4.getText());
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    synchronized public void run() {
                        try{
                            while(jf.getWidth()<=700){
                                jf.setSize(jf.getWidth()+20,300);
                                wait(20);
                            }
                        }catch (Exception ex1){
                            ex1.printStackTrace();
                        }
                    }
                });
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            isConnected = dbc.connect();
                            SwingUtilities.invokeAndWait(thread2);
                            if(isConnected){
                                try{
                                    jtf1.setEditable(false);
                                    jtf2.setEditable(false);
                                    jtf3.setEditable(false);
                                    jtf4.setEditable(false);
                                    jtf5.setEditable(false);
                                    List<String> tableNames = dbc.getTableNames();
                                    for(int i = 0;i<tableNames.size();i++){
                                        jcb.addItem(tableNames.get(i));
                                    }
                                    //System.out.println(tableNames.get(0));
                                    System.out.println(tableNames.size());
                                }catch (Exception ex1){
                                    ex1.printStackTrace();
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println("数据库连接失败");
                            JOptionPane.showMessageDialog(jf,"数据库连接失败","提示",JOptionPane.WARNING_MESSAGE);
                        }

                    }
                });
                thread.start();
                System.out.println(isConnected);

            }
            //重新读取数据库中的表
            else if(e.getActionCommand() == "Refresh"){
                try {
                    DBConnection dbc = new DBConnection(jtf5.getText(), Integer.parseInt(jtf2.getText()), jtf1.getText(), jtf3.getText(), jtf4.getText());
                    List<String> tableNames = dbc.getTableNames();
                    for (int i = 0; i < tableNames.size(); i++) {
                        jcb.addItem(tableNames.get(i));
                    }
                }catch (Exception ex2){
                    //数据库状态发生变化 无法连接
                    new Toast("DatabaseError");
                }
            }
            //从数据库中提取表存为Excel
            else if(e.getActionCommand() == "RsToExcel"){
                if(jcb.getSelectedItem() == null)   return;
                System.out.println("RsToExcel");
                if(Window.getFilePath() == ""){
                    new Toast("EmptyFP");
                    return;
                }
                try {
                    ResultSetToExcel.convert(DBConnection.getTableResult(jcb.getSelectedItem().toString()),Window.getFilePath(),jtf1.getText() + "-" + jcb.getSelectedItem().toString());
                }catch (Exception ex){
                    ex.printStackTrace();
                }


            }

            else if(e.getActionCommand() == "ExcelToSql"){
                JFileChooser jfc = new JFileChooser();
                //限定选取文件格式
                jfc.setFileFilter(new FileNameExtensionFilter(".txt、.doc、.docx", "txt", "doc", "docx"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setDialogTitle("请选择txt或word文件");
                jfc.showDialog(new Label(), "选择");

                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();
            }
        }
    }
}





	
