#### GC日志
       5.617（时间戳）: [GC（Young GC） 5.617（时间戳）: [ParNew（使用ParNew作为年轻代的垃圾回收期）: 43296K（年轻代垃圾回收前的大小）->7006K（年轻代垃圾回收以后的大小）(47808K)（年轻代的总大小）, 0.0136826 secs（回收时间）] 44992K（堆区垃圾回收前的大小）->8702K（堆区垃圾回收后的大小）(252608K)（堆区总大小）, 0.0137904 secs（回收时间）] [Times: user=0.03（Young GC用户耗时） sys=0.00（Young GC系统耗时）, real=0.02 secs（Young GC实际耗时）]
#### FullGC:
    1.HotSpot自动选择和调优引发的FullGC( Ergonomics)
    2.元空间引发的FullGC (Metadata GC Threshold)
    
#### 常用配置
    -Xms20m
    -Xmx20m
    -Xmn10M
    -XX:SurvivorRatio=8
    -XX:NewRatio=8
    -XX:MetaspaceSize=2m
    -XX:MaxMetaspaceSize=8m
                     
    -XX:+HeapDumpOnOutOfMemoryError
    -XX:HeapDumpPath=data/gc/java_pidxxx.hprof
                    
    -XX:-PrintGC
    -XX:+PrintGCDetails
    -XX:+PrintGCTimeStamps
    -Xloggc:/opt/webserver/bsjd/gc.log
   
 
### 1.8 jvm参数说明

    -Xmx3550m：设置JVM最大堆内存为3550M。
    -Xms3550m：设置JVM初始堆内存为3550M。此值可以设置与-Xmx相同，以避免每次垃圾回收完成后JVM重新分配内存。
    -Xmn2g：设置年轻代大小为2G。在整个堆内存大小确定的情况下，增大年轻代将会减小年老代，反之亦然。此值关系到JVM垃圾回收，对系统性能影响较大，官方推荐配置为整个堆大小的3/8。
    -Xss128k：设置每个线程的栈大小。JDK5.0以后每个线程栈大小为1M，之前每个线程栈大小为256K。应当根据应用的线程所需内存大小进行调整。在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在3000~5000左右。需要注意的是：当这个值被设置的较大（例如>2MB）时将会在很大程度上降低系统的性能。
                
    -XX:MaxTenuringThreshold=7 表示一个对象如果在救助空间移动7次还没有被回收就放入年老代，
    -XX:GCTimeRatio=19 表示java可以用5%的时间来做垃圾回收，1/（1+19）=1/20=5%.
    -XX:SurvivorRatio=4 设置堆内存年轻代中 Eden：Survivor（中的一个）= 4，则两个Survivor区与一个Eden区的比值为2:4，一个Survivor区占整个年轻代的1/6。
    -XX:NewRatio=4 设置年轻代: 年老代=1:4。
    
    -XX:NewSize=1024m：设置年轻代初始值为1024M。
    -XX:MaxNewSize=1024m：设置年轻代最大值为1024M。
    
    -XX:MetaspaceSize，初始空间大小，达到该值就会触发垃圾收集进行类型卸载，同时GC会对该值进行调整：如果释放了大量的空间，就适当降低该值；如果释放了很少的空间，那么在不超过MaxMetaspaceSize时，适当提高该值。
    -XX:MaxMetaspaceSize，最大空间，默认是没有限制的。
    除了上面两个指定大小的选项以外，还有两个与 GC 相关的属性：
    -XX:MinMetaspaceFreeRatio，在GC之后，最小的Metaspace剩余空间容量的百分比，减少为分配空间所导致的垃圾收集
    -XX:MaxMetaspaceFreeRatio，在GC之后，最大的Metaspace剩余空间容量的百分比，减少为释放空间所导致的垃圾收集
    
    -XX:-PrintGC：                           每次GC时打印相关信息
    -XX:+PrintGCApplicationStoppedTime      打印垃圾回收期间程序暂停的时间
    -XX:+PrintGCDetails                    打印GC详细信息
    -XX:+PrintGCTimeStamps                 每次GC时输出GC的时间戳（以基准时间的形式）
    -Xloggc:D:/apache-tomcat-7.0.56/logs/gc.log  设置GC输入的文件地址
                
    -XX:-CITime：打印消耗在JIT编译的时间
    -XX:ErrorFile=./hs_err_pid.log：保存错误日志或者数据到指定文件中
    -XX:-ExtendedDTraceProbes：开启solaris特有的dtrace探针
    -XX:HeapDumpPath=./java_pid.hprof：指定导出堆信息时的路径或文件名
    -XX:-HeapDumpOnOutOfMemoryError：当首次遭遇内存溢出时导出此时堆中相关信息
    -XX:OnError=";"：出现致命ERROR之后运行自定义命令
    -XX:OnOutOfMemoryError=";"：当首次遭遇内存溢出时执行自定义命令
    -XX:-PrintClassHistogram：遇到Ctrl-Break后打印类实例的柱状信息，与jmap -histo功能相同
    -XX:-PrintConcurrentLocks：遇到Ctrl-Break后打印并发锁的相关信息，与jstack -l功能相同
    -XX:-PrintCommandLineFlags：打印在命令行中出现过的标记
    -XX:-PrintCompilation：当一个方法被编译时打印相关信息
                    
    -XX:-TraceClassLoading：跟踪类的加载信息
    -XX:-TraceClassLoadingPreorder：跟踪被引用到的所有类的加载信息
    -XX:-TraceClassResolution：跟踪常量池
    -XX:-TraceClassUnloading：跟踪类的卸载信息
    -XX:-TraceLoaderConstraints：跟踪类加载器约束的相关信息
                
   
                
