package ch05.pc;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jary on 2017/8/19 0019.
 */
public class Producer implements Runnable {
    private volatile  boolean isRunning = true;
    private BlockingDeque<PCData> queue;
    private static AtomicInteger count = new AtomicInteger(); //比较交换（CAS）不可变且线程安全的
    private static final int SLeepTime = 1000;

    public Producer(BlockingDeque queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        PCData data = null;
        Random r = new Random();
        System.out.println("start producer id="+Thread.currentThread().getId());
        try {
            while (isRunning) {

                Thread.sleep(r.nextInt(SLeepTime));
                data = new PCData(count.incrementAndGet()); //
                System.out.println(data+ " is put into queue");
                //queue.put(data);
                if(!queue.offer(data,2,TimeUnit.SECONDS)){
                    System.out.println(data+" is not in ....");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
    public void stop(){
        isRunning = false;
    }
    public static void main(String[] args) throws InterruptedException {
        BlockingDeque blockingDeque = QueueSingleton.getInstance();
        System.out.println(blockingDeque.hashCode());
        ExecutorService es = ExecutorSingleton.getInstance(5);

        Producer p1 = new Producer(blockingDeque);


        es.execute(p1);


        Thread.sleep(300000);
        es.shutdown();


    }
}
