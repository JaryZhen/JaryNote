package kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by Jary on 2017/10/9 0009.
 */
@Slf4j
public class Consumer {
    private static final String CONSUMER_TOPIC = "test_topic";
    private static final String HOSTNAME = "localhost";
    private static final int ZK_PORT = 2222;

    private static final String ZOOKEEPER_CONNECT = HOSTNAME + ":" + ZK_PORT;

    public static void main(String[] args) {
        startConsumer();
    }

    public static  void  startConsumer() {
        KafkaConsumer<Integer, String> consumer;
        Properties props = new Properties();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,KafkaProperties.BOOTSTRAP_SERVERS );
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(KafkaProperties.TOPIC));
        System.out.println("...."+consumer.listTopics().toString());
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(10);

            try {
                Thread.sleep(500);
                for (ConsumerRecord<Integer, String> record : records) {
                    System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
