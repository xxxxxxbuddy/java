package contact;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class contactBook {
	
	private static int i = 0;
	private static contact[] contactBook = new contact[1000];
	
	public void add(contact a){
		contactBook[i] = a;
		i++;
	}

	/* 通过姓名查找电话 */
	public void findPhoneNum(String name){		
		boolean flag = false;
		for (int j = 0;j < i;j++) {
			if(contactBook[j].getName().equals(name)){
				System.out.println("电话号码为：" + contactBook[j].getPhoneNum());
				System.out.println("——————————————————————");
				flag = true;
				break;
				//return contactBook[j].getPhoneNum(); 
			}
		}
		if(!flag){
			System.out.println("无此联系人");
			System.out.println("——————————————————————");
		}
	}
	
	/* 通过电话查找姓名 */
	public void findName(String phoneNumber){
		boolean flag = false;
		for(int k = 0;k <i;k++){
			if(contactBook[k].getPhoneNum().equals(phoneNumber)){
				System.out.println(contactBook[k].getName());
				System.out.println("——————————————————————");
				flag = true;
				break;
			}
		}
		if(!flag){
			System.out.println("无此联系人");
			System.out.println("——————————————————————");
		}
	}
	
	public void checkAllContacts(){
		for(int s = 0;s < i ; s++){
			System.out.println("姓名：" + contactBook[s].getName() + "\t电话号码：" + contactBook[s].getPhoneNum());
		}
		System.out.println("——————————————————————");
	}
	
	public static void main(String[] args){
		boolean isExit = false;
		contactBook cBook = new contactBook();
		contact contact_a = new contact("张三",new PhoneNumber(123, 456, 7890));
		contact contact_b = new contact("李四", new PhoneNumber(133, 555, 6789));
		cBook.add(contact_a);
		cBook.add(contact_b);
		System.out.println("————————电话簿————————");
		while(!isExit){
			System.out.println("请选择功能：1.添加联系人");
			System.out.println("         2.查找");
			System.out.println("         3.查看电话簿");
			System.out.println("         4.退出");
			int input = 0;
			String str = null;
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			try {
				while((str = br.readLine()).equals(""))
				{

				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			input = Integer.parseInt(str);
			if(input == 2){		//查找联系人
				boolean isNumeric = true;
				System.out.println("请输入联系人姓名或电话号码");
				String userInput = null;
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				try {
					while((userInput = in.readLine()).equals("")){
						
					}
					//in.close();		若关闭无法二次查找
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				for(int i = 0;i < userInput.length();i++){
					if(!Character.isDigit(userInput.charAt(i))){
						isNumeric = false;
						break;
					}
				}
				/* 判断用户输入的是数字还是字符串 */
				if(isNumeric){
					cBook.findName(userInput);
				}else{
					cBook.findPhoneNum(userInput);
				}
			}else if (input == 1) {		//添加联系人
				String name = null,phoneNumber = null;
				System.out.println("请输入姓名：");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				try {
					while((name = in.readLine()).equals("")){}					
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("请输入电话号码（限定10位）：");
				try {
					while((phoneNumber = in.readLine()).equals("")){}					
				} catch (IOException e) {
					e.printStackTrace();
				}
				cBook.add(new contact(name, new PhoneNumber(Integer.parseInt(phoneNumber.substring(0, 3)), Integer.parseInt(phoneNumber.substring(3, 6)), Integer.parseInt(phoneNumber.substring(6, 10)))));
				System.out.println("添加成功！");
				System.out.println("——————————————————————");
				
			}else if(input == 3){
				cBook.checkAllContacts();
			}else if(input == 4){
				isExit = true;
			}else{
				System.out.println("输入有误2");
				System.out.println("——————————————————————");
			}
		}

	}
}

class contact{
	private String name = null;
	private PhoneNumber phoneNumber;
	public contact(String name, PhoneNumber phoneNumber){
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPhoneNum(){
		return this.phoneNumber.toString();
	}
}

