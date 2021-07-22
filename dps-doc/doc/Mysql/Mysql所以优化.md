# 索引优化

```mysql
CREATE TABLE `employees` ( 
 `id` int(11) NOT NULL AUTO_INCREMENT, 
 `name` varchar(24) NOT NULL DEFAULT '' COMMENT '姓名', 
 `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄', 
 `position` varchar(20) NOT NULL DEFAULT '' COMMENT '职位', 
 `hire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入职时间', 
 PRIMARY KEY (`id`), 
 KEY `idx_name_age_position` (`name`,`age`,`position`) USING BTREE 
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='员工记录表'; 

 INSERT INTO employees(name,age,position,hire_time) VALUES('LiLei',22,'manager',NOW()); 
 INSERT INTO employees(name,age,position,hire_time) VALUES('HanMeimei', 23,'dev',NOW()); 
 INSERT INTO employees(name,age,position,hire_time) VALUES('Lucy',23,'dev',NOW()); 
  
 drop procedure if exists insert_emp; 
 delimiter ;; 
 create procedure insert_emp() 
 begin 
 declare i int; 
 set i = 1; 
 while(i<=100000)do 
 insert into employees(name,age,position) values(CONCAT('zhuge',i),i,'dev'); 
 set i=i+1; 
 end while; 
 end;; 
 delimiter ; 
 call insert_emp();
```

### 1、联合索引第一个字段用范围不会走索引
   >  EXPLAIN SELECT * FROM employees WHERE name > 'LiLei' AND age = 22 AND position ='manager'; 

结论：联合索引第一个字段就用范围查找不会走索引，mysql内部可能觉得第一个字段就用范围，结果集应该很大，回表效率不高，还不 如就全表扫描

### 2、强制走索引
   >  EXPLAIN SELECT * FROM employees force index(idx_name_age_position) WHERE name > 'LiLei' AND age = 22 A ND position ='manager';

结论：虽然使用了强制走索引让联合索引第一个字段范围查找也走索引，扫描的行rows看上去也少了点，但是最终查找效率不一定比全表 扫描高，因为回表效率不高
### 3、覆盖索引优化
   > EXPLAIN SELECT name,age,position FROM employees WHERE name > 'LiLei' AND age = 22 AND position ='manag er';
    
### 4、in和or在表数据量比较大的情况会走索引，在表记录不多的情况下会选择全表扫描
   > EXPLAIN SELECT * FROM employees WHERE name in ('LiLei','HanMeimei','Lucy') AND age = 22 AND position ='manager';
    
### 5、like KK% 一般情况都会走索引
   >  EXPLAIN SELECT * FROM employees WHERE name like 'LiLei%' AND age = 22 AND position ='manager';

like KK%其实就是用到了索引下推优化

#### 索引下推
对于辅助的联合索引(name,age,position)，正常情况按照最左前缀原则，SELECT * FROM employees WHERE name like 'LiLei%' AND age = 22 AND position ='manager' 这种情况只会走name字段索引，因为根据name字段过滤完，得到的索引行里的age和 position是无序的，无法很好的利用索引。 在MySQL5.6之前的版本，这个查询只能在联合索引里匹配到名字是 'LiLei' 开头的索引，然后拿这些索引对应的主键逐个回表，到主键索 引上找出相应的记录，再比对age和position这两个字段的值是否符合。 MySQL 5.6引入了索引下推优化，可以在索引遍历过程中，对索引中包含的所有字段先做判断，过滤掉不符合条件的记录之后再回表，可 以有效的减少回表次数。使用了索引下推优化后，上面那个查询在联合索引里匹配到名字是 'LiLei' 开头的索引之后，同时还会在索引里过 滤age和position这两个字段，拿着过滤完剩下的索引对应的主键id再回表查整行数据。

索引下推会减少回表次数，对于innodb引擎的表索引下推只能用于二级索引，innodb的主键索引（聚簇索引）树叶子节点上保存的是全 行数据，所以这个时候索引下推并不会起到减少查询全行数据的效果。

    估计应该是Mysql认为范围查找过滤的结果集过大，like KK% 在绝大多数情况来看，过滤后的结果集比较小，所以这里Mysql选择给 like KK% 用了索引下推优化，当然这也不是绝对的，有时like KK% 也不一定就会走索引下推。
    
