package note.kafka.v10;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;
import note.kafka.KafkaProperties;
import org.apache.zookeeper.*;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


/**
 * Created by Jary on 2017/10/9 0009.
 */
public  class LocalKafka {

    public static void main(String[] args) {
        try {
            new LocalZK().startZkLocal();
            startKafkaLocal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startKafkaLocal() throws Exception {
        final File kafkaTmpLogsDir = File.createTempFile("zk_kafka", "2");
        if (kafkaTmpLogsDir.delete() && kafkaTmpLogsDir.mkdir()) {
            Properties kafkaProperties = new Properties();
            kafkaProperties.setProperty("host.name", KafkaProperties.HOSTNAME);
            kafkaProperties.setProperty("port", String.valueOf(KafkaProperties.KAFKA_SERVER_PORT));
            kafkaProperties.setProperty("broker.id", String.valueOf(KafkaProperties.BROKER_ID));
            kafkaProperties.setProperty("zookeeper.connect", KafkaProperties.ZOOKEEPER_CONNECT);
            kafkaProperties.setProperty("log.dirs", kafkaTmpLogsDir.getAbsolutePath());
            //kafkaProperties.setProperty("num.partitions", "2");

            KafkaConfig kafkaConfig = new KafkaConfig(kafkaProperties);

            KafkaServerStartable kafka = new KafkaServerStartable(kafkaConfig);
            kafka.startup();
            System.out.println("start kafka ok "+kafka.serverConfig().numPartitions());
        }
    }


    static class LocalZK {

        private void startZkLocal() throws Exception {
            final File zkTmpDir = File.createTempFile("zookeeper", "test");

            if (zkTmpDir.delete() && zkTmpDir.mkdir()) {
                Properties zkProperties = new Properties();
                zkProperties.setProperty("dataDir", zkTmpDir.getAbsolutePath());
                zkProperties.setProperty("clientPort", String.valueOf(KafkaProperties.ZK_PORT));
                zkProperties.setProperty("tickTime", "4000");
                zkProperties.setProperty("initLimit", "10");
                zkProperties.setProperty("syncLimit", "5");


                QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
                quorumConfiguration.parseProperties(zkProperties);

                ServerConfig configuration = new ServerConfig();
                configuration.readFrom(quorumConfiguration);

                //final ZooKeeperServerMain zkServer = new ZooKeeperServerMain();
                //zkServer.runFromConfig(configuration);
                System.out.println("starting");
                new Thread("zookeeper") {
                    public void run() {
                        try {
                            System.out.println("Start of Local ZooKeeper .....");
                            new ZooKeeperServerMain().runFromConfig(configuration);
                            System.out.println("Start of Local ZooKeeper success");
                        } catch (IOException e) {
                            System.out.println("Start of Local ZooKeeper Failed");
                            e.printStackTrace(System.err);
                        }
                    }
                }.start();

            } else {
                System.out.println("Failed to delete or create domain dir for Zookeeper");
            }
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
}