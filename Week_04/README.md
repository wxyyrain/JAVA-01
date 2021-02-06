## JUC

* JDK核心库的包
    * java.lang.*
        * 最基础，Integer/String
    * java.io.*
        * IO读写，文件操作
    * java.util.*
        * 工具类，集合/日期
    * java.math.*
        * 数学计算，BigInteger
    * java.net.*
        * 网络编程，socket
    * java.rmi.*
        * Java内置的远程调用
    * java.sql.*
        * JDBC操作数据库
    * javax.*
        * Java扩展API
    * sun.*
        * sun的jdk实现包
* java.util.concurrency
    * synchronized/wait _锁
        * 锁机制类：Lock、Condition、ReadWriteLock
    * sum++多线程安全 _原子类
        * 原子操作类Atomic：AtomicInteger
    * new Thread()管理 _线程池
        * 线程池相关类：Future、Callable、Executor
    * 线程间协作信号量 _工具类
        * 信号量三组工具类：CountDownLatch、CyclicBarrier、Semaphore
    * 线程安全集合类 _集合类
        * 并发集合类：CopyOnWriteArrayList、ConcurrentMap
## 锁

* Lock、Condition、ReadWriteLock
    * 为什么需要显式的锁
        * 同步块的阻塞无法中断
        * 同步块的阻塞无法控制超时
        * 同步块无法异步处理锁（即不能立即知道是否可以拿到锁）
        * 同步块无法根据条件灵活解锁加锁（即只能跟同步块范围一致）
    * 更自由的锁：Lock
        * 使用方式灵活
        * 性能开销小
    * Lock接口设计
        * 支持中断的API：void lockInterruptibly() throws InterruptedException;
        * 支持超时的API：boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
        * 支持非阻塞获取锁的API：boolean tryLock();
        * 多条件：Condition newCondition();
    * 可重入锁
        * 第二次进入时是否阻塞
    * 公平锁
        * 公平锁：排队靠前的优先
        * 非公平锁：都是同样机会
    * 读写锁：ReentrantReadWriteLock
        * Lock readLock(); 读锁，共享锁
        * Lock writeLock(); 写锁，独占锁
    * Condition
        * 方法
            * void await() throws InterruptedException;
            * void awaitUninterruptibly();
            * boolean await(long time, TimeUnit unit) throws InterruptedException;
            * boolean awaitUntil(Date deadline) throws InterruptedException;
            * void signal();
            * void signalAll();
        * 可以看做Lock对象上的信号，类似于wait/notify
    * LockSupport：锁当前线程
    * 用锁的最佳实践
        * 永远只在更新对象的成员变量时加锁
        * 永远只在访问可变的成员变量时加锁
        * 永远不在调用其他对象的方法时加锁
    * 最小使用锁
        * 降低锁范围：锁定代码的范围/作用域
        * 细分锁粒度：将一个大锁，拆分成多个小锁
## 并发原子类

* AtomicInteger
    * 实现
        * volatile保证读写操作都可见
        * 使用CAS指令，作为乐观锁实现，通过自选重试保证写入
* 并发压力跟锁性能的关系
    * 压力非常小，性能本身要求就不高
    * 压力一般的情况下，无锁更快，大部分都一次写入
    * 压力非常大时，自旋导致重试过多，资源消耗很大
* AtomicLong的改进
    * 将单个value拆分成跟线程一样多的数组Cell[]
    * 每个线程写自己的Cell[i]++，最后对数组求和
## 并发工具类

* AQS：JUC中的核心基础组件
    * 实现：state + CLH队列
* Semaphore-信号量
    * 场景：同一时间控制并发线程数
* CountDownLatch
    * 场景：master等待woker把任务执行完
* CyclicBarrier
    * 场景：任务执行到一定阶段，等待其他任务对齐
* Future
* FutureTask
* CompletableFuture
## 常用线程安全类型

* List线程安全的简单办法
    * Vector
    * Collections.synchronizedList
    * Arrays.asList 不允许添加删除，允许set替换元素
    * Collections.unmodifiableList，不允许修改内容，包括添加删除和set
* CopyOnWriteArrayList
    * 线程安全原理：读写分离，最终一致
        * 写加锁，保证不会写混乱
        * 写在一个copy副本上，而不是原始数据上
    * api
        * add使用ReentrantLock加锁，拷贝原容器，且长度加一，之后将原容器引用指向新副本
        * remove使用ReentrantLock加锁，删除末尾元素，直接使用前N-1个元素创建一个新数组，删除其他位置元素，创建新数组，将剩余元素复制到新数组
        * get不加锁
        * 使用迭代器时候，直接拿当前数据对象做一个快照
* ConcurrentHashMap
    * JDK1.7
        * Segment（继承ReentrantLock）分段锁
            * 相当于，Segment[]分库，HashEntry[]分表
        * 根据哈希码高sshift位决定Segment数组的index
            * 目的是避免两次散列后的值一样，虽然元素在Segment里散列开了，但是却没有在HashEntry里散列开
    * JDK1.8
        * cas +synchronized
## 并发编程相关内容

* ThreadLocal
    * 线程本地变量
    * 场景：每个线程一个副本
    * 既是进行清理
    * Context模式，减少显示传递参数
* 并行stream
* 伪并发问题
    * 表单重复提交
        * 前端控制：点击后按钮不可用，跳转到其他页
        * 后端控制：给每个表单生成一个编号，提交时判断重复
## 并发编程经验总结

* 加锁考虑的问题
    * 粒度
    * 性能
    * 重入
    * 公平
    * 自旋锁
    * 场景
* 线程间协作与通信
    * 线程间共享
        * static变量
        * Lock
        * synchronized
    * 线程间协作
        * Thread.join
        * object.wait/notify/notifyAll
        * Futrure/Callable
        * CountDownLatch
        * CyclicBarrier

