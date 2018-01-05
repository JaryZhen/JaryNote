package note.zk.cpt07;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoAuthException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

/**
 * �嵥7-1-4-1 ʹ����ȷ��scheme������Ȩ
 */
public class Sample_AuthFailed1 {

    final static String SERVER_LIST = "kafka1:2222";

    static ZooKeeper zkClient = null;
    static ZooKeeper zkClient_error = null;
    static List<ACL> acls = new ArrayList<ACL>(1);

    static {

        for (ACL ids_acl : Ids.CREATOR_ALL_ACL) {
            acls.add(ids_acl);
        }

    }

    public static void main(String[] args) throws Exception {

        try {
            zkClient = new ZooKeeper(SERVER_LIST, 3000, new TWatcher());
            zkClient.addAuthInfo("digest", "taokeeper:true".getBytes());
            zkClient.create("/zk-book2", "".getBytes(), acls, CreateMode.EPHEMERAL);

            zkClient_error = new ZooKeeper(SERVER_LIST, 3000, new TWatcher());
            zkClient_error.addAuthInfo("digest", "taokeeper:error".getBytes());
            zkClient_error.getData("/zk-book2", true, null);
        } catch (NoAuthException e) {
            e.printStackTrace();
        }
    }


}
