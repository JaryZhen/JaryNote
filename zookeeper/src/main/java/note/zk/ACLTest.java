package note.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
//使用含权限信息的ZooKeeper会话创建数据节点
public class ACLTest {

    final static String PATH = "/zk-book-auth_test";
    public static void main1(String[] args) throws Exception {

        ZooKeeper zookeeper = new ZooKeeper("localhost:2222",50000,null);
        zookeeper.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper.create( PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL );
        Thread.sleep( Integer.MAX_VALUE );


    }

    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper1 = new ZooKeeper("localhost:2222",50000,null);
        zookeeper1.addAuthInfo("digest", "foo:true".getBytes());
        //zookeeper1.create( PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL );

        ZooKeeper zookeeper2 = new ZooKeeper("localhost:2222",50000,null);
        zookeeper2.addAuthInfo("digest", "foo:true".getBytes());
        System.out.println(zookeeper2.getData( PATH, false, null ));

        ZooKeeper zookeeper3 = new ZooKeeper("localhost:2222",50000,null);
        zookeeper3.addAuthInfo("digest", "foo:false".getBytes());
        zookeeper3.getData( PATH, false, null );
    }
}