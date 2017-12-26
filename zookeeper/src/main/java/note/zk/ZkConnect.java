package note.zk;


import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.List;

public class ZkConnect {
    public static void main (String args[]) throws Exception
    {
        ZooKeeper zk = new ZooKeeper("localhost:2222", 3000, null);
        //ZooKeeper zk = connector.connect("192.168.1.55");    public static final String HOSTNAME = "localhost";

        System.out.println("=========创建节点===========");
        if(zk.exists("/ZkServer", false) == null)
        {
            System.out.println("chaungji");
            zk.create("/ZkServer", "znode1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("=============查看节点是否安装成功===============");
        System.out.println(new String(zk.getData("/ZkServer", false, null)));

        System.out.println("=========修改节点的数据==========");
        zk.setData("/ZkServer", "zNode2".getBytes(), -1);
        System.out.println("========查看修改的节点是否成功=========");
        System.out.println(new String(zk.getData("/ZkServer", false, null)));

        System.out.println("=======删除节点==========");
        zk.delete("/ZkServer", -1);
        System.out.println("==========查看节点是否被删除============");
        System.out.println("节点状态：" + zk.exists("/ZkServer", false));
        zk.getSaslClient();
        zk.close();
    }


    public void startZK() throws Exception {
        ZooKeeper zk = new ZooKeeper("localhost:2222", 10000,
                new Watcher() {
                    public void process(WatchedEvent event) {
                        System.out.println("event: " + event.getType());
                    }
                });

        System.out.println(zk.getState());

        zk.create("/myApps", "myAppsData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create("/myApps/App1", "App1Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create("/myApps/App2", "App2Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create("/myApps/App3", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.setData("/myApps/App3", "App3Data".getBytes(), -1);

        System.out.println(zk.exists("/myApps", true));
        System.out.println(new String(zk.getData("/myApps", true, null)));

        List<String> children = zk.getChildren("/myApps", true);
        for (String child : children) {
            System.out.println(new String(zk.getData("/myApps/" + child, true, null)));
            zk.delete("/myApps/" + child, -1);
        }

        //zk.delete("/myApps",-1);

        zk.close();
    }
}