/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package note.kafka.v11.kstream.wordcount;

import lombok.extern.slf4j.Slf4j;
import note.kafka.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.Stores;
import sun.rmi.runtime.Log;

import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

@Slf4j
public class WordCountProcessorDemo {

    private static class MyProcessorSupplier implements ProcessorSupplier<String, String> {

        @Override
        public Processor<String, String> get() {
            return new Processor<String, String>() {
                private ProcessorContext context;
                private KeyValueStore<String, Integer> kvStore;

                @Override
                @SuppressWarnings("unchecked")
                public void init(ProcessorContext context) {
                    this.context = context;
                    this.context.schedule(2000);
                    this.kvStore = (KeyValueStore<String, Integer>) context.getStateStore("Counts");
                }

                @Override
                public void process(String key, String line) {
                    System.out.println("key: "+ key+" line: "+line);
                    String [] kv = line.split(" ");
                    String k = kv[0];
                    int v = Integer.parseInt(kv[1]);


                    Optional<Integer> getCount = Optional.ofNullable(kvStore.get(k));
                    int sum = getCount.map(kc -> kc).orElse(0);
                    System.out.println("sum  "+sum+ " value " +v);
                    kvStore.put(k,v+sum);
                   /* Stream.of(line.toLowerCase().split(" ")).forEach((String w) -> {
                        log.info("w= "+w);
                        Optional<Integer> counts = Optional.ofNullable(kvStore.get(w));
                        int count = counts.map(wc -> wc+1).orElse(1);
                        kvStore.put(w, count);
                    });*/
                }

                @Override
                public void punctuate(long timestamp) {
                    /*try (KeyValueIterator<String, Integer> iter = this.kvStore.all()) {
                        System.out.println("----------- " + timestamp + " ----------- ");
                        while (iter.hasNext()) {
                            KeyValue<String, Integer> entry = iter.next();
                            System.out.println("[" + entry.key + ", " + entry.value + "]");
                            context.forward(entry.key, entry.value.toString());
                        }
                    }
                    context.commit();*/
                    KeyValueIterator<String,Integer> iter = (KeyValueIterator<String, Integer>) this.kvStore.all();
                    System.out.println("----------- " + timestamp + " ----------- ");

                    iter.forEachRemaining(entry -> {
                        context.forward(entry.key,entry.value.toString());
                        System.out.println("world:" + entry.key + " count: " + entry.value);
                        this.kvStore.delete(entry.key);
                    });
                    context.commit();
                }

                @Override
                public void close() {
                    this.kvStore.close();
                }
            };
        }
    }

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // setting offset reset to earliest so that we can re-run the demo code with the same pre-loaded data
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        TopologyBuilder builder = new TopologyBuilder();

        builder.addSource("Source", KafkaProperties.TOPIC_V11_S);

        builder.addProcessor("Process", new MyProcessorSupplier(), "Source");
        builder.addStateStore(Stores.create("Counts").withStringKeys().withIntegerValues().inMemory().build(), "Process");


        builder.addSink("Sink", KafkaProperties.TOPIC, "Process");

        final KafkaStreams streams = new KafkaStreams(builder, props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-wordcount-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            System.out.println("stream is started");
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
