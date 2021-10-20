package note.kafka.producer.v11;

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

public class Producer11 extends Thread {
    private final KafkaProducer<Integer, String> producer;
    private final String topic;
    private final Boolean isAsync;

    public static void main(String[] args) {
        new Producer11(KafkaProperties.TOPIC_vk, false).start();
    }

    public Producer11(String topic, Boolean isAsync) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaProperties.BOOTSTRAP_SERVERS);
        props.put("client.id", "DemoProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        props.put("acks", "all");
        props.put("retries", 3);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        // it will failed when set this to 10000.
        props.put("buffer.memory", 133333);
        props.put("advertised.host.name", "172.24.4.184");
        props.put("advertised.port", "9092");

        producer = new KafkaProducer<>(props);
        System.out.println(producer.partitionsFor(topic)
                + "\n" + producer.metrics().toString());
        this.topic = topic;
        this.isAsync = isAsync;
    }

    @Override
    public void run() {
        Random ran1 = new Random();
        int key = 2;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        while (true) {

            try {
                Thread.sleep(4000);
                long startTime = System.currentTimeMillis();
                String data = String.format("" +
                        "{\"role\":\"88\"," +
                        "\"user_type\":88," +
                        "\"local_ping\":88," +
                        "\"line\":\"88\"," +
                        "\"server_rtt\":88," +
                        "\"classroom\":\"jzaaa-"+ran1.nextInt(1)+"\"," +
                        "\"full_ping\":"+key+"," +
                        "\"timestamp\":1572839989492}");

                String valeu = data; /*String.format(
                        "{\"colName\":\"" + format.format(startTime) + "\"," +
                        "\"tm\":" + startTime + "}");
*/
                System.out.println(data);

                //异步方式发送
                if (isAsync) {
                    // Send asynchronously
                    producer.send(new ProducerRecord<>(topic, key, valeu), new DemoCallBack(startTime, key, valeu));

                } else { // Send synchronously

                    int partition = key % 2;
                    Future<RecordMetadata> a = producer.send(new ProducerRecord<>(topic, key, valeu));
                    a.get();
                    key++;
                    System.out.println(a.isDone() + ", topic=" + a.get().topic() + " , partition=" + a.get().partition() + ", offset=" + a.get().offset());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
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

        @Override
        public void onCompletion(RecordMetadata metadata, Exception exception) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (metadata != null) {
                System.out.println("asyc message(" + key + ", " + message + ") sent to partition(" + metadata.partition() + "), " + "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
            } else {
                exception.printStackTrace();
            }
        }
    }
}

