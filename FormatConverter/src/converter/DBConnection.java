package converter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 与mysql数据库的连接
 */
public final class DBConnection
{
//  private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//  private static final String DB_URL = "jdbc:mysql://localhost:3306/test";

    // 新版MySQL的连接驱动方式不同
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /* 服务器名（默认为localhost） */
    private String server;

    /* 端口号（默认为3306） */
    private int port;

    /* 数据库名 */
    private String database;

    /* 用户名 */
    private String user;

    /* 密码 */
    private String password;

    /* 数据库的连接 */
    private Connection conn;

    public DBConnection(String database, String user, String password)
    {
	this.server = "localhost";
	this.port = 3306;
	this.database = database;
	this.user = user;
	this.password = password;
    }

    public DBConnection(String server, int port, String database, String user, String password)
    {
	this(database, user, password);
	this.server = server;
	this.port = port;
    }

    /**
     * 连接到数据库
     * 
     * @return 成功返回true，失败返回false
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean connect() throws ClassNotFoundException, SQLException
    {
	if (conn != null && !conn.isClosed()) conn.close();

	conn = null;
	Class.forName(JDBC_DRIVER);
	String dbUrl = String.format("jdbc:mysql://%s:%d/%s?useSSL = false&serverTimezone = UTC", server, port,
		database);
	conn = DriverManager.getConnection(dbUrl, user, password);

	if (conn.isClosed()) return false;
	return true;
    }

    /**
     * 关闭数据库
     * 
     * @throws SQLException
     */
    public void close() throws SQLException
    {
	conn.close();
    }

    /**
     * 查询当前数据库的所有表名
     * 
     * @return 数据表名集合
     * @throws SQLException
     */
    public List<String> getTableNames() throws SQLException
    {
	List<String> tableNames = new ArrayList<String>();

	if (conn != null && !conn.isClosed())
	{
	    Statement stat = null;
	    ResultSet rs = null;
	    String sql = String.format("select table_name from information_schema.TABLES where TABLE_SCHEMA='%s'",
		    database);

	    stat = conn.createStatement();
	    rs = stat.executeQuery(sql);

	    while (rs.next())
		tableNames.add(rs.getString("table_name"));

	    rs.close();
	    stat.close();
	}

	return tableNames;
    }

    /**
     * 获取数据表的数据集
     * 
     * @param tableName 数据表名
     * @return 数据表的数据集
     * @throws SQLException
     */
    public ResultSet getTableResult(String tableName) throws SQLException
    {
	ResultSet rs = null;

	Statement stat = null;
	String sql = String.format("select * from %s", tableName);

	stat = conn.createStatement();
	rs = stat.executeQuery(sql);
	stat.close();

	return rs;
    }

    /**
     * 数据库插入操作
     * 
     * @param sql 插入的sql语句
     * @return 影响的行数
     * @throws SQLException
     */
    public int insert(String sql) throws SQLException
    {
	Statement stat = conn.createStatement();
	int num = stat.executeUpdate(sql);
	stat.close();

	return num;
    }

}
