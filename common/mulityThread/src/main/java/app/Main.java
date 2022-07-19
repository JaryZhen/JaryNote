package app;


import app.thread.RemoteBankThread;
import app.thread.RemoteLoanThread;
import app.thread.RemotePassportThread;
import ch03.Threadpool.MyThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 客户请求下单服务（OrderService），
 *
 * 服务端会验证用户的身份（RemotePassportService），
 * 用户的银行信用（RemoteBankService），
 * 用户的贷款记录（RemoteLoanService）。
 *
 * 为提高并发效率，要求三项服务验证工作同时进行，如其中任意一项验证失败，则立即返回失败，
 * 否则等待所有验证结束，成功返回。要求Java实现
 */
public class Main {

    private static ThreadPoolExecutor executor;

    public static void main(String[] args) {
        final int poolSize = 3;
        final int maxPoolSize = poolSize * 4;

        executor = new ThreadPoolExecutor(poolSize, maxPoolSize,
                1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new MyThreadFactory());

        boolean result = check();
        System.out.println(result ? "验证成功" : "验证失败");

        executor.shutdownNow();
    }

    private static boolean check() {
        int taskNumber = 3;
        int uid = 1;
        CountDownLatch latch = new CountDownLatch(taskNumber);
        List<FutureTask<Boolean>> tasks = new ArrayList<>();

        tasks.add(new CheckFutureTask(new RemoteBankThread(uid), latch, taskNumber));
        tasks.add(new CheckFutureTask(new RemoteLoanThread(uid), latch, taskNumber));
        tasks.add(new CheckFutureTask(new RemotePassportThread(uid), latch, taskNumber));

        for (FutureTask<Boolean> task : tasks) {
            executor.execute(task);
        }
        try {
            latch.await();

            for (FutureTask<Boolean> task : tasks) {
                task.cancel(true);
            }

            for (FutureTask<Boolean> task : tasks) {
                if (!task.get()) {
                    return false;
                }
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
