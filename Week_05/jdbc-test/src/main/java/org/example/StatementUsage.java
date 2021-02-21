package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementUsage extends JdbcUsage {

    protected void add() throws SQLException {
        Statement statement = connection.createStatement();
        int add = statement.executeUpdate("INSERT INTO `test`.`cat` ( `age`, `name`) VALUES ('2', 'mike')");
        System.out.println("插入" + add + "条数据");
        statement.close();
    }

    protected void edit() throws SQLException {
        Statement statement = connection.createStatement();
        int update = statement.executeUpdate("update cat set `name` = 'little cat'");
        System.out.println("修改" + update + "条数据");
        statement.close();
    }

    protected void query() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from cat ");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + "---" + resultSet.getString(3));
        }
        statement.close();
        resultSet.close();
    }

    protected void delete() throws SQLException {
        Statement statement = connection.createStatement();
        int del = statement.executeUpdate("delete from cat ");
        System.out.println("删除" + del + "条数据");
        statement.close();
    }
}
