package kafka;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;

import java.io.File;
import java.util.Properties;

/**
 * Created by Jary on 2017/10/9 0009.
 */
public class LocalKafka {

    public static void main(String[] args) {
        try {
            new LocalZK().start();
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
            KafkaConfig kafkaConfig = new KafkaConfig(kafkaProperties);
            KafkaServerStartable kafka = new KafkaServerStartable(kafkaConfig);
            kafka.startup();
        }
    }
}
