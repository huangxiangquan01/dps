# BlockingQueue

### 概要
BlockingQueue，是java.util.concurrent 包提供的用于解决并发生产者 - 消费者问题 的最有用的类，它的特性是在任意时刻只有一个线程可以进行take或者put操作，并且 BlockingQueue提供了超时return null的机制，在许多生产场景里都可以看到这个工具的 身影。

### 队列类型
   - 无限队列 （unbounded queue ） - 几乎可以无限增长 
   - 有限队列 （ bounded queue ） - 定义了最大容量
   
### 队列数据结构
    > 队列实质就是一种存储数据的结构

   - 通常用链表或者数组实现 
   - 一般而言队列具备FIFO先进先出的特性，当然也有双端队列（Deque）优先级 
   - 队列主要操作：入队（EnQueue）与出队（Dequeue）

### 常见的4种阻塞队列
   - ArrayBlockingQueue 由数组支持的有界队列 
   - LinkedBlockingQueue 由链接节点支持的可选有界队列 
   - PriorityBlockingQueue 由优先级堆支持的无界优先级队列 
   - DelayQueue 由优先级堆支持的、基于时间的调度队列
   
### ArrayBlockingQueue
队列基于数组实现,容量大小在创建ArrayBlockingQueue对象时已定义好数据结构。

#### 应用场景
在线程池中有比较多的应用，生产者消费者场景
#### 工作原理
基于ReentrantLock保证线程安全，根据Condition实现队列满时的阻塞

### LinkedBlockingQueue
是一个基于链表的无界队列(理论上有界， blockingQueue 的容量将设置为 Integer.MAX_VALUE 。)

向无限队列添加元素的所有操作都将永远不会阻塞，[注意这里不是说不会加锁保证线程安全]，因此它可以增长到非常大的容量。 使用无限 BlockingQueue 设计生产者 - 消费者模型时最重要的是 消费者应该能够像生产 者向队列添加消息一样快地消费消息 。否则，内存可能会填满，然后就会得到一 个 OutOfMemory 异常。

### DelayQueue
由优先级堆支持的、基于时间的调度队列，内部基于无界队列PriorityQueue实现，而无界 队列基于数组的扩容实现。

#### 要求
入队的对象必须要实现Delayed接口,而Delayed集成自Comparable接口 
### 应用场景
电影票
### 工作原理
队列内部会根据时间优先级进行排序。延迟类线程池周期执行。
