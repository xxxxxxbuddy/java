package FormatConverter.View;

import FormatConverter.DAL.DBConnection;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.FlatBorderPainter;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class db {
    JFrame jf;
    JPanel jp;
    JLabel jl1, jl2, jl3, jl4, jl5;
    JTextField jtf1, jtf2, jtf3, jtf4, jtf5;
    JButton jb1, jb2;
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

        Database = "lalala";
        port = 3066;
        username = "user";
        password = "";
        url = "127.0.0.1";

        jtf1 = new JTextField(Database);
        jtf2 = new JTextField(Integer.toString(port));
        jtf3 = new JTextField(username);
        jtf4 = new JPasswordField(password);
        jtf5 = new JTextField(url);

        jb1 = new JButton("连接");
        jb2 = new JButton("退出");

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

        jb1.setActionCommand("Connect");
        jb2.setActionCommand("Exit");
        action act1 = new action();
        action act2 = new action();
        jb1.addActionListener(act1);
        jb2.addActionListener(act2);
        jf = new JFrame("连接服务器");
        jf.setVisible(true);
        jf.setBounds(600, 300, 360, 310);
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
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            isConnected = dbc.connect();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                thread.start();
                System.out.println(isConnected);
                if(isConnected){
                    try{
                    List<String> tableNames = dbc.getTableNames();
                    System.out.println(tableNames.size());
                    }catch (Exception ex1){
                        ex1.printStackTrace();
                    }
                }
            }
        }
    }

}





	
