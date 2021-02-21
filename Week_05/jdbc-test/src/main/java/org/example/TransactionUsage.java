package org.example;

import java.sql.*;

public class TransactionUsage extends JdbcUsage {

    private PrepareStatementUsage prepareStatementUsage = new PrepareStatementUsage();

    /**
     * 另一个连接
     */
    protected static Connection other;

    static {
        try {
            other = DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void add() throws SQLException {
        connection.setAutoCommit(false);
        try {
            prepareStatementUsage.add();
            int a = 1 / 0;
            prepareStatementUsage.add();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        // 如果catch没有rollback会导致下面的edit无法执行，因为rollback回滚事务，释放锁
        edit();
    }

    @Override
    protected void edit() throws SQLException {
        Statement statement = other.createStatement();
        int update = statement.executeUpdate("update cat set `name` = 'little cat'");
        System.out.println("修改" + update + "条数据");
        statement.close();
    }

    @Override
    protected void query() throws SQLException {

    }

    @Override
    protected void delete() throws SQLException {

    }
}
