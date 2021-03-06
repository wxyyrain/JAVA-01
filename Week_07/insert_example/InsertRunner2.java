package org.example.insert;

import org.springframework.util.StopWatch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRunner2 {


    /**
     * 先生存一个文件runoob.txt，再使用load data把数据导入
     */
    public void method1() throws IOException {
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

    public static void main(String[] args) throws IOException {
        InsertRunner2 insertRunner = new InsertRunner2();
        insertRunner.method1();
    }
}
