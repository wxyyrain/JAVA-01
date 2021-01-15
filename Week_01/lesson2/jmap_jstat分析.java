jmap -heap 查看堆内存信息：

// 使用tlab
using thread-local object allocation.
// jdk8默认使用Parallel回收器，gc threads = cpu核心数，参数-XX:ParallelGCThreads=N修改；默认-XX:+UseAdaptiveSizePolicy，动态调节新生代、endn、survivor、晋升老年代对象年龄大小等参数；参数-XX:GCTimeRatio设置吞吐量
Parallel GC with 4 thread(s)
// CMS回收器-XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction设置老年代被使用多少触发垃圾回收（默认68%）；-XX:+UseCMSCompactAtFullCollection垃圾回收之后进行内存整理；-XX:CMSFullGCsBeforeCompaction多少次fullgc后内存整理
// gc threads = (ParallelGCThreads + 3)/4
// G1回收器

Heap Configuration:
   // 空闲堆的最小百分比，默认40，自适应会影响该值。HeapFreeRatio小于该值会扩容
   MinHeapFreeRatio         = 0
   // 空闲堆的最大百分比，默认70，自适应会影响该值。HeapFreeRatio大于该值会缩容
   MaxHeapFreeRatio         = 100
   // 堆空间最大值，通过-Xmx设置(服务器内存的60%-80%)，默认服务器内存的四分之一（服务器内存大于1G）
   MaxHeapSize              = 2126512128 (2028.0MB)
   // 年轻代当前值，通过-Xmn配置
   NewSize                  = 44564480 (42.5MB)
   // 年轻代最大值，与年老代默认比例1:2
   MaxNewSize               = 708837376 (676.0MB)
   // 年老代当前值
   OldSize                  = 89653248 (85.5MB)
   // 表示新生代：年老代 = 1 ：2，通过-XX:NewRatio设置
   NewRatio                 = 2
   // 表示eden : survivor = 8 : 1，通过-XX:SurvivorRatio配置
   SurvivorRatio            = 8
   // 元空间当前值，通过-XX:MetaspaceSize设置
   MetaspaceSize            = 21807104 (20.796875MB)
   // 压缩空间
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   // 元空间最大值,通过-XX:MaxMetaspaceSize设置
   MaxMetaspaceSize         = 17592186044415 MB
   // 使用G1收集器时候每个region的值
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
// 年轻代
PS Young Generation
// eden区，关闭自适应后，eden:s0:s1很多时候是6:1:1，因为Ratio是最大容量时候的比例
Eden Space:
   capacity = 140509184 (134.0MB)
   used     = 11792048 (11.245773315429688MB)
   free     = 128717136 (122.75422668457031MB)
   8.39236814584305% used
// s0区
From Space:
   capacity = 11010048 (10.5MB)
   used     = 0 (0.0MB)
   free     = 11010048 (10.5MB)
   0.0% used
// s1区
To Space:
   capacity = 10485760 (10.0MB)
   used     = 0 (0.0MB)
   free     = 10485760 (10.0MB)
   0.0% used
// 年老代
PS Old Generation
   capacity = 93323264 (89.0MB)
   used     = 19903616 (18.9815673828125MB)
   free     = 73419648 (70.0184326171875MB)
   21.32760380091292% used

jmap -histo 查看对象信息：
// B 代表 byte
// C 代表 char
// D 代表 double
// F 代表 float
// I 代表 int
// J 代表 long
// Z 代表 boolean
// 前边有 [ 代表数组， [I 就相当于 int[]
		 // 实例数       // 所占内存大小  // 类名
 num     #instances         #bytes  class name
----------------------------------------------
   1:        561022      141385672  [C
   2:         32441       21177480  [I
   3:         16546       13054792  [B
   4:        326362        7832688  java.lang.String
   5:         39433        3470104  java.lang.reflect.Method
   6:         44406        2841984  java.net.URL
   7:         37889        2638344  [Ljava.lang.Object;
   8:        108043        2203240  [Ljava.lang.Class;
   9:         19363        1545800  [S
  10:         40567        1298144  org.springframework.boot.loader.jar.StringSequence
  11:         12309        1161032  [Ljava.util.HashMap$Node;
  12:         31559        1009888  java.util.HashMap$Node
  13:         40567         973608  org.springframework.boot.loader.jar.JarURLConnection$JarEntryName
  14:          7121         790624  java.lang.Class
  15:         20589         658848  java.util.concurrent.ConcurrentHashMap$Node
  16:         10968         513040  [Ljava.lang.reflect.Method;
  17:         20822         499728  java.lang.StringBuilder
  18:          7655         489920  org.springframework.asm.Label
  19:          9952         477696  java.util.HashMap
  20:         11522         460880  java.util.LinkedHashMap$Entry
  21:         12801         409632  java.lang.ref.WeakReference
  22:         17883         385096  [Ljava.lang.reflect.Type;
  23:          6527         365512  org.springframework.asm.Item
  24:          6967         334416  org.springframework.asm.Frame
  25:          5476         306656  java.util.LinkedHashMap
  26:         11484         275616  java.util.ArrayList
  27:         17108         273728  java.lang.Object
  28:          5696         273408  org.springframework.core.ResolvableType
  29:          3004         240320  org.springframework.boot.loader.jar.JarURLConnection
  
  
 jstat -gc:
 // s0容量     // s0使用       // eden容量       // old容量     // meta容量   // CompressedClass容量 // young gc时时长 // fullgc次数  // gc时长
  S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
34048.0 34048.0 23052.6  0.0   272640.0 188888.1  707840.0     0.0     32916.0 31373.0 4388.0 4010.3      2    0.083   2      0.064    0.148

	
