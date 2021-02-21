package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class JdbcUsage {

    protected static final String url = "jdbc:mysql://localhost:3306/test";

    protected static final String username = "root";

    protected static final String password = "123";

    protected static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    protected abstract void add() throws SQLException;

    protected abstract void edit() throws SQLException;

    protected abstract void query() throws SQLException;

    protected abstract void delete() throws SQLException;

    public void execute() throws SQLException {
        add();
        edit();
        query();
        delete();
        connection.close();
    }
}
