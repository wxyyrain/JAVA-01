## 字节码文件

* Java文件都会编译成字节码文件（.class），运行时，jvm会将代码逐行解释为机器代码，不会生中间文件；同时，hotspot也提供了JIT编译器，会把一些字节码文件编译为机器代码直接运行，因此，Java既是解释执行，也有编译执行。
* 字节码：由单字节（byte）的指令组成，理论上最多支持256（八位，0-255）个操作码。
* 指令分类：
    * 栈操作指令，如iconst_1、istore_1
    * 程序流程控制指令，如if_icmpne、goto
    * 对象操作指令，包括方法调用指令，如invokestatic、invokevirtual
    * 算术运算以及类型转换指令，如isub、imul
## 类加载

* 类的生命周期
    * 加载_loading

获取类的二进制字节流，在方法区生成class对象；可以从包中读取，可以从网络中获取，也可以运行时计算生成

    * 验证_verification

确保字节流内的信息符合虚拟机的要求

    * 准备_preparation

为静态变量分配内存并且设置初始值

    * 解析_resolution

符号解析为引用

    * 初始化_initialization

执行静态代码块，静态变量赋值

    * 使用_using
    * 卸载_unloading
* 类初始化时机
    * 主动引用
        * new、getstaic、putstatic、invokestatic指令时
        * 使用反射时
        * 子类初始化会触发父类初始化
        * 虚拟机启动时，会执行主类
        * 初次调用MethodHandle实例，初始化该methodHandle指向方法所在的实例
    * 被动引用：所有引用类的方式都不会触发初始化，称为被动引用
        * 通过子类引用父类的静态字段，不会导致子类初始化。
        * 通过数组定义来引用类，不会触发此类的初始化
        * 常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
## 类加载器

* 启动类加载器

无法被直接引用，负责加载<JAVA_HOME>/lib目录中且虚拟机识别的类库

* 扩展类加载器

可以被引用，负责加载<JAVA_HOME>/lib/ext目录下的类库

* 应用类加载器

开发者可以直接使用，加载classpath的类库

* 特点
    * 双亲委派
    * 负责依赖
    * 缓存加载
## JMM

* 内存模型

特定的内存协议下，对特定的内存或高速缓存进行读写访问的过程抽象

* java内存模型
    * 主内存和工作内存
    * 线程对变量的操作只能在工作内存；线程之间不能互相访问对方的工作内存
    * 主内存与工作内存交互：lock、unlock、read、load、user、assign、store、write
    * 特征：
        * 原子性，read、load等是原子操作，synchronized可以保证原子性
        * 可见性，volatile、final、synchronized可以保证可见性
        * 有序性，volatile、synchronized可以保证有序性
    * heppens-before：程序次序规则、管程锁定规则、volatile变量规则、线程启动规则、线程终止规则、线程中断规则、对象终结规则、传递性
## 内存分布

* JVM整体内存结构

