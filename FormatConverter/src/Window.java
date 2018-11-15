import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.*;

public class Window {
	JFrame jf;
	JPanel jp;
	JButton jb1, jb2 ,jb3 ,jb4;
	
	public Window() {
		jp = new JPanel();
		jb1 = new JButton("txt与Word");
		jb2 = new JButton("txt与Word");
		jb3 = new JButton("txt与Word");
		jb4 = new JButton("txt与Word");
		
//		jb1.setBounds(5, 10, 200, 100);
//		jb2.setBounds(220, 10, 200, 100);
//		jb3.setBounds(5, 130, 200, 100);
//		jb4.setBounds(220, 130, 200, 100);
		
		jb1.setPreferredSize(new Dimension(200, 100));
		jb2.setPreferredSize(new Dimension(200, 100));
		jb3.setPreferredSize(new Dimension(200, 100));
		jb4.setPreferredSize(new Dimension(200, 100));
		//jb1.setIcon(new ImageIcon("队徽.jpg"));
		//jb1.setMargin(new Insets(0, 0, 0, 0));
		jb1.setBackground(new Color(152, 245, 255));
		
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);
		jp.add(jb4);
		
		jf = new JFrame("格式转换器");
		jf.setBounds(500, 300, 460, 260);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setMaximumSize(new Dimension(460, 260));
		jf.add(jp);
		
	}
	
	public static void main(String[] args) {
		Window window = new Window();
	}
}
