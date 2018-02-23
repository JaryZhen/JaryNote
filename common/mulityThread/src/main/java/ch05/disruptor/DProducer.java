package ch05.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by Jary on 2018/2/23 0023.
 */
public class DProducer {
    private final RingBuffer<DData> ringBuffer;
    public DProducer(RingBuffer<DData> ringBuffer){
        this.ringBuffer = ringBuffer;
    }
    public void pushData(ByteBuffer bb){
        long sequence = ringBuffer.next();
        try {
            DData e = ringBuffer.get(sequence);
            e.setValue(bb.getLong());
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
