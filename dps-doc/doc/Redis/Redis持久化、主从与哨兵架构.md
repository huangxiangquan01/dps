## Redis持久化

### **RDB快照（snapshot）**

​		在默认情况下， Redis 将内存数据库快照保存在名字为 dump.rdb 的二进制文件中。 你可以对 Redis 进行设置， 让它在“ N 秒内数据集至少有 M 个改动”这一条件被满足时， 自动保存一次数据集。 

​		比如说， 以下设置会让 Redis 在满足“ 60 秒内有至少有 1000 个键被改动”这一条件时， 自动保存一次数据集：

```
# save 60 1000 //关闭RDB只需要将所有的save保存策略注释掉即可
```

​		还可以手动执行命令生成RDB快照，进入redis客户端执行命令**save**或**bgsave**可以生成dump.rdb文件，每次命令执行都会将所有redis内存快照到一个新的rdb文件里，并覆盖原有rdb快照文件。

#### **bgsave的写时复制(COW)机制**

​		Redis 借助操作系统提供的写时复制技术（Copy-On-Write, COW），在生成快照的同时，依然可以正常处理写命令。简单来说，bgsave 子进程是由主线程 fork 生成的，可以共享主线程的所有内存数据。bgsave 子进程运行后，开始读取主线程的内存数据，并把它们写入 RDB 文件。此时，如果主线程对这些 数据也都是读操作，那么，主线程和 bgsave 子进程相互不影响。但是，如果主线程要修改一块数据，那么，这块数据就会被复制一份，生成该数据的副本。然后，bgsave 子进程会把这个副本数据写入 RDB 文件，而在这个过程中，主线程仍然可以直接修改原来的数据。

#### **save与bgsave对比：** 

|       **命令**        |       save       |                   **bgsave**                    |
| :-------------------: | :--------------: | :---------------------------------------------: |
|        IO类型         |       同步       |                      异步                       |
| 是否阻塞redis其它命令 |        是        | 否(在生成子进程执行调用fork函 数时会有短暂阻塞) |
|        复杂度         |       O(n)       |                      O(n)                       |
|         优点          | 不会消耗额外内存 |                不阻塞客户端命令                 |
|         缺点          |  阻塞客户端命令  |            需要fork子进程，消耗内存             |

#### **配置自动生成rdb文件后台使用的是bgsave方式。**

### **AOF（append-only file）** 

​		快照功能并不是非常耐久（durable）： 如果 Redis 因为某些原因而造成故障停机， 那么服务器将丢失最近写入、且仍未保存到快照中的那些数据。从 1.1 版本开始， Redis 增加了一种完全耐久的持久化方式： AOF 持久化，将**修改的**每一条指令记录进文件appendonly.aof中(先写入os cache，每隔一段时间fsync到磁盘) 

​		比如执行命令**“set zhuge 666”**，aof文件里会记录如下数据 

```
*3
$3 
set 
$5 
zhuge 
$3 
666
```

​		这是一种resp协议格式数据，星号后面的数字代表命令有多少个参数，$号后面的数字代表这个参数有几个字符 

​		注意，如果执行带过期时间的set命令，aof文件里记录的是并不是执行的原始命令，而是记录key过期的**时间戳** 

​		你可以通过修改配置文件来打开 AOF 功能：

```
 appendonly yes
```

- 从现在开始， 每当 Redis 执行一个改变数据集的命令时（比如 SET）， 这个命令就会被追加到 AOF 文件的末尾。 

- 这样的话， 当 Redis 重新启动时， 程序就可以通过重新执行 AOF 文件中的命令来达到重建数据集的目的。

- 你可以配置 Redis 多久才将数据 fsync 到磁盘一次。 

```
appendfsync always：每次有新命令追加到 AOF 文件时就执行一次 fsync ，非常慢，也非常安全。 
appendfsync everysec：每秒 fsync 一次，足够快，并且在故障时只会丢失 1 秒钟的数据。 
appendfsync no：从不 fsync ，将数据交给操作系统来处理。更快，也更不安全的选择。
```

​	推荐（并且也是默认）的措施为每秒 fsync 一次， 这种 fsync 策略可以兼顾速度和安全性。 

#### **AOF重写** 

​		AOF文件里可能有太多没用指令，所以AOF会定期根据**内存的最新数据**生成aof文件 

​		如下两个配置可以控制AOF自动重写频率 :

