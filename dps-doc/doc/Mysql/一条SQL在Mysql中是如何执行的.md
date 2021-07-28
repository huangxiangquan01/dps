# MySQL的内部组件结构
![组件结构](https://xqhuang.oss-cn-beijing.aliyuncs.com/study/一条sql是如何执行的.png?versionId=CAEQERiBgMDa0JSl1hciIDJiMGE3YmE2ZGJiNDQwZDlhMDAxMTIxNzBlYzc4ZWEy)

### Server层
主要包括连接器、查询缓存、分析器、优化器、执行器等，涵盖 MySQL 的大多数核心服务功能，以及所有的内置函数 （如日期、时间、数学和加密函数等），所有跨存储引擎的功能都在这一层实现，比如存储过程、触发器、视图等。

### Store层
存储引擎层负责数据的存储和提取。其架构模式是插件式的，支持 InnoDB、MyISAM、Memory等多个存储引擎。现在最常用的存储引擎是 InnoDB，它从MySQL 5.5.5 版本开始成为了默认存储引擎。也就是说如果我们在create table时不指定表的存储引擎类型,默认会给你设置存储引擎为InnoDB。

```mysql
 CREATE TABLE `test` ( 
 `id` int(11) NOT NULL AUTO_INCREMENT, 
 `name` varchar(255) DEFAULT NULL, 
 PRIMARY KEY (`id`) 
 ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
```

###  连接器
主要负责用户登录数据库，进行用户的身份认证，包括校验账户密码，权限等操作，如果用户账户密码已通过，连接器会到权限表中查询该用户的所有权限，之后在这个连接里的权限逻辑判断都是会依赖此时读取到的权限数据，也就是说，后续只要这个连接不断开，即时管理员修改了该用户的权限，该用户也是不受影响的。

### 查询缓存
连接建立后，执行查询语句的时候，会先查询缓存，Mysql会先校验这个sql是否执行过，以Key-Value的形式缓存在内存中，Key是查询预计，Value是结果集。如果缓存key被命中，就会直接返回给客户端，如果没有命中，就会执行后续的操作，完成后也会把结果缓存起来，方便下一次调用。当然在真正执行缓存查询的时候还是会校验用户的权限，是否有该表的查询条件。

Mysql 查询不建议使用缓存，因为对于经常更新的数据来说，缓存的有效时间太短了，往往带来的效果并不好，对于不经常更新的数据来说，使用缓存还是可以的，Mysql 8.0 版本后删除了缓存的功能，官方也是认为该功能在实际的应用场景比较少，所以干脆直接删掉了。

### 分析器
mysql 没有命中缓存，那么就会进入分析器，
    1. **第一步，词法分析**，一条SQL语句有多个字符串组成，首先要提取关键字，比如select，提出查询的表，提出字段名，提出查询条件等等。做完这些操作后，就会进入第二步。
    1. **第二步，语法分析**，主要就是判断你输入的sql是否正确，是否符合mysql的语法。

### 优化器
优化器的作用就是它认为的最优的执行方案去执行（虽然有时候也不是最优），比如多个索引的时候该如何选择索引，多表查询的时候如何选择关联顺序等。

### 执行器
当选择了执行方案后，mysql就准备开始执行了，首先执行前会校验该用户有没有权限，如果没有权限，就会返回错误信息，如果有权限，就会去调用引擎的接口，返回接口执行的结果。

## bin-log归档
binlog是Server层实现的二进制日志,他会记录我们的cud操作。Binlog有以下几个特点：
    1. Binlog在MySQL的Server层实现（引擎共用） 
    2. Binlog为逻辑日志,记录的是一条语句的原始逻辑
    3. Binlog不限大小,追加写入,不会覆盖以前的日志 

配置my.cnf
```
配置开启binlog 
log‐bin=/usr/local/mysql/data/binlog/mysql‐bin 
注意5.7以及更高版本需要配置本项：server‐id=123454（自定义,保证唯一性）; 
#binlog格式，有3种statement,row,mixed 
binlog‐format=ROW 
#表示每1次执行写入就与硬盘同步，会影响性能，为0时表示，事务提交时mysql不做刷盘操作，由系统决定
sync‐binlog=1
```
binlog命令
```
mysql> show variables like '%log_bin%'; 查看bin‐log是否开启 
mysql> flush logs; 会多一个最新的bin‐log日志 
mysql> show master status; 查看最后一个bin‐log日志的相关信息 
mysql> reset master; 清空所有的bin‐log日志
```
查看binlog内容
```
 /usr/local/mysql/bin/mysqlbinlog
```



