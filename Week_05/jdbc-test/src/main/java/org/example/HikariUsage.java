package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class HikariUsage {

    private static final HikariDataSource ds;

    static {
        HikariConfig conf = new HikariConfig();
        conf.setUsername("root");
        conf.setPassword("123");
        conf.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        ds = new HikariDataSource(conf);
    }

    protected void add() throws SQLException {
        Statement statement = ds.getConnection().createStatement();
        int add = statement.executeUpdate("INSERT INTO `test`.`cat` ( `age`, `name`) VALUES ('2', 'mike')");
        System.out.println("插入" + add + "条数据");
        statement.close();
    }

    protected void edit() throws SQLException {
        Statement statement = ds.getConnection().createStatement();
        int update = statement.executeUpdate("update cat set `name` = 'little cat'");
        System.out.println("修改" + update + "条数据");
        statement.close();
    }

    protected void query() throws SQLException {
        Statement statement = ds.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from cat ");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + "---" + resultSet.getString(3));
        }
        statement.close();
        resultSet.close();
    }

    protected void delete() throws SQLException {
        Statement statement = ds.getConnection().createStatement();
        int del = statement.executeUpdate("delete from cat ");
        System.out.println("删除" + del + "条数据");
        statement.close();
    }

    public void execute() throws SQLException {
        add();
        edit();
        query();
        delete();
    }

}
