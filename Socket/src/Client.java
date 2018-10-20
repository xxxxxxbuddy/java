import java.net.*;
import java.io.*;
public class Client//�ͻ���
{
	private static final int PORT = 7777;//�˿�
	public static String user;
	public static Socket socket;
	public Client(String user)
	{
		this.user=user;
		
		try{
			socket=new Socket("127.0.0.1",PORT);//����socket����
			System.out.println("��"+user+"����ӭ���������ң�");
			
			Thread tt=new Thread(new Recove(socket,user));//�����ͻ����߳�
			tt.start();//�����߳�
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new Client(user);
		
	}
}
class Recove implements Runnable
{
	public String user;
	private Socket socket;
	private BufferedReader keybord;
	public BufferedReader br;
	private PrintWriter pw;
	private String msg;
	mainInterface gm=new mainInterface();
	public Recove(Socket socket,String user) throws IOException
	{
	try{
		this.socket=socket;
		this.user=user;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void run()
	{
		try{
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while((msg=br.readLine())!=null)
			{
				String message=msg;
				if(message.equals("1008611"))//ƥ���ַ��� ��ʾ�����б�
				{
					gm.listModel1.clear();//����ǰ��պ����б�
					gm.jcomb.removeAllItems();//���JCombox
					gm.jcomb.addItem("������");
					message=br.readLine();
					String[] str=message.split(":");//�����յ��������û���Ϣ�ָ���
					for(String ss:str)
					{
						gm.listModel1.addElement(ss);//�������û���Ϣ��ӵ������б�
						gm.jcomb.addItem(ss);//�������û���Ϣ��ӵ�JCombox
					}
				}
				else if(message.equals("841163574"))//˽��
				{
					message=br.readLine();
					System.out.println("�յ���"+message);//�ڷ���������ʾ˽����Ϣ
					gm.jta2.append(message+"\n");//���ҵ�Ƶ����ʾ˽����Ϣ
				}
				else if(message.equals("10010"))//��ʾ˵����Ϣ
				{
					message=br.readLine();
					System.out.println("�յ���"+message);//�ڷ���������ʾ˵����Ϣ
					gm.jta1.append(message+"\n");//�ڹ���Ƶ����ʾ˵����Ϣ
					gm.jta2.append(message+"\n");//���ҵ�Ƶ����ʾ˵����Ϣ
				}
				else if(message.equals("10086"))//��ʾ����������
				{
					message=br.readLine();
					gm.jta1.append(message+"\n");//�ڹ���Ƶ����ʾ������������Ϣ
					gm.jta2.append(message+"\n");//���ҵ�Ƶ����ʾ������������Ϣ
				}
				else if(message.equals("123654"))//ˢ��
				{
					gm.listModel1.clear();//�������б����
					gm.jcomb.removeAllItems();//��JCombox ���
					gm.jcomb.addItem("������");
					message=br.readLine();
					String[] sr=message.split(":");//�����յ����û���Ϣ�ָ���
					for(String sst:sr)
					{
						gm.listModel1.addElement(sst);////��ˢ�º������û���Ϣ��ӵ������б�
						gm.jcomb.addItem(sst);//��ˢ�º������û���Ϣ��ӵ�JCombox
					}
				}
				else if(message.equals("456987"))//����
				{
					message=br.readLine();
					gm.jta1.append(message+"\n");//�ڹ���Ƶ����ʾ�û�������Ϣ
					gm.jta2.append(message+"\n");//���ҵ�Ƶ����ʾ�û�������Ϣ
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

}