##### 垃圾回收器选择
                
    JVM给出了3种选择：串行收集器、并行收集器、并发收集器。串行收集器只适用于小数据量的情况，所以生产环境的选择主要是并行收集器和并发收集器。
     设置串行收集器。
        -XX:+UseSerialGC：
     并行收集器（吞吐量优先,"指针碰撞"的方式分配内存）
        -XX:+UseParallelGC：设置为并行收集器。此配置仅对年轻代有效。即年轻代使用并行收集，而年老代仍使用串行收集。
        -XX:ParallelGCThreads=20：配置并行收集器的线程数，即：同时有多少个线程一起进行垃圾回收。此值建议配置与CPU数目相等。
        -XX:+UseParallelOldGC：配置年老代垃圾收集方式为并行收集。JDK6.0开始支持对年老代并行收集。
        -XX:MaxGCPauseMillis=100：设置每次年轻代垃圾回收的最长时间（单位
        -XX:+UseAdaptiveSizePolicy：设置此选项后，并行收集器会自动选择年轻代区大小和相应的Survivor区比例，以达到目标系统规定的最低响应时间或者收集频率等。
                
     并发收集器CMS(响应时间优先，"空闲列表"的方式分配内存)
        -XX:+UseParNewGC：设置年轻代为并发收集。可与CMS收集同时使用。JDK5.0以上，JVM会根据系统配置自行设置，所以无需再设置此值。     
        -XX:+UseConcMarkSweepGC：设置年老代为并发收集。测试中配置这个以后，-XX:NewRatio=4的配置失效了。所以，此时年轻代大小最好用-Xmn设置。
        -XX:CMSFullGCsBeforeCompaction=：由于并发收集器不对内存空间进行压缩、整理，所以运行一段时间以后会产生“碎片”，使得运行效率降低。此参数设置运行次FullGC以后对内存空间进行压缩、整理。
        -XX:+UseCMSCompactAtFullCollection：打开对年老代的压缩。可能会影响性能，但是可以消除内存碎片。
        -XX:+CMSIncrementalMode：设置为增量收集模式。一般适用于单CPU情况。
        -XX:CMSInitiatingOccupancyFraction=70：表示年老代空间到70%时就开始执行CMS，确保年老代有足够的空间接纳来自年轻代的对象。
          
     注：如果使用 throughput collector 和 concurrent low pause collector 这两种垃圾收集器，需要适当的挺高内存大小，为多线程做准备。
                
##### 其它
     -XX:+ScavengeBeforeFullGC：新生代GC优先于Full GC执行。
     -XX:-DisableExplicitGC：禁止调用System.gc()，但JVM的gc仍然有效。
     -XX:+MaxFDLimit：最大化文件描述符的数量限制。
     -XX:+UseThreadPriorities：启用本地线程优先级API，即使 java.lang.Thread.setPriority() 生效，反之无效。
     -XX:SoftRefLRUPolicyMSPerMB=0：“软引用”的对象在最后一次被访问后能存活0毫秒（默认为1秒）。
     -XX:TargetSurvivorRatio=90：允许90%的Survivor空间被占用（默认为50%）。提高对于Survivor的使用率——超过就会尝试垃圾回收。
     -XX:+UseCompressedOops: 对于64位系统有指针膨胀和数据类型对齐的原因，这个参数可以用来对象指针压缩功能
     -XX:+DoEscapeAnalysis：开启逃逸分析，优化前是在堆中，而优化后的是在栈中
                
##### 疑问解答
                
    -Xmn，-XX:NewSize/-XX:MaxNewSize，-XX:NewRatio 3组参数都可以影响年轻代的大小，混合使用的情况下，优先级是什么？
    如下：
       高优先级：-XX:NewSize/-XX:MaxNewSize
       中优先级：-Xmn（默认等效  -Xmn=-XX:NewSize=-XX:MaxNewSize=?）
       低优先级：-XX:NewRatio
       推荐使用-Xmn参数，原因是这个参数简洁，相当于一次设定 NewSize/MaxNewSIze，而且两者相等，适用于生产环境。-Xmn 配合 -Xms/-Xmx，即可将堆内存布局完成。-Xmn参数是在JDK 1.4 开始支持。                
                

                
                