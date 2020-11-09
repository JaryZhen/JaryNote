package note.kafka.consumer;

import note.kafka.KafkaProperties;
import note.kafka.KafkaUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Date;

/**
 * @Author: Jary
 * @Date: 2020/11/9 11:04 AM
 */
public class ConsumerWithTimeStamp {

    public static void main(String[] args) {
        long time = 232L;
        startConsumer(time);
    }

    public static void startConsumer(long s) {
        System.out.println("let func");
        String server = KafkaProperties.BOOTSTRAP_SERVERS;

        String topic = KafkaProperties.TOPIC;  // "logs_opc_ali_opc_signalrouter_signalrouter";
        String sinkTiopic = "app_inline_etl_signal_user_in_out";

        KafkaConsumer<String, String> consumer = KafkaUtils.createConsummer(server, "ConsumerWithTimeStamp_test");

        try {
            // 获取topic的partition信息
            consumer = KafkaUtils.configConsumerWithTimeStamp(consumer, topic, s);
            long t1 = System.currentTimeMillis();
            while (true) {
                Thread.sleep(2000);
                ConsumerRecords<String, String> records = consumer.poll(2000);
                System.out.println("running... " + (System.currentTimeMillis() - t1) / 1000 / 60 + " " + new Date() + " polled " + records.count() + " msg:" + records.toString().getBytes().length);

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("partition = " + record.partition() + ", offset = " + record.offset() + " msg:" + record.value());
                    //String daf = parseValue(record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

}
