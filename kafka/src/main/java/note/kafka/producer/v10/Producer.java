package note.kafka.producer.v10;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import note.kafka.KafkaProperties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

        producer = new KafkaProducer<>(props);
        System.out.println(producer.partitionsFor(topic)
        +"\n"+ producer.metrics().toString());
        this.topic = topic;
        this.isAsync = isAsync;
    }

    public void run() {
        Random ran1 = new Random();
        int key = 1;
        String data = FileUtil.readFileToString("data/data");
        JSONArray dataArray = JSON.parseArray(data);


        //while (true) {
        for (int i = 0; i < dataArray.size(); i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = (JSONObject) dataArray.get(i);
            String valeu = jsonObject.getJSONObject("_source").toJSONString();

            long startTime = System.currentTimeMillis();
            //异步方式发送
            if (isAsync) { // Send asynchronously
                System.out.println("Sent message: " + key + ": " + valeu + ")");
                producer.send(new ProducerRecord<>(topic,
                        key,
                        valeu), new DemoCallBack(startTime, key, valeu));
            } else { // Send synchronously
                try {
                    //producer.send(new ProducerRecord<>(topic, key, valeu)).get();
                    int partition =key%2;
                    System.out.println("Sent message: (" + key + ", " + valeu + ")");
                    //Future<RecordMetadata> a = producer.send(new ProducerRecord<>(topic,partition,key, valeu));
                    Future<RecordMetadata> a = producer.send(new ProducerRecord<>(topic,key, valeu));

                    Thread.sleep(0);
                    System.out.println(a.isDone() + ", topic=" + a.get().topic()+" , partition="+a.get().partition()+", offset="+ a.get().offset());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            ++key;
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

    static class FileUtil{

        public static String readFileToString(String path) {
            StringBuffer sb = null;
            try {

                File file = new File(path);    //给定一个目录
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");//读取文件,同时指定编码
                sb = new StringBuffer();
                char[] ch = new char[128];  //一次读取128个字符
                int len = 0;
                while ((len = isr.read(ch, 0, ch.length)) != -1) {
                    sb.append(ch, 0, len);
                }
                isr.close();
                //System.out.println(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
}


