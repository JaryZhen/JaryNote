package ch03.Threadpool;

import java.util.concurrent.*;

/**
 * Created by Jary on 2017/9/19 0019.
 */
public class MyThreadPool extends ThreadPoolExecutor {
    public MyThreadPool(int corePoolSize,
                        int maximumPoolSize,
                        long keepAliveTime, TimeUnit unit,
                        BlockingQueue<Runnable> workQueue,
                        ThreadFactory threadFactory,
                        RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        System.out.println("ready to execut" + t.getName() + r.toString());

    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                Object result = ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null)
            System.out.println(t);
    }

    @Override
    protected void terminated() {
        System.out.println("thread out");
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }
    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, clientTrace(), Thread.currentThread().getName()));
    }
    private Exception clientTrace() {
        return new Exception(" Client stack trace");
    }
    private Runnable wrap(final Runnable task, final Exception clentStack, String clientName) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
/*

        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024 / 1024.0
                + " \n" + Runtime.getRuntime().totalMemory() / 1024 / 1024 / 1024.0
                + " \n" + Runtime.getRuntime().freeMemory() / 1024 / 1024 / 1024.0
                + " \n" + Runtime.getRuntime().availableProcessors());
*/

        ExecutorService es = new MyThreadPool(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new MyThreadFactory(),//线程创建工厂方法
                new MyReajectThread()); // 拒绝策略
        for (int i = 0; i < 5; i++) {
            MyTask t = new MyTask("jary", 5, i);
            es.execute(t);
            Thread.sleep(100);


        }
        es.shutdown();
    }

}
