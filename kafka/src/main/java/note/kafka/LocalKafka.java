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
        System.out.println("Log path " + kafkaTmpLogsDir.getAbsolutePath());
        if (kafkaTmpLogsDir.delete() && kafkaTmpLogsDir.mkdir()) {
            Properties props = new Properties();
            props.setProperty("host.name", KafkaProperties.HOSTNAME);
            props.setProperty("port", String.valueOf(KafkaProperties.KAFKA_SERVER_PORT));
            props.setProperty("broker.id", String.valueOf(KafkaProperties.BROKER_ID));
            props.setProperty("zookeeper.connect", KafkaProperties.ZOOKEEPER_CONNECT);
            props.setProperty("log.dirs", kafkaTmpLogsDir.getAbsolutePath());

            props.setProperty("num.partitions", "2");
            // With kafka 0.11, if you set num.partitions to 1 you also need to set the following 3 settings: default.replication.factor
            props.setProperty("offsets.topic.replication.factor", "1");
            props.setProperty("transaction.state.log.replication.factor", "1");
            props.setProperty("transaction.state.log.min.isr", "1");

            //日志刷新到磁盘策略
            props.setProperty("log.default.flush.scheduler.interval.ms", "1");// 周期性检查，是否需要将信息flush
            props.setProperty("log.flush.interval", "1");//当达到该时间时，强制执行一次flush null
            props.setProperty("log.flush.interval.messages", "1");//消息达到多少条时将数据写入到日志文件 10000
            props.setProperty("replica.socket.timeout.ms", "1500");
            props.setProperty("auto.create.topics.enable", "true");

            //分段策略属性
            props.setProperty("log.roll.hours", "24");//log.roll.{hours,ms} 日志滚动的周期时间，到达指定周期时间时，强制生成一个新的segmen 168（7day）
            props.setProperty("log.segment.bytes", "409600000");// 每个segment的最大容量。到达指定容量时，将强制生成一个新的segment 1G(-1为不限制)
            props.setProperty("log.retention.check.interval.ms","6000"); //日志片段文件检查的周期时间 60000

            //日志保存清理策略： 不管哪种模式都是针对不活跃的segment

            /*
            delete：一般是使用按照时间保留的策略，当不活跃的segment的时间戳是大于设置的时间的时候，当前segment就会被删除
            compact: 日志不会被删除，会被去重清理，这种模式要求每个record都必须有key，然后kafka会按照一定的时机清理segment中的key，对于同一个key只保留最新的那个key.
                     同样的，compact也只针对不活跃的segment
            日志删除，只是无法被索引到了而已。文件本身仍然是存在的，只有当过了log.segment.delete.delay.ms 这个时间以后，文件才会被真正的从文件系统中删除
             */
            props.setProperty("log.cleanup.policy", "delete");//
            props.setProperty("log.retention.minutes", "1");// 过期时间 minutes hours
            props.setProperty("log.segment.delete.delay.ms","3000");//日志文件被真正删除前的保留时间 默认为1分钟

            props.setProperty("log.retention.check.interval.ms", "5000");//过期check时间
            props.setProperty("log.cleanup.interval.mins","1"); //每隔一段时间多久调用一次清理的步骤
            KafkaConfig kafkaConfig = new KafkaConfig(props);

            KafkaServerStartable kafka = new KafkaServerStartable(kafkaConfig);
            kafka.startup();
            System.out.println("start kafka ok numPartion: " + kafka.serverConfig().numPartitions());
            System.out.println(KafkaProperties.BOOTSTRAP_SERVERS);
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
