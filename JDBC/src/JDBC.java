import java.sql.*;

public class JDBC {
	public static void main(String [] args){
	  String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	  String dbURL="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=lalala;integratedSecurity=true";		//(Windows�����֤)
	  //String userName="JDBC";
	  //String userPwd="zhanglei";
	 try{
	    Class.forName(driverName);
	    System.out.println("���������ɹ���");
	}catch(Exception e){
	    e.printStackTrace();
	    System.out.println("��������ʧ�ܣ�");
	}
	 try{
	    Connection dbConn=DriverManager.getConnection(dbURL);
	    System.out.println("�������ݿ�ɹ���");
	    Statement st1 = dbConn.createStatement();
	    /* ��ѯ���� */
	    //ResultSet rs1 = st1.executeQuery("SELECT * FROM db");	
		//    while(rs1.next()){
		//        System.out.println(rs1.getString(1));
		//    }
	    
	    /* �������� */
	    //    System.out.println(st1.executeUpdate("UPDATE db SET title='����' WHERE tag='test'"));
	
	    /* ɾ������ */
	    //   System.out.println(st1.executeUpdate("DELETE db WHERE tag='test'"));
	    
	    /* �������� */
	       System.out.println(st1.executeUpdate("INSERT db(title,tag,time) VALUES('JDBC','java','Oct,13 2018')"));

	 }catch(Exception e){
		 e.printStackTrace();
    System.out.print("SQL Server����ʧ�ܣ�");
	 }        
	}
}