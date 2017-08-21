package ch05.pc;


import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jary on 2017/8/19 0019.
 */
public class Consumer implements Runnable {
    private BlockingDeque<PCData> queue;
    private static final int SLeepTime = 1000;

    public Consumer(BlockingDeque queue){
        this.queue = queue;
    }

    public void run() {
        System.out.println("start consumer id="+Thread.currentThread().getId());
        Random r  = new Random();
        try {
            while (true) {
                System.out.println("um... "+queue.hashCode());
                PCData data = queue.take();
                if(null != data){
                    int re = data.getIntData()*data.getIntData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}",data.getIntData(),data.getIntData(),re));
                    //Thread.sleep(r.nextInt(SLeepTime));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingDeque blockingDeque = QueueSingleton.getInstance();
        ExecutorService es = ExecutorSingleton.getInstance(5);

        System.out.println(blockingDeque.hashCode());
        Consumer c1 = new Consumer(blockingDeque);

        es.execute(c1);

        Thread.sleep(3000000);
        es.shutdown();


    }

}
