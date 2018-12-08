package FormatConverter.View;

import org.apache.log4j.Logger;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.FlatBorderPainter;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class Setting extends JFrame {
    private Properties settings = new Properties();
    private String appProperties = "app.properties";
    File file = null;
    private static Logger logger = Logger.getLogger(Setting.class);

    public Setting() {
        //读取UI库
        try {
            UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
            //SubstanceLookAndFeel.setSkin(new NebulaSkin());
            SubstanceLookAndFeel.setCurrentBorderPainter(new FlatBorderPainter());
        } catch (Exception e) {
            logger.error("UI库加载失败:" + e.getMessage());
        }


        JFrame jf = new JFrame();
        JPanel jp = new JPanel();
        JLabel jl1 = new JLabel("输出路径:");
        JLabel jl2 = new JLabel("输出文件名：");
        JTextField jtf1 = new JTextField("<原文件目录>");
        JTextField jtf2 = new JTextField("<原文件名>");
        jtf1.setEditable(false);
        JButton jb = new JButton("...");
        JButton jb1 = new JButton("确定");
        JButton jb2 = new JButton("取消");
        jtf1.setPreferredSize(new Dimension(100, 20));
        jp.setLayout(null);
        jp.add(jl1);
        jp.add(jl2);
        jp.add(jb);
        jf.add(jb1);
        jf.add(jb2);
        jp.add(jtf1);
        jp.add(jtf2);
        jf.add(jp);
        jl1.setBounds(10, 20, 70, 20);
        jl2.setBounds(10, 50, 90, 20);
        jtf1.setBounds(90, 20, 300, 20);
        jtf2.setBounds(90, 50, 300, 20);
        jb.setBounds(400, 20, 35, 20);
        jb1.setBounds(100, 100, 60, 30);
        jb2.setBounds(300, 100, 60, 30);
        jf.setBounds(600, 600, 480, 200);
        if (!Window.getFilePath().equals("")) {
            jtf1.setText(Window.getFilePath());
        } else {
            jtf1.setText("<原文件目录>");
        }

        if (!Window.getFileName().equals("")) {
            jtf2.setText(Window.getFileName());
        } else {
            jtf2.setText("<原文件名>");
        }
        jb.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new Label(), "选择目录");
                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();
                jtf1.setText(file.getAbsolutePath());
                logger.info("设置输出路径:" + file.getAbsolutePath());
            }
        });
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //写入配置
                    logger.info("尝试写入配置...");
                    FileOutputStream out = new FileOutputStream(appProperties);
                    settings.put("filePath", jtf1.getText()=="<原文件目录>" ? "" : jtf1.getText());
                    settings.put("fileName", jtf2.getText()=="<原文件名>" ? "" : jtf2.getText());
                    settings.store(out, "AppConfig");
                    out.close();
                    if (!jtf2.getText().equals("<原文件目录>")) {
                        Window.setFilePath(jtf1.getText());
                    }
                    if (!jtf2.getText().equals("<原文件名>")) {
                        Window.setFileName(jtf2.getText());
                    }
                    logger.info("配置更新成功");
                } catch (Exception ex1) {
                    logger.error("配置写入失败:" + ex1.getMessage());
                }
                jf.setVisible(false);
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
            }
        });
        jf.setDefaultCloseOperation(HIDE_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setIconImage(new ImageIcon("Setting.png").getImage());
        jf.setTitle("设置");

    }

}
