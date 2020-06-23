package note.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import note.kafka.KafkaProperties;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

/**
 * Created by Jary on 2017/10/9 0009.
 */
@Slf4j
public class ConsumerWithTimeStamp {

    public static void main(String[] args) {
        startConsumer();
    }

    public static void startConsumer() {
        KafkaConsumer<Integer, String> consumer;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.BOOTSTRAP_SERVERS_vktest);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("linger.ms", 1);
        // it will failed when set this to 10000.

        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        consumer = new KafkaConsumer<Integer, String>(props);


        Map<TopicPartition, Long> map = new HashMap<>();
        List<PartitionInfo> flink_order = consumer.partitionsFor(KafkaProperties.TOPIC_a);
        //从半小时前开始消费

        long fetchDataTime = 1587264017045L; //System.currentTimeMillis() - (10000000);
        for (PartitionInfo par : flink_order) {
            map.put(new TopicPartition(KafkaProperties.TOPIC_a, par.partition()), fetchDataTime);
        }
        Map<TopicPartition, OffsetAndTimestamp> parMap = consumer.offsetsForTimes(map);
        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : parMap.entrySet()) {
            TopicPartition key = entry.getKey();
            OffsetAndTimestamp value = entry.getValue();
            long offset = value.offset();
            System.out.println("p: "+key.partition()+ " o "+offset);
            //根据消费里的timestamp确定offset
            if (value != null) {
                //没有这行代码会导致下面的报错信息
                consumer.assign(Arrays.asList(key));
                consumer.seek(key, offset);
            }
        }
        //consumer.assign(parMap.keySet());
        //consumer.seek(key, offset);
        while (true) {
            ConsumerRecords<Integer, String> poll = consumer.poll(1000);
            for (ConsumerRecord<Integer, String> record : poll) {
                System.out.println(record.partition()+"  " + record.value());
            }
        }
    }
}
