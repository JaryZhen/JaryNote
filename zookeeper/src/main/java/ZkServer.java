import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Jary on 2017/11/1 0001.
 */
public class ZkServer
{

    public static void main(String[] args) throws Exception {
        new LocalZK().startZkLocal();
    }
    static class LocalZK {

        private void startZkLocal() throws Exception {
            final File zkTmpDir = File.createTempFile("zookeeper", "test");

            if (zkTmpDir.delete() && zkTmpDir.mkdir()) {
                Properties zkProperties = new Properties();
                zkProperties.setProperty("dataDir", zkTmpDir.getAbsolutePath());
                zkProperties.setProperty("clientPort", String.valueOf(2181));
                zkProperties.setProperty("tickTime", "4000");
                zkProperties.setProperty("initLimit", "10");
                zkProperties.setProperty("syncLimit", "5");


                QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
                quorumConfiguration.parseProperties(zkProperties);

                ServerConfig configuration = new ServerConfig();
                configuration.readFrom(quorumConfiguration);

                final ZooKeeperServerMain zkServer ;
                //zkServer.runFromConfig(configuration);
                new Thread("zookeeper") {
                    public void run() {
                        try {
                            System.out.println("Starting  Local ZooKeeper .....");
                            new ZooKeeperServerMain().runFromConfig(configuration);

                            System.out.println("Started of Local ZooKeeper success");
                        } catch (IOException e) {
                            System.out.println("Start of Local ZooKeeper Failed");
                            e.printStackTrace(System.err);
                        }
                    }
                }.start();
                //System.out.println(zookeeper.getState());

            } else {
                System.out.println("Failed to delete or create domain dir for Zookeeper");
            }
        }
    }
}
