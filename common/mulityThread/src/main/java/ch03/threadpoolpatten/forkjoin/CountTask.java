package ch03.threadpoolpatten.forkjoin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Jary on 2017/9/19 0019.
 */
public class CountTask extends RecursiveTask<Long> implements Runnable {

    private static final int THREADHOLD = 10000;
    private long start;
    private long end;

    CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean canCpmput = (end - start) < THREADHOLD;
        if (canCpmput) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            long step = (start + end) / 100;
            ArrayList<CountTask> subTasks = new ArrayList<CountTask>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if (lastOne > end) lastOne = end;
                CountTask subtask = new CountTask(pos, lastOne);
                pos += step + 1;
                subTasks.add(subtask);
                subtask.fork();
            }
            for (CountTask t : subTasks) {
                sum += t.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(0, 100000L);
        ForkJoinTask<Long> result = (ForkJoinTask<Long>) forkJoinPool.submit((Runnable) task);
        try {
            long res = result.get();
            System.out.println("sum=" + res);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