![图片](https://uploader.shimo.im/f/8LxvwR0BMghpVQUB.png!thumbnail?fileGuid=jQYgtVq6jQPtDYt6)


* JVM栈内存结构

![图片](https://uploader.shimo.im/f/cE5oTHpxMCqiXu2k.png!thumbnail?fileGuid=jQYgtVq6jQPtDYt6)


* JVM堆内存结构

![图片](https://uploader.shimo.im/f/PZ4rtEflKyCyFHas.png!thumbnail?fileGuid=jQYgtVq6jQPtDYt6)

## JVM启动参数

* 系统属性参数

-Dfile.encoding=UTF-8

-Duser.timezone=GMT+08

-Dmaven.test.skip=true

-Dio.netty.eventLoopThreads=8

* 运行模式参数

-server：设置 JVM 使用 server 模式，特点是启动速度比较慢，但运行时性能和内存管理效率很高，适用于生产环境。在具有 64 位能力的 JDK 环境下将默认启用该模式，而忽略 -client 参数。

-client ：JDK1.7 之前在32位的 x86 机器上的默认值是 -client 选项。设置 JVM 使用 client 模式，特点是启动速度比较快，但运行时性能和内存管理效率不高，通常用于客户端应用程序或者 PC 应用开发和调试。此外，我们知道 JVM 加载字节码后，可以解释执行，也可以编译成本地代码再执行，所以可以配置 JVM 对字节码的处理模式。

-Xint：在解释模式（interpreted mode）下运行，-Xint 标记会强制 JVM 解释执行所有的字节码，这当然会降低运行速度，通常低10倍或更多。

-Xcomp：-Xcomp 参数与-Xint 正好相反，JVM 在第一次使用时会把所有的字节码编译成本地代码，从而带来最大程度的优化。

-Xmixed：-Xmixed 是混合模式，将解释模式和编译模式进行混合使用，有 JVM 自己决定，这是 JVM 的默认模式，也是推荐模式。 我们使用 java -version 可以看到 mixed mode 等信息。

* 堆内存设置参数
* GC设置参数

-XX：+UseG1GC：使用 G1 垃圾回收器

-XX：+UseConcMarkSweepGC：使用 CMS 垃圾回收器

-XX：+UseSerialGC：使用串行垃圾回收器

-XX：+UseParallelGC：使用并行垃圾回收器

// Java 11+

-XX：+UnlockExperimentalVMOptions -XX:+UseZGC

// Java 12+

-XX：+UnlockExperimentalVMOptions -XX:+UseShenandoahGC

* 分析诊断参数
* JavaAgent参数
## jdk内置工具

* 命令行工具
    * jps，查看java进程
    * jstat，查看jvm内部gc相关信息
    * jmap，查看heap或类占用空间统计
    * jstack，查看线程信息
    * jcmd，整合命令
* 图形化工具：主要关注堆、CPU占用率（线程数、加载的类的数据会趋于平稳）
    * jconsole，远程通过JMX方式连接
    * jvisiualvm
    * visualGC，idea 插件
    * jmc
## GC算法

* 判断对象死亡
    * 引用计数法
        * 缺点：无法回收相互引用的对象
    * 可达性分析算法
        * 根据gcroot对象判断是否可达
        * 常见gcroot：
            * 当前正在执行方法的局部变量和输入参数
            * 活动线程
            * 静态字段
            * JNI引用
* 垃圾收集算法
    * 标记清除
        * 缺点：空间碎片
    * 复制
        * 只能使用一半的内存，代价大
    * 标记整理
    * 分代：young gen和old gen
        * 前提：大部分对象朝生夕死，存活较长时间的对象，可能会存活更久
        * 算法：年轻代使用复制算法；老年代使用标记清除算法
        * 策略：对象优先在eden分配；大对象直接进入老年代；长期存活的对象进入老年代；动态对象年龄判定；空间分配担保
## GC实现

* 串行
    * serial
        * 年轻代收集器
    * serial old
        * 年老代收集器
    * 总结：serial和serial old搭配使用，适合单cpu小内存（几百兆）的机器，默认client场景下的收集器
* 并行
    * parNew
        * serial的多线程版本
        * 除了serial外，能和CMS搭配使用的收集器
    * parallel
        * 吞吐量优先的垃圾收集器，可以调节吞吐量和停顿时间
        * 有自适应策略
        * 适合后台运算不需要太多交互的程序
* 并发
    * CMS
        * 步骤：
            * 初始标记：标记gc roots，会stw
            * 并发标记：gc roots tracing
            * 并发预清理：标记脏区
            * 最终标记：完成所有存活对象的标记，会stw
            * 并发清除：删除不再使用的对象
            * 并发重置
        * 优点：降低多核CPU的GC停顿导致的系统延迟
        * 缺点：
            * 无法处理浮动垃圾，默认68的时候触发垃圾回收，如果concurrent mode failure会使用serial old收集器导致停顿时间很长
            * 会产生空间碎片
            * CPU资源敏感，总吞吐降低
    * G1
        * CMS的升级版
        * 整个内存区划分为2048个堆区域，每个区域是eden或是s或是old是动态变化的
        * 增量垃圾回收，每次处理一部分。垃圾最多的小块会被优先收集
        * 将STW停顿时间和分布变成可预期的
        * 某些fullgc情况退化到serial，STW时间到秒级别
            * 并发模式失败
            * 晋升失败
            * 巨型对象分配失败
## GC如何选择

* 吞吐量优先，CPU资源最大程度处理业务，使用parallel gc
* 低延迟优先，使用CMS
* 系统内存较大（4g以上），同时希望GC时间可控，使用G1
