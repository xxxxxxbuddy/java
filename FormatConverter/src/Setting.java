import javax.swing.*;
import java.awt.*;

public class Setting extends JFrame {
    public static String filePath = null;

    public Setting(){
        try {
            UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame jf = new JFrame();
        JPanel jp = new JPanel();
        JLabel jl = new JLabel("输出路径:");
        JTextField jtf = new JTextField("<源文件目录>");
        JButton jb = new JButton("click",new ImageIcon("./icon.png"));
        jtf.setPreferredSize(new Dimension(100,20));
        jp.setLayout(null);
        jp.add(jl);
        jp.add(jb);
        jp.add(jtf);
        jf.add(jp);
        jl.setBounds(10,20,70,20);
        jtf.setBounds(90,20,300,20);
        jb.setBounds(410,20,100,20);
        jf.setBounds(600,600,480,100);
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
    public static void main(String[] args){
        new Setting();
    }
}
