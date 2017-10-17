package ch03;

import scala.collection.mutable.Publisher$class;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Jary on 2017/9/18 0018.
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable {

        String soldiers;
        final CyclicBarrier cyclic;

        public Soldier(CyclicBarrier cyclic, String soldiers) {
            this.cyclic = cyclic;
            this.soldiers = soldiers;
        }

        @Override
        public void run() {
            try {
                cyclic.await(); //等待集合完毕
                doWork(); //集合完毕后执行任�?
                cyclic.await();//任务都执行完�?
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        void doWork() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldiers + " :任务完成");
        }
    }

    public static class BarrerRun implements Runnable {
        boolean flag;
        int N;
        BarrerRun(boolean flag, int n) {
            this.flag = flag;
            this.N = n;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("commander: " + N + " complete");//任务都执行完�? ---> 返回结果
            } else {
                System.out.println("commander: " + N + " soldiers"); //等待集合完毕 --> 返回结果
                flag = true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int N = 10;
        Thread[] allSodlier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrerRun(flag, N));
        System.out.println("集合队伍");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵 " + i + " 报道");
            Thread.sleep(1000);
            allSodlier[i] = new Thread(new Soldier(cyclic, "士兵" + i));
            allSodlier[i].start();
        }
    }
}
