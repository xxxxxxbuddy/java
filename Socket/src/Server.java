import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{
	private static final int PORT=7777;
	mainInterface gm=new mainInterface();
	private ServerSocket server;
	public ArrayList<PrintWriter> list;
	public static String user;
	public static ArrayList<User> list1=new ArrayList<User>();//�����û�����
	public User uu;
	public Server(String user)
	{
		this.user=user;
	}
	public void getServer()
	{
		list =new ArrayList<PrintWriter>();
		try{
			server=new ServerSocket(PORT);
			System.out.println("��������������ʼ����......");
			while(true)
			{
				Socket client=server.accept();//���տͻ����߳�
				PrintWriter writer = new PrintWriter(client.getOutputStream());
                list.add(writer); 
				Thread t = new Thread(new Chat(client));
                t.start();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		new Server(user).getServer();
	}
	class Chat implements Runnable
	{
		Socket socket;
		private BufferedReader br;
		private String msg;
		private String mssg="";
		
		public Chat(Socket socket) 
		{
			try{
				this.socket=socket;
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
					
					if(msg.equals("1008611"))//ƥ���ַ��� ��ʾ�����б�
					{
						msg=br.readLine();
						String[] st=msg.split(":");//���û���Ϣ����Ϣ�ָ���
						uu=new User(st[0],st[1],socket);//���û���Ϣ��ӵ�User������
						list1.add(uu);//��������ӵ��û�����
						Iterator<User> it=Server.list1.iterator();//�����û�����
						while(it.hasNext())
						{
							User use=it.next();
							msg=use.getName()+"("+use.getSex()+"):";
							mssg+=msg;//�����е��û���Ϣ���ӳ�һ���ַ���
						}
						sendMessage("1008611");//��ʾ�����б�ƥ���ʶ
						sendMessage(mssg);//Ⱥ����Ϣ
						}
					
					else if(msg.equals("10010"))//��ʾ˵����Ϣ
					{
						msg=br.readLine();
						System.out.println(msg);
						sendMessage("10010");//��ʾ˵����Ϣƥ���ʶ
						sendMessage(msg);
					}
					else if(msg.equals("10086"))//��ʾ����������
					{
						msg=br.readLine();
						System.out.println(msg);
						sendMessage("10086");//����������ƥ���ʶ
						sendMessage(msg);
					}
					else if(msg.equals("841163574"))//˽��
					{
						msg=br.readLine();
						String[] rt=msg.split("1072416535");//�Ѵ��������û���Ϣ��˵�����ݷֿ�
						System.out.println(rt[1]);//�ڷ���������ʾ˵������
						String[] tg=rt[0].split(":");//��Ϊ��˽�ģ������������û����û���Ϣ������������ٰ������û���Ϣ�ֿ�
						Iterator<User> iu=Server.list1.iterator();//�����û�����
						while(iu.hasNext())
						{
							User se=iu.next();
							if(tg[1].equals(se.getName()+"("+se.getSex()+")"))//������������û���Ϣ�������е��û���Ϣ�Ǻ�
							{
								try{
									PrintWriter pwriter=new PrintWriter(se.getSock().getOutputStream());//�����û��Լ�����
									pwriter.println("841163574");//ƥ���ʶ
									pwriter.println(rt[1]);//�򵥶��û�������Ϣ
									pwriter.flush();
									System.out.println(rt[1]);
								}catch(Exception ex){
									ex.printStackTrace();
								}
							}
							else if(tg[0].equals(se.getName()))//������������û���Ϣ�������е��û���Ϣ�Ǻ�
							{
								try{
									PrintWriter pwr=new PrintWriter(se.getSock().getOutputStream());//�����û��Լ�����
									pwr.println("841163574");//ƥ���ʶ
									pwr.println(rt[1]);//�򵥶��û�������Ϣ
									pwr.flush();
									System.out.println(rt[1]);
								}catch(Exception ex){
									ex.printStackTrace();
								}
							}
						}
						
					}
					else if(msg.equals("456987"))//����
					{
						msg=br.readLine();
						System.out.println(msg);//�ڷ������ʾ��Ϣ
						sendMessage("456987");//ƥ���ַ���
						sendMessage(msg);//ƥ����Ϻ�Ⱥ����Ϣ
						String[] si=msg.split(":");//�����������û�������Ϣ�ָ���
						Iterator<User> at=Server.list1.iterator();//�����û�����
						while(at.hasNext())
						{
							User sr=at.next();
							if(sr.getName().equals(si[0]))//������������û������û���������û��Ǻ�
							{
								list1.remove(sr);//���Ǻϵ��û��Ƴ�
								sr.getSock().close();//�رմ��û���socket
							}
						}
						break;
					}
					else if(msg.equals("123654"))//ˢ��
					{
						String mssge="";
						Iterator<User> iter=Server.list1.iterator();//�����û�����
						while(iter.hasNext())
						{
							User uus=iter.next();
							msg=uus.getName()+"("+uus.getSex()+"):";
							mssge+=msg;//�����е��û���Ϣ���ӳ�һ���ַ���
							
						}
						sendMessage("123654");//����ˢ��ƥ���ʶ
						sendMessage(mssge);//Ⱥ����Ϣ
					}
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	public void sendMessage(String message)//Ⱥ����Ϣ����
		{
			try{
			for(PrintWriter pw:list)//���������
			{
				pw.println(message);
				pw.flush();
			}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
}
