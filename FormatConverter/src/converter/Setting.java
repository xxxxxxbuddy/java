package converter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Setting extends JFrame {
    public static String filePath = null;
    private String appTitle = "app_title";
    private String appVersion = "app_version";
    private String appProperties = "app.properties";
    private Properties settings = new Properties();
    public Setting(){
        settings.put(appTitle,"格式转换器");
        settings.put(appVersion, "1.0");


        JFrame jf = new JFrame();
        JPanel jp = new JPanel();
        JLabel jl = new JLabel("输出路径:");
        JTextField jtf = new JTextField("<源文件目录>");
        jtf.setEditable(false);
        JButton jb = new JButton("...");
        jtf.setPreferredSize(new Dimension(100,20));
        jp.setLayout(null);
        jp.add(jl);
        jp.add(jb);
        jp.add(jtf);
        jf.add(jp);
        jl.setBounds(10,20,70,20);
        jtf.setBounds(90,20,300,20);
        jb.setBounds(410,20,40,20);
        jf.setBounds(600,600,480,100);

        try{
            FileInputStream in = new FileInputStream(appProperties);
            settings.load(in);
            String title = settings.getProperty(appTitle);
            String version = settings.getProperty(appVersion);
            if(!settings.getProperty("filePath").equals("")){
                jtf.setText(settings.getProperty("filePath"));
            }else{
                jtf.setText("<源文件目录>");
            }
            in.close();
            System.out.println(title + "\t" + version);
        }catch(Exception ex2){
            //ex2.printStackTrace();
            System.out.println("初次加载");
        }
        jb.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new Label(), "选择目录");
                File file = jfc.getSelectedFile();
                try {
                    //写入配置
                    FileOutputStream out = new FileOutputStream(appProperties);
                    settings.put("filePath",file.getAbsolutePath());
                    settings.store(out, "AppConfig");
                    out.close();
                } catch (Exception ex1) {
                    ex1.printStackTrace();
                }
                Window.setFilePath(file.getAbsolutePath());
                Window.setPath = true;
                System.out.println(Window.getFilePath());
                jtf.setText(file.getAbsolutePath());
            }
        });
        jf.setDefaultCloseOperation(HIDE_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);

    }
    public static void main(String[] args){
        new Setting();
    }
}
