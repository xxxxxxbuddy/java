import java.net.Socket;

class User
{
	private String name;//�û�����
	private String sex;//�û��Ա�
	private Socket sock;//�û��Լ���socket
	public User(String name,String sex,Socket sock)
	{
		setName(name);
		setSex(sex);
		setSock(sock);
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex=sex;
	}
	public Socket getSock()
	{
		return sock;
	}
	public void setSock(Socket sock)
	{
		this.sock=sock;
	}
	
}
