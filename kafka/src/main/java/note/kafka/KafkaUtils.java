package note.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Jary
 * @Date: 2020/11/9 11:17 AM
 */
public class KafkaUtils {
    public static KafkaConsumer createConsummer(String serverHosts, String group) {
        Properties conProps = new Properties();
        conProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverHosts);

        conProps.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        conProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        conProps.put("max.poll.records", "300000");
        conProps.put("max.poll.interval.ms", "3000");

        conProps.put("fetch.max.bytes", "152428800");
        conProps.put("fetch.max.wait.ms", "2000");
        conProps.put("max.partition.fetch.bytes", "552428800");

        conProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "3000");
        conProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        conProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        conProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        return new KafkaConsumer<>(conProps);
    }

    public static KafkaProducer createProducer(String server) {
        Properties props = new Properties();
        props.put("bootstrap.servers", server);
        props.put("client.id", "DemoProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // it will failed when set this to 10000.
        //conProps.put("zookeeper.session.timeout.ms", "400");
        //conProps.put("zookeeper.sync.time.ms", "200");
        return new KafkaProducer(props);
    }

    public static KafkaConsumer<String, String> configConsumerWithTimeStamp(KafkaConsumer<String, String> consumer, String topic, long tsp) {
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        List<TopicPartition> topicPartitions = new ArrayList<>();
        Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        System.out.println("当前时间: " + df.format(now));
        long fetchDataTime = tsp;// 1604743214000L;  // 计算30分钟之前的时间戳

        for (PartitionInfo partitionInfo : partitionInfos) {
            topicPartitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
            timestampsToSearch.put(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()), fetchDataTime);
        }

        consumer.assign(topicPartitions);

        // 获取每个partition一个小时之前的偏移量
        Map<TopicPartition, OffsetAndTimestamp> map = consumer.offsetsForTimes(timestampsToSearch);

        OffsetAndTimestamp offsetTimestamp = null;
        System.out.println("begain to assign offer set ...");
        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : map.entrySet()) {
            // 如果设置的查询偏移量的时间点大于最大的索引记录时间，那么value就为空
            offsetTimestamp = entry.getValue();
            if (offsetTimestamp != null) {
                int partition = entry.getKey().partition();
                long timestamp = offsetTimestamp.timestamp();
                long offset = offsetTimestamp.offset();
                System.out.println("partition = " + partition + ", time = " + df.format(new Date(timestamp)) + ", offset = " + offset);
                // 设置读取消息的偏移量
                consumer.seek(entry.getKey(), offset);
            }
        }
        System.out.println("ent to assign offer set ...");
        return consumer;
    }
}