```
auto‐aof‐rewrite‐min‐size 64mb //aof文件至少要达到64M才会自动重写，文件太小恢复速度本来就 很快，重写的意义不大 
auto‐aof‐rewrite‐percentage 100 //aof文件自上一次重写后文件大小增长了100%则再次触发重写
```

​		当然AOF还可以手动重写，进入redis客户端执行命令**bgrewriteaof**重写AOF 

​		注意，**AOF重写redis会fork出一个子进程去做(与bgsave命令类似)，不会对redis正常命令处理有太多** **影响**

**RDB 和 AOF ，我应该用哪一个**

|  **命令**  |  **RDB**   |   **AOF**    |
| :--------: | :--------: | :----------: |
| 启动优先级 |     低     |      高      |
|    体积    |     小     |      大      |
|  恢复速度  |     快     |      慢      |
| 数据安全性 | 容易丢数据 | 根据策略决定 |

​		生产环境可以都启用，redis启动时如果既有rdb文件又有aof文件则优先选择aof文件恢复数据，因为aof一般来说数据更全一点。

### **Redis 4.0 混合持久化**

​		重启 Redis 时，我们很少使用 RDB来恢复内存状态，因为会丢失大量数据。我们通常使用 AOF 日志重放，但是重放 AOF 日志性能相对 RDB来说要慢很多，这样在 Redis 实例很大的情况下，启动需要花费很长的时间。 Redis 4.0 为了解决这个问题，带来了一个新的持久化选项——混合持久化。 通过如下配置可以开启混合持久化(**必须先开启aof**)：

```
aof‐use‐rdb‐preamble yes
```

​		如果开启了混合持久化，**AOF在重写时**，不再是单纯将内存数据转换为RESP命令写入AOF文件，而是将重写**这一刻之前**的内存做RDB快照处理，并且将RDB快照内容和**增量的**AOF修改内存数据的命令存在一起，都写入新的AOF文件，新的文件一开始不叫appendonly.aof，等到重写完新的AOF文件才会进行改名，覆盖原有的AOF文件，完成新旧两个AOF文件的替换。 于是在 Redis 重启的时候，可以先加载 RDB 的内容，然后再重放增量 AOF 日志就可以完全替代之前的AOF 全量文件重放，因此重启效率大幅得到提升。 

#### **Redis**数据备份策略:

1. 写crontab定时调度脚本，每小时都copy一份rdb或aof的备份到一个目录中去，仅仅保留最近48小时的备份 

2. 每天都保留一份当日的数据备份到一个目录中去，可以保留最近1个月的备份 

3. 每次copy备份的时候，都把太旧的备份给删了 

4. 每天晚上将当前机器上的备份复制一份到其他机器上，以防机器损坏 

## **Redis主从架构**

### **Redis主从工作原理**

​		如果你为master配置了一个slave，不管这个slave是否是第一次连接上Master，它都会发送一个**PSYNC**命令给master请求复制数据。 

​		master收到PSYNC命令后，会在后台进行数据持久化通过bgsave生成最新的rdb快照文件，持久化期间，master会继续接收客户端的请求，它会把这些可能修改数据集的请求缓存在内存中。当持久化进行完毕以后，master会把这份rdb文件数据集发送给slave，slave会把接收到的数据进行持久化生成rdb，然后再加载到内存中。然后，master再将之前缓存在内存中的命令发送给slave。当master与slave之间的连接由于某些原因而断开时，slave能够自动重连Master，如果master收到了多个slave并发连接请求，它只会进行一次持久化，而不是一个连接一次，然后再把这一份持久化的数据发送给多个并发连接的slave。

### 主从复制(全量复制)流:

#### **数据部分复制** 

- 当master和slave断开重连后，一般都会对整份数据进行复制。但从redis2.8版本开始，redis改用可以支持部分数据复制的命令PSYNC去master同步数据，slave与master能够在网络连接断开重连后只进行部分数据复制(**断点续传**)。

- master会在其内存中创建一个复制数据用的缓存队列，缓存最近一段时间的数据，master和它所有的slave都维护了复制的数据下标offset和master的进程id，因此，当网络连接断开后，slave会请求master继续进行未完成的复制，从所记录的数据下标开始。如master进程id变化了，或者从节点数据下标offset太旧，已经不在master的缓存队列里了，那么将会进行一次全量数据的复制。

- 如果有很多从节点，为了缓解**主从复制风暴**(多个从节点同时复制主节点导致主节点压力过大)，可以做如下架构，让部分从节点与从节点(与主节点同步)同步数据

## **Redis哨兵高可用架构**

