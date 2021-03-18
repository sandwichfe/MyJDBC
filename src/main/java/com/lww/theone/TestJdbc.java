package com.lww.theone;

import com.lww.theone.Bean.User;
import com.lww.theone.util.JdbcTemplate;
import com.lww.theone.util.JdbcUtil;
import com.lww.theone.util.handler.BeanHandler;
import com.lww.theone.util.handler.BeanListHandler;
import org.junit.Test;

import java.sql.*;
import java.util.List;

public class TestJdbc {

    /**
     * java与MySQL的连接
     *
     * @param args
     */
    Connection conn = null;

    public Connection getConnection() {
        try {
            conn = (Connection) JdbcUtil.getConn();
            System.out.println("已成功的与数据库MySQL建立连接！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //显示数据库中的表
    public ResultSet listDB() {
        String sql = "show tables;";
        try {
            conn = getConnection();
            Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        TestJdbc testJdbc = new TestJdbc();
        ResultSet rest = testJdbc.listDB();
        System.out.println("数据库company中有下列数据表：");
        try {
            while (rest.next()) {
                System.out.println(rest.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void insert() {
        User user = new User(99, "fdf", "fdsfsdf");
        JdbcTemplate.update("insert into user (userid,username,pwd) values (?,?,?) ", user.getUserid(), user.getUserName(), user.getPwd());
    }

    @Test
    public void update() {
        System.out.println(JdbcTemplate.update("update user set username= ? where userid = ?", "newName", 99));
    }

    @Test
    public void delete() {
        System.out.println(JdbcTemplate.update("delete from user where userid=?", 2));
    }

    @Test
    public void get() {
        User user = (User) JdbcTemplate.query("select * from user where userid= ? ", new BeanHandler<>(User.class), 99);
        System.out.println(user);
    }

    @Test
    public void listAll() {
        List<User> list = JdbcTemplate.query("select * from user ", new BeanListHandler<>(User.class));
        System.out.println(list);
    }


}
