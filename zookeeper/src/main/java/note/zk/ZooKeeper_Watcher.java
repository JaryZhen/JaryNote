package note.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.*;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Jary on 2017/12/26 0026.
 */
public class ZooKeeper_Watcher implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper = new ZooKeeper("localhost:2222",
                5000,
                new ZooKeeper_Watcher());
        System.out.println(zookeeper.getState());
        try {
            connectedSemaphore.await();
            long sessionId = zookeeper.getSessionId();
            byte[] passwd = zookeeper.getSessionPasswd();

            System.out.println("sessionID" + sessionId + " pwd" + passwd.toString());
            //Use correct sessionId and sessionPassWd
            zookeeper = new ZooKeeper("localhost:2222", 5000, new ZooKeeper_Watcher(), sessionId, passwd);
            System.out.println(zookeeper.getState());

            zookeeper.create("/zk-test-ephemeral-", "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                    new Callback(), "I am context.");

            zookeeper.create("/zk-test-ephemeral-", "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                    new Callback(), "I am context.");

            zookeeper.create("/zk-test-ephemeral-", "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                    new Callback(), "I am context.");

            zookeeper.addAuthInfo("digest", "foo:true".getBytes());

            String path2 = zookeeper.create("/zk-test-ephemeral-",
                    "".getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("Success create znode: " + path2);

            Thread.sleep(3000);

        } catch (InterruptedException e) {

        }
        System.out.println("ZooKeeper session established.");
    }

    public void process(WatchedEvent event) {
        System.out.println("Receive watched event：" + event);
        if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();

        }
    }

}


