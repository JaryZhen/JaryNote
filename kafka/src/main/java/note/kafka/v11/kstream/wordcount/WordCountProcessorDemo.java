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

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import lombok.extern.slf4j.Slf4j;
import note.kafka.JSScript;
import note.kafka.KafkaProperties;
import note.kafka.NashornJsEvaluator;
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

import javax.script.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

@Slf4j
public class WordCountProcessorDemo {

    private static class MyProcessorSupplier implements ProcessorSupplier<String, String> {

        @Override
        public Processor<String, String> get() {
            return new Processor<String, String>() {
                private ProcessorContext context;
                private KeyValueStore<String, String> kvStore;

                TimeBasedGenerator gen;
                String configuration_getFilter = " basisFuc("; //String sum = "sum(a,temperature) ";
                NashornJsEvaluator jsexe;

                String splitTM = " ";
                String splitKV = "=";

                @Override
                @SuppressWarnings("unchecked")
                public void init(ProcessorContext context) {
                    gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
                    this.context = context;
                    this.context.schedule(1000);
                    this.kvStore = (KeyValueStore<String, String>) context.getStateStore("Counts");
                }


                @Override
                public void process(String key, String line) {
                    UUID uuid = gen.generate();
                    kvStore.put(uuid.toString().replaceAll("-", ""), line);
                }

              @Override
                public void punctuate(long timestamp) {
                    System.out.println("\n\n----------- " + timestamp + " ----------- ");

                    Map<String, String> kvMap = new HashMap<>();//<pre:"Jadfasda=23 Jadfasda=22"> <pre2:"Jadfasda=90 Jadfasda=22">
                    KeyValueIterator<String, String> iter = (KeyValueIterator<String, String>) this.kvStore.all();

                    iter.forEachRemaining(entry -> {
                        // System.out.println("key:" + entry.key + " msg: " + entry.value);
                        String[] msg = entry.value.split(splitTM); //[0]pre=23 ; [1]pre2=90

                        for (int i = 0; i < msg.length; i++) {
                            String[] kv = msg[i].split(splitKV);//[0]pre=23
                            String k = kv[0];//pre
                            //System.out.println("k = " +k);
                            int v = Integer.parseInt(kv[1]);//23
                            String key = "J"+entry.key;
                            String msg2 = kvMap.get(k);// "Jasdfadfe4rd=23"
                            if (msg2 != null) {
                                kvMap.put(k, msg2 + splitTM + key + splitKV + v);
                            } else {
                                kvMap.put(k, key + splitKV + v);
                            }
                        }
                        context.forward(entry.key, entry.value);
                        this.kvStore.delete(entry.key);
                    });

                    Iterator iterMap = kvMap.keySet().iterator();
                    System.out.println("Telemetry siz:" + kvMap.size());

                    iterMap.forEachRemaining(preK -> {

                        //System.out.println("jaryzhen "+preK);
                        String value = kvMap.get(preK);//Jadfasda=23 Jadfasda=22
                        StringBuffer sb = new StringBuffer();

                        Map<String, Integer> bindingMap = new HashMap<>();
                        String[] msg = value.split(splitTM); //[0]Jadfasda=23 ; [1]Jadfasda=90
                        for (int i = 0; i < msg.length; i++) {
                            String[] kv = msg[i].split(splitKV);//[0]Jadfasda=23
                            String k = kv[0];//Jadfasda
                            Integer v = Integer.parseInt(kv[1]);
                            bindingMap.put(k, v);
                            sb.append(k + ",");
                        }

                        if (bindingMap.size() > 0) {
                            String sum = configuration_getFilter + sb.toString().substring(0, sb.lastIndexOf(",")) + ")";
                            System.out.println("JS Func= " + sum);
                            System.out.println("Telemetry= " + preK);

                            jsexe = new NashornJsEvaluator(JSScript.Variance + sum);
                            try {
                                jsexe.execute(NashornJsEvaluator.toBindings(bindingMap));
                            } catch (ScriptException e) {
                                e.printStackTrace();
                            }
                        }
                   // }

                });
                    context.commit();
                }

                @Override
                public void close() {
                    this.kvStore.close();
                }

                public void execute(String key, Integer value) {
                    //log.info("executing ..{}={}.",key,value);

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
        builder.addStateStore(Stores.create("Counts").withStringKeys().withStringValues().inMemory().build(), "Process");


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
