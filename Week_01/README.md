java 命令行工具
	java
	javac
	javap
	javadoc
	javah
	
	jstat -gc 看gc情况
	jmap -heap 打印堆内存的配置和使用信息 -histo 看哪些类占用的空间最多 
	jstack 看堆栈
	jcmd 综合xing命令
	
	
java 可视化工具(linux是否能用？)
	jconsole 
		主要关注堆、CPU占用率（线程数、加载的类的数据会趋于平稳）
	jvisualvm
	visualgc	idea 插件
	jmc	

GC
	引用计数
		优点：简单有效；缺点：无法解决互相引用的问题，导致内存泄漏
		改进：gcroot（1.当前正在执行方法的局部变量和输入参数 2,活动线程 3.静态字段 4.JNI引用）可达
		
		整理算法：
			并行GC和CMS基本原理
			可以处理依赖循环，只扫描部分对象
			清除后需要压缩，防止过多内存碎片
			标记和清除的时候需要stop the world
			老年代使用的算法
		分代：
			前提：假设大部分新生对象很快无用；存活较长时间的对象，可能会存活更久
			内存划分：young-gen(eden-space s0 s1) old-gen，不同类型对象不同区域，不同区域不同处理策略
			对象转移过程：eden-》s0/s1；s0/s1-》old （参数空值，默认15次）
		复制算法：
			年轻代使用的算法
		标记清除算法：
			老年代使用的算法
	串行GC
		serial
			-XX:+UseSerialGC
			对young使用复制算法，对old使用标记清除算法
			单线程，会STW
			无法利用多核CPU，很少使用
			CPU利用率高，暂停时间长，适合几百MB的堆内存
		par new
			serial的改进
	并行GC
		-XX:+UseParallelGC -XX:+UseParallelOldGC
		对young使用复制算法，对old使用标记清除算法
		-XX:ParallelGCThreads=N 执行gc线程数，默认为CPU核心数
		适合多核服务器，主要目标增加吞吐量
		STW时间短，非GC的时候，没有GC线程运行
		
	java8默认并行GC
	
	CMS GC mostly concurrent mark and sweep
		年轻代 STW方式的标记-复制算法，老年代并发（gc和业务同时运行）标记-清除
		避免fullgc出现长时间的STW，通过：1.不对老年代进行整理，使用空闲列表管理内存空间的回收 2.在标记清除阶段大部分工作和应用线程一起并发执行，默认使用CPU四分之一的核心
		可能导致碎片多
		目的：降低多核CPU的GC停顿导致的系统延迟
		阶段：
			1.初始标记（会有STW，标记root对象） 
			2.并发标记（遍历老年代，标记存活对象） 
			3.并发预处理 （引用变了的话标记位脏区）
			4.最终标记 （会STW，完成所有存活对象的标记）
			5.并发清除 
			6.并发重置
			
	G1 CMS升级版
		整个内存区划分为2048个堆区域，每个区域是eden或是s或是old是动态变化的。增量垃圾回收，每次处理一部分。垃圾最多的小块会被优先收集。
		将STW停顿时间和分布变成可预期的，
		-XX:useG1GC -XX:MaxGCPauseMills = 50 初始年轻代占比 最大年轻代占比 每个region大小 与java应用一起运行的gc线程数 开始gc的阈值 暂停回收的内存大小（默认5%） java应用和gc时间比率（默认9）  gc停顿时间
		处理步骤：
			年轻代模式转移暂停：通过历史运行情况调整策略
			并发标记
			转移暂停
		注意：
			某些fullgc情况退化到serial，STW时间到秒级别
				并发模式失败
				晋升失败
				巨型对象分配失败
				
	GC如何选择
		吞吐量优先，CPU资源最大程度处理业务，使用parallel gc
		低延迟优先，使用CMS
		系统内存较大（4g以上），同时希望GC时间可控，使用G1
		
	ZGC
		STW时间不超过10ms，内存支持范围广，几百兆~4TB，与G1相比，吞吐量下降不超过15%，只支持linux64
	shennandoahGC
		
		
		
		
		
	
	gc停顿时间和吞吐量的思考
	
	adapter动态配置 -XX:-UseAdaptiveSizePolicy
	
	
	CMS比parallel的新生代内存少：
	
	
	你家存款有1024w（Xmx-单位m），你的CFO（GC）规定old老婆和young你占有比例2:1，所以你老婆占有的（最大值）是683w（old区最大值），你是341w（max new size），用jmap -heap去看的时候，就是你日常找你老婆申请零花钱，虽然你名义上有341w，但是你老婆看你一天也就抽包烟两顿饭，只给了你8k，这个8k就是young区的初始容量（远远不到新生代最大值341m）。注意，CFO一开始就可能给了你老婆100w。

作为一个节俭的young男人，你把约摸着，你所有的钱，计划分三份用途（eden理财、S0吃饭、S1穿衣服），花的比例应用是8:1:1。

这会儿你手上钱不多，，先得生存（survivor），估计得吃饭占大头，所以实际上你预计吃饭可能花多点1100(S0容量)，穿少点900(S1容量)，还有6000(eden容量)可以买理财，这时候你没法保证真正花的钱比例8:1:1，对吧，差不多6:1:1，但是你基本可以肯定，要是一下子把341w都给你，你就按这个比例来花。

实际上，你可能今天买理财钱花了一半，吃饭钱1100实际上只用了300，买衣服基金没动。
这样查看你的账户，
EC  6000
EU  3000
S0C 1100
S0U 300
S1C 900
S1   0


-XX:+UseSerialGC  
-XX:+UseParallelGC
-XX:+UseConcMarkSweepGC 
-XX:+UseG1GC -XX:MaxGCPauseMillis=50

	
	
		
		
		
			
		