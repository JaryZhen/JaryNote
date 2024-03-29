package note.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import note.kafka.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
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

    public static void main(String[] args) {
        startConsumer();
    }

    public static void startConsumer() {
        KafkaConsumer<Integer, String> consumer;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");//earliest  latest
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("auto.commit.interval.ms", "1000");


        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("app_inline_room_anticipation_ft_sql_test_sink"));
        System.out.println("" + consumer.listTopics().toString());
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(10);
            System.out.println(records.count());
            try {
                Thread.sleep(2000);
                System.out.println("Received message:");
                for (ConsumerRecord<Integer, String> record : records) {
                    System.out.println("Received message: (" + record.partition() + " : " + record.value() + ") at offset " + record.offset());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
