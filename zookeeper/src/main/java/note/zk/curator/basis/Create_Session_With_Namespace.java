package note.zk.curator.basis;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

//使用curator来创建一个含隔离命名空间的ZooKeeper客户�?
public class Create_Session_With_Namespace {
    static String path = "/zk-book/c1/w";

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(10, 1);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString("kafka1:2222")
                        .sessionTimeoutMs(50)
                        .retryPolicy(retryPolicy)
                        .namespace("jary")
                        .build();


        client.start();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path, "init".getBytes());

        System.out.println(client.getNamespace());
        ///Thread.sleep(Integer.MAX_VALUE);
    }
}