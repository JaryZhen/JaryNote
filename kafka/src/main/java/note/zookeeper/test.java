package kafka;

import org.apache.log4j.spi.ErrorHandler;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import java.io.File;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by Jary on 2017/11/1 0001.
 */
public class test
{

    /**
     * ZooKeeper server.
     */
    private volatile ZooKeeperServerMain zkServer;

    private ErrorHandler errorHandler;
    private class ServerRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Properties properties = new Properties();
                File file = new File(System.getProperty("java.io.tmpdir")
                        + File.separator + UUID.randomUUID());
                file.deleteOnExit();
                properties.setProperty("dataDir", file.getAbsolutePath());
                properties.setProperty("clientPort", String.valueOf(2222));

                QuorumPeerConfig quorumPeerConfig = new QuorumPeerConfig();
                quorumPeerConfig.parseProperties(properties);

                zkServer = new ZooKeeperServerMain();
                ServerConfig configuration = new ServerConfig();
                configuration.readFrom(quorumPeerConfig);

                zkServer.runFromConfig(configuration);
            }
            catch (Exception e) {
                System.out.println("Exception running embedded ZooKeeper");
            }
        }
    }
}
