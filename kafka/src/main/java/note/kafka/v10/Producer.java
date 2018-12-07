package note.kafka.v10;

import note.kafka.KafkaProperties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Producer extends Thread {
    private final KafkaProducer<Integer, String> producer;
    private final String topic;
    private final Boolean isAsync;

    public static void main(String[] args) {
        new Producer(KafkaProperties.TOPIC, false).start();
    }

    public Producer(String topic, Boolean isAsync) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaProperties.BOOTSTRAP_SERVERS);
        props.put("client.id", "DemoProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        props.put("acks", "all");
        props.put("retries", 3);
        props.put("batch.size", 16384);
        // props.put("linger.ms", 1); // it will failed when set this to 10000.
        props.put("buffer.memory", 133333);
        //props.put("advertised.host.name","172.24.4.184:9092");
        //props.put("advertised.port","9092");
        props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
        producer = new KafkaProducer<>(props);
        System.out.println(producer.partitionsFor(KafkaProperties.TOPIC)
                + "\n" + producer.metrics().toString());
        this.topic = topic;
        this.isAsync = isAsync;
    }

    public void run() {
        Random ran1 = new Random();
        int key = 1;
        long tmp = 1541511012000L;
        Long laststat =  tmp;
        Boolean isC = false;

        while (true) {
            try {
                //Thread.sleep(1000);
                int user_room = ran1.nextInt(5);
                int user_role = ran1.nextInt(2);

                String value = "{\n" +
                        "    \"timestamp\": " + tmp + ",\n" +
                        "    \"role\": \"" + "STUDENT" +"\",\n" +
                        "    \"classroom\": \""+ "room:"+user_room +"\"\n" +
                        "  }";

                String value1 = "\n" +
                        "{\n" +
                        "    \"country\":\"United States\",\n" +
                        "    \"role\":\"TEACHER\",\n" +
                        "    \"city\":\"McDonough\",\n" +
                        "    \"line\":\"Audio\",\n" +
                        "    \"_ip\":\"39.107.245.223\",\n" +
                        "    \"isp\":\"AT&T U-verse\",\n" +
                        "    \"ua\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36\",\n" +
                        "    \"subdivision\":\"Georgia\",\n" +
                        "    \"user_type\":1,\n" +
                        "    \"@version\":\"1\",\n" +
                        "    \"server_rtt\":285,\n" +
                        "    \"ep_addr\":\"128.1.75.178\",\n" +
                        "    \"timestamp\":1542866056094,\n" +
                        "    \"user_ip\":\"23.127.206.181\",\n" +
                        "    \"_ua\":null,\n" +
                        "    \"classroom\":\"jz74cd8ba983e64b8c81445e40a3aaf873\",\n" +
                        "    \"full_ping\":334,\n" +
                        "    \"@timestamp\":\"2018-11-22T05:54:16.094Z\",\n" +
                        "    \"local_ping\":49,\n" +
                        "    \"location\":{\n" +
                        "        \"lon\":-84.187,\n" +
                        "        \"lat\":33.4514\n" +
                        "    }\n" +
                        "}";

                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.println("room:"+user_room + ", " + f.format(tmp)+" tm:"+tmp);
                long startTime = System.currentTimeMillis();
                //异步方式发送
                if (isAsync) { // Send asynchronously
                    System.out.println("Sent message: " + key + ": " + value + ")");
                    producer.send(new ProducerRecord<>(topic, key, value), new DemoCallBack(startTime, key, value));
                } else { // Send synchronously

                    Integer partition = key % 4;
                    //System.out.println("Sent message: (" + key + ", " + value + ")");
                    Future<RecordMetadata> futureSend = producer.send(new ProducerRecord<>(topic, partition, key, value));
                    //Future<RecordMetadata> futureSend = producer.send(new ProducerRecord<>(topic,key, value));

                    Thread.sleep(50);
                    //System.out.println(futureSend.isDone() + ", topic=" + futureSend.get().topic() + " , partition=" + futureSend.get().partition() + ", offset=" + futureSend.get().offset());

                }
                key++;
                //制造乱序数据
                int ranInt = ran1.nextInt(5);
                if (ranInt%4 == 0){
                    laststat = tmp;
                    isC = true;
                    tmp += -6000*ranInt;
                }else {
                    if (isC == true){
                        isC = false;
                        tmp = laststat;
                    }
                    tmp += 6000;
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    class DemoCallBack implements Callback {

        private final long startTime;
        private final int key;
        private final String message;

        public DemoCallBack(long startTime, int key, String message) {
            this.startTime = startTime;
            this.key = key;
            this.message = message;
        }

        public void onCompletion(RecordMetadata metadata, Exception exception) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (metadata != null) {
                System.out.println(
                        "message(" + key + ", " + message + ") sent to partition(" + metadata.partition() +
                                "), " +
                                "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
            } else {
                exception.printStackTrace();
            }
        }
    }
}


