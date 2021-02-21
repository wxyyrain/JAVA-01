package org.example;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchUsage extends JdbcUsage {

    @Override
    protected void add() throws SQLException {
//        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `test`.`cat` ( `age`, `name`) VALUES (?, ?)");
        for (int i = 1; i < 100; i++) {
            preparedStatement.setString(1, "10" + i);
            preparedStatement.setString(2, "mike" + i);
            preparedStatement.addBatch();
        }
        preparedStatement.addBatch("update cat set `name` = 'little cat'");
        preparedStatement.executeBatch();
        preparedStatement.close();
//        connection.commit();
    }

    @Override
    protected void edit() throws SQLException {

    }

    @Override
    protected void query() throws SQLException {

    }

    @Override
    protected void delete() throws SQLException {

    }
}
