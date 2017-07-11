package rule_kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;

import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Jary on 2017/7/6 0006.
 */
public class Consumer {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        pro();
        //con();
    }

    public static  void pro() throws InterruptedException, ExecutionException {

        Properties props = new Properties();
        props.put("bootstrap.servers", "172.24.4.141:9092");
       // props.put("advertised.host.name","172.24.4.141:9092");
       // props.put("advertised.port","9092");
        props.put("acks", "all");
        props.put("retries", 0);
        //props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);


        for (int messageNo = 1; messageNo < 50000 ; messageNo++  ){
            String messageStr = "Message_" + messageNo+" from jary";
          //Thread.sleep(500L);
            //System.out.print(messageNo+".........");
           Future<RecordMetadata> a =  producer.send(new ProducerRecord<String, String>("test",messageNo+"",messageStr));
            System.out.println(a.isDone()+""+a.get().topic());
        }

    }
    public static  void  con() {
        KafkaConsumer<Integer, String> consumer;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.50:9092" );
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("topic1"));
        ConsumerRecords<Integer, String> records = consumer.poll(10);
        System.out.println("...."+consumer.listTopics().toString());
        while (true) {
            for (ConsumerRecord<Integer, String> record : records) {
                System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
            }
        }

    }
}
