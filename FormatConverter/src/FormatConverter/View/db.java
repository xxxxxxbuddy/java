package FormatConverter.View;

import FormatConverter.DAL.DBConnection;
import FormatConverter.util.SqlConvertor;
import org.apache.log4j.Logger;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.FlatBorderPainter;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class db {
    JFrame jf;
    JPanel jp;
    JLabel jl1, jl2, jl3, jl4, jl5;
    JTextField jtf1, jtf2, jtf3, jtf4, jtf5;
    JButton jb1, jb2, jb3, jb4, jb5, jb6;
    JComboBox jcb;
    private String Database, username, password, url;
    private int port;
    private static DBConnection dbc;
    private static Logger logger = Logger.getLogger(db.class);

    public db() {
        logger.info("载入数据库连接界面...");
        try {
            UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
            //SubstanceLookAndFeel.setSkin(new NebulaSkin());
            SubstanceLookAndFeel.setCurrentBorderPainter(new FlatBorderPainter());
        } catch (Exception e) {
            logger.error("UI库加载失败:" + e.getMessage());
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
        jb6 = new JButton("断开连接");

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
        jb5.setBounds(620, 30, 60, 20);
        jb3.setBounds(380, 130, 110, 30);
        jb4.setBounds(520, 130, 110, 30);
        jb6.setBounds(450, 180, 110, 30);

        jcb.setBounds(370, 30, 240, 20);
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
        jp.add(jb6);
        jp.add(jcb);

        jb1.setActionCommand("Connect");
        jb2.setActionCommand("Exit");
        jb5.setActionCommand("Refresh");
        jb3.setActionCommand("RsToExcel");
        jb4.setActionCommand("ExcelToSql");
        jb6.setActionCommand("disconnect");
        action act1 = new action();
        action act2 = new action();
        action act3 = new action();
        action act4 = new action();
        action act5 = new action();
        action act6 = new action();
        jb1.addActionListener(act1);
        jb2.addActionListener(act2);
        jb3.addActionListener(act3);
        jb4.addActionListener(act4);
        jb5.addActionListener(act5);
        jb6.addActionListener(act6);
        jf = new JFrame("连接服务器");
        jf.setVisible(true);
        jf.setBounds(600, 300, 360, 300);
        //jf.setLayout(null);
        jf.setResizable(false);
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

            if (e.getActionCommand().equals("Exit")) {
                logger.info("退出数据库连接界面");
                System.exit(0);
            } else if (e.getActionCommand().equals("Connect")) {
                logger.info("尝试连接数据库...");
                dbc = new DBConnection(jtf5.getText(), Integer.parseInt(jtf2.getText()), jtf1.getText(), jtf3.getText(), jtf4.getText());
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    synchronized public void run() {
                        try {
                            while (jf.getWidth() <= 700) {
                                jf.setSize(jf.getWidth() + 20, 300);
                                wait(20);
                            }
                        } catch (Exception ex1) {
                            logger.error("界面线程阻塞:" + ex1.getMessage());
                        }
                    }
                });
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            isConnected = dbc.connect();
                            SwingUtilities.invokeAndWait(thread2);
                            if (isConnected) {
                                try {
                                    jtf1.setEditable(false);
                                    jtf2.setEditable(false);
                                    jtf3.setEditable(false);
                                    jtf4.setEditable(false);
                                    jtf5.setEditable(false);
                                    List<String> tableNames = dbc.getTableNames();
                                    for (int i = 0; i < tableNames.size(); i++) {
                                        jcb.addItem(tableNames.get(i));
                                    }
                                } catch (SQLException ex1) {
                                    logger.error("数据库查询错误:" + ex1.getMessage());
                                }
                            }
                        } catch (Exception ex) {
                            logger.error("数据库连接失败:" + ex.getMessage());
                            JOptionPane.showMessageDialog(jf, "数据库连接失败", "提示", JOptionPane.WARNING_MESSAGE);
                        }

                    }
                });
                thread.start();

            }
            //重新读取数据库中的表
            else if (e.getActionCommand().equals("Refresh")) {
                logger.info("重新读取数据库中的表...");
                try{
                    List<String> tableNames = dbc.getTableNames();
                    jcb.removeAllItems();
                    for (int i = 0; i < tableNames.size(); i++) {
                        jcb.addItem(tableNames.get(i));
                    }
                }catch(Exception ex2){
                        ex2.getMessage();
                    }

            }
            //从数据库中提取表存为Excel
            else if (e.getActionCommand().equals("RsToExcel")) {
                logger.info("从数据库中提取表...");
                if (jcb.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(jf, "未选择数据表", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (Window.getFilePath().equals("")) {
                    JOptionPane.showMessageDialog(jf, "请设置输出路径", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    ResultSet rs = DBConnection.getTableResult(jcb.getSelectedItem().toString());
                    SqlConvertor.ReusltSetToExcel(rs,Window.getFilePath(),jtf1.getText() + "-" + jcb.getSelectedItem().toString());
                } catch (Exception ex) {
                    logger.error("转换失败:" + ex.getMessage());
                }


            } else if (e.getActionCommand().equals("ExcelToSql")) {
                logger.info("导入Excel表到数据库中");
                String sql = null;
                int result = 0;
                JFileChooser jfc = new JFileChooser();
                //限定选取文件格式
                jfc.setFileFilter(new FileNameExtensionFilter(".xlsx、.xls", "xls", "xlsx"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setDialogTitle("请选择xlsx或xls文件");
                jfc.showDialog(new Label(), "选择");

                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();
                logger.info("选择源文件:" + file.getAbsolutePath());
                if (jcb.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(jf, "无效数据库表名", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    logger.info("尝试插入数据库...");
                    sql = SqlConvertor.ExcelToSql(file, jcb.getSelectedItem().toString());
                    try{
                        result = dbc.insert(sql);   //影响的行数
                    }catch (SQLException ex){
                        logger.error("插入失败:" + ex.getMessage());
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(jf, "插入失败", "提示", JOptionPane.ERROR_MESSAGE);
                }
                if (result != 0) {
                    logger.info("插入成功");
                    JOptionPane.showMessageDialog(jf, "插入成功", "提示", JOptionPane.PLAIN_MESSAGE);
                }
            } else if (e.getActionCommand().equals("disconnect")) {
                logger.info("断开连接");
                try{
                    System.out.println(dbc);
                    dbc.close();
                }catch(SQLException ex1){
                    logger.error("数据库连接关闭失败");
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    synchronized public void run() {
                        try {
                            while (jf.getWidth() >= 360) {
                                jf.setSize(jf.getWidth() - 20, 300);
                                wait(20);
                            }
                            jcb.removeAllItems();
                            jtf1.setEditable(true);
                            jtf2.setEditable(true);
                            jtf3.setEditable(true);
                            jtf4.setEditable(true);
                            jtf5.setEditable(true);
                            isConnected = false;
                        } catch (Exception ex1) {
                            logger.error("界面线程阻塞:" + ex1.getMessage());
                        }
                    }
                });

            }
        }
    }
}





	
