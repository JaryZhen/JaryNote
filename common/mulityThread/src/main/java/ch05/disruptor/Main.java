package ch05.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jary on 2018/2/23 0023.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        DDataFactory factory = new DDataFactory();
        int bufferSize = 4;
        Disruptor<DData> disruptor = new Disruptor<DData>(factory,bufferSize,executor, ProducerType.MULTI,new BlockingWaitStrategy());

        disruptor.handleEventsWithWorkerPool(new Comsumer(),new Comsumer(),new Comsumer());

        disruptor.start();
        RingBuffer<DData> ringBuffer = disruptor.getRingBuffer();
        DProducer producer = new DProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);

        for (int i = 0; i < 1000; i++) {
            bb.putLong(0,1);
            producer.pushData(bb);
            Thread.sleep(100);
            System.out.println("add data = "+i);

        }
    }
}
