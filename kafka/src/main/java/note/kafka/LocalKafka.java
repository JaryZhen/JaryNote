package note.kafka;

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
public class LocalKafka {

    public static void main(String[] args) {
        try {
            new LocalZK().startZkLocal();
            startKafkaLocal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startKafkaLocal() throws Exception {
        System.setProperty("log4j.configuration", String.valueOf(new File("resources", "log4j.xml")));
        final File kafkaTmpLogsDir = File.createTempFile("zk_kafka", "2");
        if (kafkaTmpLogsDir.delete() && kafkaTmpLogsDir.mkdir()) {
            Properties props = new Properties();
            props.setProperty("host.name", KafkaProperties.HOSTNAME);
            props.setProperty("port", String.valueOf(KafkaProperties.KAFKA_SERVER_PORT));
            props.setProperty("broker.id", String.valueOf(KafkaProperties.BROKER_ID));
            props.setProperty("zookeeper.connect", KafkaProperties.ZOOKEEPER_CONNECT);
            props.setProperty("log.dirs", kafkaTmpLogsDir.getAbsolutePath());

            props.setProperty("num.partitions", "6");
           // With kafka 0.11, if you set num.partitions to 1 you also need to set the following 3 settings:
            props.setProperty("offsets.topic.replication.factor","1");
            props.setProperty("transaction.state.log.replication.factor","1");
            props.setProperty("transaction.state.log.min.isr","1");

            // flush every 1ms
            props.setProperty("log.default.flush.scheduler.interval.ms", "1");
            props.setProperty("log.flush.interval", "1");
            props.setProperty("log.flush.interval.messages", "1");
            props.setProperty("replica.socket.timeout.ms", "1500");
            props.setProperty("auto.create.topics.enable", "true");

            KafkaConfig kafkaConfig = new KafkaConfig(props);

            KafkaServerStartable kafka = new KafkaServerStartable(kafkaConfig);
            kafka.startup();
            System.out.println("start kafka ok " + kafka.serverConfig().numPartitions());
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
    }
}
