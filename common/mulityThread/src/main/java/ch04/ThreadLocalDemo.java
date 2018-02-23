package ch04;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jary on 2018/2/23 0023.
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            es.execute(new parseDate(i));
        }
    }

    public static class parseDate implements Runnable {
        ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<>();

        ReentrantLock r = new ReentrantLock();
        int i = 0;

        parseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                // r.lock();
                // Date t = new SimpleDateFormat("yyyy-MM-ss HH:mm:ss").parse("2011-12-12 23:23:"+i%60);
                // r.unlock();
                if (tl.get() == null)
                    tl.set(new SimpleDateFormat("yyyy-MM-ss HH:mm:ss"));

                Date t = tl.get().parse("2011-12-12 23:23:" + i % 60);
                // System.out.println(""+i+"  "+t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
