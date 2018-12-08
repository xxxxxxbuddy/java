package FormatConverter.DAL;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 与mysql数据库的连接
 */
public final class DBConnection {
    private Logger logger = Logger.getLogger(DBConnection.class);

//  private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

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

    public DBConnection(String database, String user, String password) {
        this.server = "localhost";
        this.port = 3306;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public DBConnection(String server, int port, String database, String user, String password) {
        this(database, user, password);
        this.server = server;
        this.port = port;
    }

    /**
     * 连接到数据库
     *
     * @return 成功返回true，失败返回false
     */
    public boolean connect() {
        logger.info("开始进行数据库连接...");
        try {
            if (conn != null) if (!conn.isClosed()) {
                logger.info("关闭原有连接...");
                conn.close();
            }

            conn = null;
            logger.info("加载数据库驱动...");
            Class.forName(JDBC_DRIVER);
            String dbUrl = String.format(
                    "jdbc:mysql://%s:%d/%s?useSSL = false&serverTimezone = UTC&allowPublicKeyRetrieval=true", server,
                    port, database);
            logger.info("进行数据库连接...");
            conn = DriverManager.getConnection(dbUrl, user, password);

            if (conn.isClosed()) return false;
        } catch (SQLException e) {
            logger.error("数据库连接异常：" + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            logger.error("数据库连接驱动加载异常：" + e.getMessage());
            return false;
        }

        logger.info("连接成功...");
        return true;
    }

    /**
     * 关闭当前数据库连接
     */
    public void close() {
        if (conn != null) try {
            logger.info("关闭当前数据库连接...");
            conn.close();
        } catch (SQLException e) {
            logger.error("数据库异常：" + e.getMessage());
        }
    }

    /**
     * @return 数据库连接关闭返回true，否则返回false
     */
    public boolean isClosed() {
        if (conn == null) return true;
        try {
            return conn.isClosed();
        } catch (SQLException e) {
            logger.error("数据库异常：" + e.getMessage());
            return false;
        }
    }

    /**
     * 查询当前数据库的所有表名
     *
     * @return 数据表名集合
     */
    public List<String> getTableNames() {
        logger.info("开始查询当前数据库的所有表名...");
        List<String> tableNames = new ArrayList<String>();

        if (isClosed()) {
            logger.info("数据库未连接...");
            return tableNames;
        }

        String sql = String.format("select table_name from information_schema.TABLES where TABLE_SCHEMA='%s'",
                database);

        logger.info("执行查询的Sql语句...");
        try (Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery(sql);) {
            logger.info("读取查询结果...");
            while (rs.next())
                tableNames.add(rs.getString("table_name"));
        } catch (SQLException e) {
            logger.error("数据库异常：" + e.getMessage());
        } finally {
            logger.info("查询结束...");
        }

        return tableNames;
    }

    /**
     * 获取数据表的数据集
     *
     * @param tableName 数据表名
     * @return 数据表的数据集
     */
    public ResultSet getTableResult(String tableName) {
        logger.info(String.format("获取%s的数据集...", tableName));
        ResultSet rs = null;
        Statement stat = null;
        String sql = String.format("select * from %s", tableName);

        try {
            logger.info("执行查询的Sql语句...");
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
        } catch (SQLException e) {
            logger.error("数据库异常：" + e.getMessage());
        } finally {
            logger.info("查询结束...");
        }

        return rs;
    }

    /**
     * 数据库插入操作
     *
     * @param sql 插入的sql语句
     * @return 影响的行数
     */
    public int insert(String sql) {
        logger.info("开始数据库插入操作...");
        int num = 0;

        try (Statement stat = conn.createStatement();) {
            logger.info("执行插入的Sql语句...");
            num = stat.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("数据库异常：" + e.getMessage());
        } finally {
            logger.info("插入结束...");
        }

        return num;
    }

}
