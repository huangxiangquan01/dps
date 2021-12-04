package cn.xqhuang.dps.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperServerTest {


    public static void main(String[] args) {

        String connectString = "172.0.0.1:2181";
        ZooKeeper zk;
        try {
            zk = new ZooKeeper(connectString, 10,  event -> {
                String path = event.getPath();                //负责监听的路径
                Watcher.Event.KeeperState state = event.getState();   //负责监听的状态
                Watcher.Event.EventType type = event.getType();       //负责监听类型

                System.out.println(path);
            });

             zk.create("/test", "1325".getBytes(),  ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (Exception e) {

        }
    }
}
