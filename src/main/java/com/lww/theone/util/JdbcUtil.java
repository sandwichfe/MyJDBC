package com.lww.theone.util;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Jdbc工具类
 */
public class JdbcUtil {

    private static DataSource ds = null;


    //静态代码块，当此类的字节码文件被加载进虚拟机时，就会立刻执行此内的代码
    static {

        Properties properties = new Properties();
        try {
            //加载和读取properties文件
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            //根据德鲁伊连接池得到DataSources对象
            ds = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //创建并返回一个Connection对象
    public static Connection getConn() {
        try {
            //从连接池中得到连接对象
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //用于关闭连接对象的方法   避免在具体执行每次都要写一次close等等的trycatch
    public static void close(Connection conn, Statement sta, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        } finally {
            try {
                if (sta != null) {
                    sta.close();
                }
            } catch (Exception e) {
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