​		sentinel哨兵是特殊的redis服务，不提供读写服务，主要用来监控redis实例节点。 哨兵架构下client端第一次从哨兵找出redis的主节点，后续就直接访问redis的主节点，不会每次都通过sentinel代理访问redis的主节点，当redis的主节点发生变化，哨兵会第一时间感知到，并且将新的redis主节点通知给client端(这里面redis的client端一般都实现了订阅功能，订阅sentinel发布的节点变动消息) 

### **Redis客户端命令对应的RedisTemplate中的方法列表**

| **String类型结构**                       |                                                             | 描述 |
| ---------------------------------------- | ----------------------------------------------------------- | ---- |
| Redis                                    | RedisTemplate rt                                            |      |
| set key value                            | rt.opsForValue().set("key","value")                         |      |
| get key                                  | rt.opsForValue().get("key")                                 |      |
| del key                                  | rt.delete("key")                                            |      |
| strlen key                               | rt.opsForValue().size("key")                                |      |
| getset key value                         | rt.opsForValue().getAndSet("key","value")                   |      |
| getrange key start end                   | rt.opsForValue().get("key",start,end)                       |      |
| **Hash结构**                             |                                                             |      |
| hmset key field1 value1 field2 value2... | rt.opsForHash().putAll("key",map) //map是一个集合对象       |      |
| hset key field value                     | rt.opsForHash().put("key","field","value")                  |      |
| hexists key field                        | rt.opsForHash().hasKey("key","field")                       |      |
| hgetall key                              | rt.opsForHash().entries("key") //返回Map对象                |      |
| hvals key                                | rt.opsForHash().values("key") //返回List对象                |      |
| hkeys key                                | rt.opsForHash().keys("key") //返回List对象                  |      |
| hmget key field1 field2...               | rt.opsForHash().multiGet("key",keyList)                     |      |
| hsetnx key field value                   | rt.opsForHash().putIfAbsent("key","field","value)           |      |
| hdel key field1 field2                   | rt.opsForHash().delete("key","field1","field2")             |      |
| hget key field                           | rt.opsForHash().get("key","field")                          |      |
| **List结构**                             |                                                             |      |
| lpush list node1 node2 node3...          | rt.opsForList().leftPush("list","node")                     |      |
|                                          | rt.opsForList().leftPushAll("list",list) //list是集合对象   |      |
| rpush list node1 node2 node3...          | rt.opsForList().rightPush("list","node")                    |      |
|                                          | rt.opsForList().rightPushAll("list",list) //list是集合对象  |      |
| lindex key index                         | rt.opsForList().index("list", index)                        |      |
| llen key                                 | rt.opsForList().size("key")                                 |      |
| lpop key                                 | rt.opsForList().leftPop("key")                              |      |
| rpop key                                 | rt.opsForList().rightPop("key")                             |      |
| lpushx list node                         | rt.opsForList().leftPushIfPresent("list","node")            |      |
| rpushx list node                         | rt.opsForList().rightPushIfPresent("list","node")           |      |
| lrange list start end                    | rt.opsForList().range("list",start,end)                     |      |
| lrem list count value                    | rt.opsForList().remove("list",count,"value")                |      |
| lset key index value                     | rt.opsForList().set("list",index,"value")                   |      |
| **Set结构**                              |                                                             |      |
| sadd key member1 member2...              | rt.boundSetOps("key").add("member1","member2",...)          |      |
|                                          | rt.opsForSet().add("key", set) //set是一个集合对象          |      |
| scard key                                | rt.opsForSet().size("key")                                  |      |
| sidff key1 key2                          | rt.opsForSet().difference("key1","key2") //返回一个集合对象 |      |
| sinter key1 key2                         | rt.opsForSet().intersect("key1","key2")//同上               |      |
| sunion key1 key2                         | rt.opsForSet().union("key1","key2")//同上                   |      |
| sdiffstore des key1 key2                 | rt.opsForSet().differenceAndStore("key1","key2","des")      |      |
| sinter des key1 key2                     | rt.opsForSet().intersectAndStore("key1","key2","des")       |      |
| sunionstore des key1 key2                | rt.opsForSet().unionAndStore("key1","key2","des")           |      |
| sismember key member                     | rt.opsForSet().isMember("key","member")                     |      |
| smembers key                             | rt.opsForSet().members("key")                               |      |
| spop key                                 | rt.opsForSet().pop("key")                                   |      |
| srandmember key count                    | rt.opsForSet().randomMember("key",count)                    |      |
| srem key member1 member2...              | rt.opsForSet().remove("key","member1","member2",...)        |      |

