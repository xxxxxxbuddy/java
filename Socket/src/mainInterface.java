import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.net.*;
import java.io.*;
class mainInterface extends JFrame implements ActionListener
{
	JFrame jf=new JFrame("������");
	
	public Client soc;
	public PrintWriter pw;
	
	public JPanel jp1=new JPanel();
	public JPanel jp2=new JPanel();
	public JPanel jp3=new JPanel();
	public JPanel jp4=new JPanel();
	public JPanel jp5=new JPanel();
	public JPanel jp6=new JPanel();
	public JPanel jp7=new JPanel();
	
	public static JTextArea jta1=new JTextArea(12,42);
	public static JTextArea jta2=new JTextArea(12,42);
	
	public JLabel jl1=new JLabel("��");
	
	public static JComboBox jcomb=new JComboBox();
	
	public JCheckBox jcb=new JCheckBox("˽��");
	
	public JTextField jtf=new JTextField(36);
	
	public JButton jb1=new JButton("����>>");
	public JButton jb2=new JButton("ˢ��");
	
	public static DefaultListModel listModel1;
	public static JList lst1;
	
	public String na;
	public String se;
	public String message;
	
	
	public void getMenu(String name,String sex)//��ʾ�������
	{
	
		jcomb.addItem("������");
		
		this.na=name;
		this.se=sex;
		
		jta1.setEditable(false);
		jta2.setEditable(false);
		
		listModel1= new DefaultListModel();
		
		lst1 = new JList(listModel1);
		lst1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lst1.setVisibleRowCount(18);
		lst1.setFixedCellHeight(28);
		lst1.setFixedCellWidth(100);
		
		JScrollPane jsp1=new JScrollPane(jta1);
		JScrollPane jsp2=new JScrollPane(jta2);
		JScrollPane jsp3=new JScrollPane(lst1);
		
		jsp3.setBorder(new TitledBorder("�û��б�"));
		jsp1.setBorder(new TitledBorder("������Ƶ��"));
		jsp2.setBorder(new TitledBorder("�ҵ�Ƶ��"));
		
		jp1.setLayout(new GridLayout(2,1));
		jp1.add(jsp1);
		jp1.add(jsp2);
		
		jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp2.add(jl1);
		jp2.add(jcomb);
		jp2.add(jcb);
		
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.add(jtf);
		jp3.add(jb1);
		
		jp4.setLayout(new GridLayout(2,1));
		jp4.add(jp2);
		jp4.add(jp3);
		
		jp5.setLayout(new BorderLayout());
		jp5.add(jp1,BorderLayout.NORTH);
		jp5.add(jp4,BorderLayout.SOUTH);
		
		jp6.setLayout(new BorderLayout());
		jp6.add(jsp3,BorderLayout.NORTH);
		jp6.add(jb2,BorderLayout.SOUTH);
		
		jp7.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp7.add(jp5);
		jp7.add(jp6);
		
		jf.add(jp7);
		
		jf.setLocation(200,200);
		jf.setSize(700,650);
		
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.setVisible(true);
		
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		jta1.setLineWrap(true);
		jta2.setLineWrap(true);
		jsp1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jf.pack();
	}
	public void sock()
	{try{
		String user=na+"("+se+")";//���û���Ϣ������ַ�����ʽ
		soc=new Client(user);//�����ͻ��˶���
		pw=new PrintWriter(soc.socket.getOutputStream());//���������
		pw.println("1008611");//���ͺ����б��ʶ
		pw.println(na+":"+se);//�����û���Ϣ
		pw.flush();
		pw.println("10086");//���ͽ��������ұ�ʶ
		pw.println("��"+na+"��"+"����������");//���ͽ�����������Ϣ
		pw.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public mainInterface() {//���ô��ڹر��¼����������������Ͻǲ�Źرգ�ִ���±߳���
	jf.addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				try {
				pw=new PrintWriter(soc.socket.getOutputStream());
				pw.println("456987");//�������߱�ʶ
				pw.println(na+":�뿪������");//����������Ϣ
				pw.flush();
				jf.dispose();//�رմ���
				}catch(Exception ex) {
				}
			}
		}
		);	
	}
	public void actionPerformed(ActionEvent event)//�¼�����
	{
		jb1.setText("����>>");
		jb2.setText("ˢ��");
		
		try{
			pw=new PrintWriter(soc.socket.getOutputStream());
			if(event.getActionCommand().equals("����>>"))//������ʹ���
			{
				
				if(!jtf.getText().equals(""))
				{
					if(jcb.isSelected())
					{
						String name1=(String)jcomb.getSelectedItem();
						message="���Ļ�"+na+"("+se+")"+"��"+name1+"˵��"+jtf.getText();
						pw.println("841163574");//����˽�ı�ʶ
						pw.println(na+":"+name1+"1072416535"+message);//����˽����Ϣ
						pw.flush();
					}
					else{
						pw.println("10010");//���������ʶ
						pw.println(na+"˵��"+jtf.getText());//����������Ϣ
						pw.flush();
					}
				}
			}
			else if(event.getActionCommand().equals("ˢ��"))//���ˢ�´���
			{
				pw=new PrintWriter(soc.socket.getOutputStream());
				pw.println("123654");//����ˢ�±�ʶ
				pw.flush();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		jtf.setText("");//�����������Ϣ
		jtf.requestFocus();//���뽹��
	}
}
