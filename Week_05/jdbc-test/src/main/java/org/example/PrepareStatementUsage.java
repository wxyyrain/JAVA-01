package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PreparedStatement可以使用占位符,它是由占位符标识需要输入数据的位置，然后再逐一填入数据。
 * 当然，PreparedStatement也可以执行没有占位符的sql语句
 */
public class PrepareStatementUsage extends JdbcUsage {

    @Override
    protected void add() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `test`.`cat` ( `age`, `name`) VALUES (?, ?)");
        preparedStatement.setString(1, "10");
        preparedStatement.setString(2, "mike");
        int add = preparedStatement.executeUpdate();
        System.out.println("插入" + add + "条数据");
        preparedStatement.close();
    }

    @Override
    protected void edit() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update cat set `name` = ? where age = ?");
        preparedStatement.setString(1, "little cat");
        preparedStatement.setString(2, "10");
        int update = preparedStatement.executeUpdate();
        System.out.println("更新" + update + "条数据");
        preparedStatement.close();
    }

    @Override
    protected void query() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from cat where name = ?");
        preparedStatement.setString(1, "little cat");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + "---" + resultSet.getString(3));
        }
        preparedStatement.close();
        resultSet.close();
    }

    @Override
    protected void delete() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from cat where name = ?");
        preparedStatement.setString(1, "little cat");
        int del = preparedStatement.executeUpdate();
        System.out.println("删除" + del + "条数据");
        preparedStatement.close();
    }
}
