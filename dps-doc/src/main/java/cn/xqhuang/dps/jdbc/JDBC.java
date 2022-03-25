package cn.xqhuang.dps.jdbc;

import java.sql.*;
import java.util.UUID;

public class JDBC {
 
    /*private String userName = " ";
    private String passwordName = " ";
    //private String urlName = "jdbc:oracle:thin:@34.115.123.11:1521/tspw";
    private String urlName = "jdbc:oracle:thin:@34.115.125.11:1521:JTDN";*/
 
 
    private Connection conn = null;//连接对象
    private ResultSet rs = null;//结果集对象
    private Statement sm = null;
 
 
    //用于连接数据库（oracle 其他数据库改一下驱动即可）
    public static Connection getOracleConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");// 加载Oracle驱动程序
            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&useSSL=false";
            String username = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, username, password);// 获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
 
 
    /**
     * 插入单条数据
     *
     * @param conn
     * @param sql
     * @return
     */
    public static boolean insert(Connection conn, String sql) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            return ps.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Connection oracleConn = getOracleConn();
        try {
            for (int i = 0; i < 100000; i++) {
                String sql = "INSERT INTO employees ( NAME, age, position ) VALUES( 'Lucy"+ i + "'," + i + ", 'dev');";

                insert(oracleConn, sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
    public static void main(String[] args) {
        String id = UUID.randomUUID().toString();
        String sql = "insert into FLOW_STATISTICS_TEST(ID,CROSS_ID,TIMESTAMP,EW,ES,EN,WE,WS,WN,SN,SE,SW,NS,NE,NW) "
                + "values('" + id + "','te1st', '男', '男', '男', '男', '男', '男', '男', '男', '男', '男', '男', '男', '男')";
        System.out.println(sql);
        Connection oracleConn = getOracleConn();
        insert(oracleConn, sql);
        try {
            oracleConn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
    }
     **/
}