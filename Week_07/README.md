## 第13节课作业实践
### 2、（必做）按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。
> 说明：订单表结构中，orderno字段是唯一索引。
#### 不同插入方式
- [单条记录插入](https://github.com/wxyyrain/JAVA-01/blob/main/Week_07/insert_example/InsertRunner1.java)使用jdbc preparedStatement addBatch方式，实际上就是在同一个事务中，提交所有insert语句
- [多值合并插入](https://github.com/wxyyrain/JAVA-01/blob/main/Week_07/insert_example/InsertRunner2.java)同样使用jdbc preparedStatement addBatch方式，但是使用了rewriteBatchedStatements=true参数，所以会使用insert ... values(),()...这种SQL语句，但是受到MySQL参数max_allowed_packet的限制，每一条SQL语句最大1M，所以在同一个事务中，会有多条这种语句，每一条插入大概不到2W的数据
- [MySQL Load Data](https://github.com/wxyyrain/JAVA-01/blob/main/Week_07/insert_example/InsertRunner2.java)是直接使用MySQL命令去加载固定格式的文件插入数据
  
| 方式 | 索引正序耗时 |
| ------ | ------ |
| 单条记录插入 | 134428ms |
| 多值合并插入 | 50955ms |
| MySQL Load Data | 39814ms |
---
#### 插入注意事项
- 在事务中进行插入：通过使用事务可以减少创建事务的消耗，所有插入都在执行后才进行提交操作
- 数据有序插入：数据库插入时，需要维护索引数据，无序的记录会增大维护索引的成本
- 多值合并插入：合并后日志量（MySQL的binlog和innodb的事务让日志）减少了，降低日志刷盘的数据量和频率，从而提高效率
  - 需要注意max_allowed_packet参数对SQL语句长度的限制
#### 思考
从结果上来看，Load Data速度最快，而且把数据写到文件中，可以比较容易的支持类似于“断点续传”功能。

## 第14节课作业实践

### 2、（必做）读写分离-动态切换数据源版本1.0
[AbstractRoutingDataSource实现](https://github.com/wxyyrain/JAVA-01/tree/main/Week_07/write-read-sep/v1)
### 3、（必做）读写分离-数据库框架版本2.0
[shardingsphere jdbc starter实现](https://github.com/wxyyrain/JAVA-01/tree/main/Week_07/write-read-sep/v1)
