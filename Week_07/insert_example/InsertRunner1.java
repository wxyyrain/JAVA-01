package org.example.insert;

import org.springframework.util.StopWatch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class InsertRunner1 {

    /**
     * rewriteBatchedStatements=true参数决定是否多值合并插入
     */
    protected static final String url = "jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true";

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

    /**
     * 单条记录插入，使用PreparedStatement插入
     *
     * @throws SQLException
     */
    public void method1() throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `test`.`ds_order_info` " +
                "(`orderno`, `total_amount`, `pay_amount`, `u_id`, `state`, `pay_time`, `end_time`, `createtime`, " +
                "`updatetime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (int i = 0; i < 1000000; i++) {
            int j = 1000000 - i;
            preparedStatement.setString(1, String.valueOf(j));
            preparedStatement.setDouble(2, 0D);
            preparedStatement.setDouble(3, 0D);
            preparedStatement.setLong(4, 0L);
            preparedStatement.setInt(5, 0);
            preparedStatement.setLong(6, 0L);
            preparedStatement.setLong(7, 0L);
            preparedStatement.setLong(8, 0L);
            preparedStatement.setLong(9, 0L);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.commit();
    }

    /**
     * 先生存一个文件runoob.txt，再使用load data把数据导入
     */
    public void method2() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("runoob.txt"));
        for (int i = 0; i < 1000000; i++) {
            out.write(i + "|0.00|0.00|0|0|0|0|0|0");
            out.newLine();
        }
        out.close();
//        load data local infile 'E:/idea_workspace/jdbc-test/runoob.txt' into table ds_order_info
//        CHARACTER SET utf8 -- 可选，避免中文乱码问题
//        FIELDS TERMINATED BY '|' -- 字段分隔符，每个字段(列)以什么字符分隔，默认是 \t
//        OPTIONALLY ENCLOSED BY '' -- 文本限定符，每个字段被什么字符包围，默认是空字符
//        ESCAPED BY '\\' -- 转义符，默认是 \
//        LINES TERMINATED BY '\n' -- 记录分隔符，如字段本身也含\n，那么应先去除，否则load data 会误将其视作另一行记录进行导入
//        (orderno, total_amount, pay_amount, u_id, state, pay_time, end_time, createtime, updatetime) -- 每一行文本按顺序对应的表字段，建议不要省略
    }

    public static void main(String[] args) throws SQLException {
        StopWatch stopWatch = new StopWatch("insert time");
        InsertRunner1 insertRunner1 = new InsertRunner1();
        stopWatch.start();
        insertRunner1.method1();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
