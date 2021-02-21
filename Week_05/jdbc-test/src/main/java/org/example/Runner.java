package org.example;

import java.sql.SQLException;

public class Runner {

    public static void main(String[] args) throws SQLException {
//        JdbcUsage jdbcUsage = new StatementUsage();
//        JdbcUsage jdbcUsage = new PrepareStatementUsage();
//        JdbcUsage jdbcUsage = new TransactionUsage();
//        JdbcUsage jdbcUsage = new BatchUsage();
        HikariUsage jdbcUsage = new HikariUsage();
        jdbcUsage.execute();
    }
}
