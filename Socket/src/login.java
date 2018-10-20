import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.*;
class Landen extends Frame implements ActionListener
{
	JFrame jf=new JFrame("聊天登陆");
	
	JPanel jp1=new JPanel();
	JPanel jp2=new JPanel();
	JPanel jp3=new JPanel();
	JPanel jp4=new JPanel();
	
	
	JLabel jl1=new JLabel("姓名：");
	JLabel jl2=new JLabel("地址：");
	JLabel jl3=new JLabel("端口：");
	
	JRadioButton jrb1=new JRadioButton("男生");
	JRadioButton jrb2=new JRadioButton("女生");
	JRadioButton jrb3=new JRadioButton("保密");
	
	public JTextField jtf1=new JTextField(10);
	public JTextField jtf2=new JTextField(10);
	public JTextField jtf3=new JTextField(10);
	
	JButton jb1=new JButton("连接");
	JButton jb2=new JButton("断开");
	
	TitledBorder tb=new TitledBorder("");
	
	ButtonGroup gb=new ButtonGroup();
	
	public void init()//显示登录界面
	{
	
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		jp1.add(jl1);
		jp1.add(jtf1);
		jp1.add(jrb1);
		jp1.add(jrb2);
		jp1.add(jrb3);
		
	
		jp2.add(jl2);
		jp2.add(jtf2);
		jp2.add(jl3);
		jp2.add(jtf3);
		
		
		jp3.add(jb1);
		jp3.add(jb2);
		
		jp4.setLayout(new GridLayout(3,1));
		jp4.add(jp1);
		jp4.add(jp2);
		jp4.add(jp3);
		
	
		jf.add(jp4);
		
		
		jtf2.setText("localhost");
		jtf3.setText("7777");
		
		gb.add(jrb1);
		gb.add(jrb2);
		gb.add(jrb3);
		
		jf.setLocation(200, 200);
		jf.setSize(350, 200);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setVisible(true);
	}
	
		
		
		public void actionPerformed(ActionEvent event)//事件触发
		{
			jb1.setText("连接");
			jb2.setText("断开");
			String s1=null;
			
			if(event.getActionCommand().equals("断开"))
			{
				System.exit(0);
			}
			if(event.getActionCommand().equals("连接"))
			{
				if(jtf1.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null,"请输入用户名！");
				}
				else if(!jrb1.isSelected()&&!jrb2.isSelected()&&!jrb3.isSelected())
				{
					JOptionPane.showMessageDialog(null,"请选择性别！");
				}
				else
				{
					jf.setVisible(false);
					if(jrb1.isSelected())
					{
						s1="boy";
					}
					else if(jrb2.isSelected())
					{
						s1="girl";
					}
					else if(jrb3.isSelected())
					{
						s1="secret";
					}
					
					mainInterface gmu=new mainInterface();
					gmu.getMenu(jtf1.getText(),s1);
					gmu.sock();
					
					
				}
			}
			
			
			
		}
	
}
public class login
{
	public static void main(String[] args)
	{
		new Landen().init();
	}
}
