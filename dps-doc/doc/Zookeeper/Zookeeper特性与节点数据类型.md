## 1、Zookeeper

​		官方文档上这么解释zookeeper，它是一个分布式协调框架，是Apache Hadoop 的一个子项目，它主要是用来解决分布式应用中经常遇到的一些数据管理问题，如：统一命名服务、状态同 步服务、集群管理、分布式应用配置项的管理等。

##  2、Zookeeper 核心概念

​		Zookeeper 是一个用于存储少量数据的基于内存的数据库，主要有如下两个核心的概念：文件系统数据结构+监听通知机制。

### **2.1、 文件系统数据结构** 

​		每个子目录项都被称作为 **znode(目录**节点)**，和文件系统类似，我们能够自由的增加、删除znode，在一个znode下增加、删除子znode。 

- PERSISTENT­持久化目录节点：
  - 客户端与zookeeper断开连接后，该节点依旧存在，只要不手动删除该节点，他将永远存在
-  PERSISTENT_SEQUENTIAL­持久化顺序编号目录节点：
  - 客户端与zookeeper断开连接后，该节点依旧存在，只是Zookeeper给该节点名称进行顺序编号 
- EPHEMERAL­临时目录节点：
  - 客户端与zookeeper断开连接后，该节点被删除 
- EPHEMERAL_SEQUENTIAL­临时顺序编号目录节点 
  - 客户端与zookeeper断开连接后，该节点被删除，只是Zookeeper给该节点名称进行顺序编号
- Container 节点（3.5.3 版本新增，如果Container节点下面没有子节点，则Container节点在未来会被Zookeeper自动清除,定时任务默认60s 检查一次）
-  TTL 节点( 默认禁用，只能通过系统配置 *zookeeper.extendedTypesEnabled=true* 开启，不稳定)

### **2.2、监听通知机制**

​		客户端注册监听它关心的任意节点，或者目录节点及递归子目录节点

- 如果注册的是对某个节点的监听，则当这个节点被删除，或者被修改时，对应的客户端将被通知
- 如果注册的是对某个目录的监听，则当这个目录有子节点被创建，或者有子节点被删除，对应的客户端将被通知
- 如果注册的是对某个目录的递归子节点进行监听，则当这个目录下面的任意子节点有目录结构的变化（有子节点被创建，或被删除）或者根节点有数据变化时，对应的客户端将被通知。

> 注意：所有的通知都是一次性的，及无论是对节点还是对目录进行的监听，一旦触发，对应的监听即被移除。递归子节点，监听是对所有子节点的，所以，每个子节点下面的事件同样只会被触发一次。 

## **2.3、Zookeeper 经典的应用场景** 

1. 分布式配置中心

2. 分布式注册中心 

3. 分布式锁 

4. 分布式队列 

5. 集群选举 

6. 分布式屏障 

7. 发布/订阅 

## **3. Zookeeper 实战** 

### **3.1. zookeeper安装**

- 配置JAVA环境，检验环境：

```
java ‐version
```

-  **下载解压 zookeeper** 

```
wget https://mirror.bit.edu.cn/apache/zookeeper/zookeeper‐3.5.8/apache‐zookeepe r‐3.5.8‐bin.tar.gz 
 tar ‐zxvf apache‐zookeeper‐3.5.8‐bin.tar.gz 
 cd apache‐zookeeper‐3.5.8‐bin
```

-  **重命名配置文件 zoo_sample.cfg** 

```
cp zoo_sample.cfg zoo.cfg
```

-  **启动zookeeper**

```
# 可以通过 bin/zkServer.sh 来查看都支持哪些参数 
bin/zkServer.sh start conf/zoo.cfg
```

- **检测是否启动成功** 

```
echo stat | nc 192.168.109.200 // 前提是配置文件中中讲 stat 四字命令设置了了白名单 
如：
4lw.commands.whitelist=stat
```

- **连接服务器** 

```
bin/zkCli.sh ‐server ip:port
```

### **3.2. 使用命令行操作zookeeper** 

​		输入命令 help 查看zookeeper所支持的所有命令： 

```
 [zk: localhost:2181(CONNECTED) 80] help
```

## **4. Zookeeper 的 ACL 权限控制( Access Control List )** 

​		Zookeeper 的ACL 权限控制,可以控制节点的读写操作,保证数据的安全性，Zookeeper ACL 权限设置分为 3 部分组成，分别是：**权限模式**（Scheme）、**授权对象**（ID）、**权限信息**（Permission）。

​	**Scheme（权限模式）**：用来设置 ZooKeeper 服务器进行权限验证的方式。

ZooKeeper 的权限验证方式大体分为两种类型：

- **范围验证**。所谓的范围验证就是说 ZooKeeper 可以针对一个 IP 或者一段 IP 地址授予某种权限。比如我们可以让一个 IP 地址为“ip：192.168.0.110”的机器对服务器上的某个数据节点具有写入的权限。或者也可以通过“ip:192.168.0.1/24”给一段 IP 地址的机器赋权。
- **口令验证**，也可以理解为用户名密码的方式。在 ZooKeeper 中这种验证方式是 Digest 认证，而 Digest 这种认证方式首先在客户端传送“username:password”这种形式的权限表示符后，ZooKeeper 服务端会对密码 部分使用 SHA-1 和 BASE64 算法进行加密， 以保证安全性。

> 还有一种Super权限模式, Super可以认为是一种特殊的 Digest 认证。具有 Super 权限的客户端可以对 ZooKeeper 上的任意数据节点进行任意操作。 

