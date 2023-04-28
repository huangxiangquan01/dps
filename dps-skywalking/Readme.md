
### 下载镜像
```shell
docker pull elasticsearch:7.6.2
docker pull apache/skywalking-oap-server:8.3.0-es7
docker pull apache/skywalking-ui:8.3.0
```
### 启动elasticsearch
```shell
docker run --restart=always -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms512m -Xmx512m" \
--name='elasticsearch' --cpuset-cpus="1" -m 2G -d elasticsearch:7.6.2
```

### 安装oap
> 注意：等待elasticsearch完全启动之后，再启动oap
```shell
docker run --name oap --restart always -d 
--restart=always -e TZ=Asia/Shanghai -p 12800:12800 
-p 11800:11800 --link elasticsearch:elasticsearch -e SW_STORAGE=elasticsearch7 
-e SW_STORAGE_ES_CLUSTER_NODES=elasticsearch:9200 apache/skywalking-oap-server:8.3.0-es7

```

### 安装ui
```shell
docker run -d --name skywalking-ui \
--restart=always \
-e TZ=Asia/Shanghai \
-p 8088:8080 \
--link oap:oap \
-e SW_OAP_ADDRESS=oap:12800 \
apache/skywalking-ui:8.3.0
```

### 启动jar包
```shell
java -javaagent:/Users/huangxq/data/apache-skywalking-apm-bin-es7/agent/skywalking-agent.jar 
-Dskywalking.agent.service_name=xxxtest -Dskywalking.collector.backend_service=127.0.0.1:11800 -jar /opt/spring-boot-demo-0.0.1-SNAPSHOT.jar
```
>说明：
>-javaagent 指定agent包位置。这里我将apache-skywalking-apm-6.6.0.tar.gz解压到/opt目录了，因此路径为：/opt/apache-skywalking-apm-bin/agent/skywalking-agent.jar
>Dskywalking.agent.service_name 指定服务名
>-Dskywalking.collector.backend_service 指定skywalking oap地址，由于在本机，地址为：127.0.0.1:11800
>-jar 指定jar包的路径，这里我直接放到/opt/目录了。
