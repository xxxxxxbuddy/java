import java.sql.*;

public class JDBC {
	public static void main(String [] args){
	  String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	  String dbURL="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=lalala;integratedSecurity=true";		//(Windows身份验证)
	  //String userName="JDBC";
	  //String userPwd="zhanglei";
	 try{
	    Class.forName(driverName);
	    System.out.println("加载驱动成功！");
	}catch(Exception e){
	    e.printStackTrace();
	    System.out.println("加载驱动失败！");
	}
	 try{
	    Connection dbConn=DriverManager.getConnection(dbURL);
	    System.out.println("连接数据库成功！");
	    Statement st1 = dbConn.createStatement();
	    /* 查询数据 */
	    //ResultSet rs1 = st1.executeQuery("SELECT * FROM db");	
		//    while(rs1.next()){
		//        System.out.println(rs1.getString(1));
		//    }
	    
	    /* 更新数据 */
	    //    System.out.println(st1.executeUpdate("UPDATE db SET title='测试' WHERE tag='test'"));
	
	    /* 删除数据 */
	    //   System.out.println(st1.executeUpdate("DELETE db WHERE tag='test'"));
	    
	    /* 插入数据 */
	       System.out.println(st1.executeUpdate("INSERT db(title,tag,time) VALUES('JDBC','java','Oct,13 2018')"));

	 }catch(Exception e){
		 e.printStackTrace();
    System.out.print("SQL Server连接失败！");
	 }        
	}
}