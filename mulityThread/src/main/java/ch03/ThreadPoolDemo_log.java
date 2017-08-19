package ch03;

import sun.rmi.server.Activation$ActivationSystemImpl_Stub;

import java.security.PrivateKey;
import java.util.concurrent.*;

/**
 * Created by Jary on 2017/8/16 0016.
 */
public class ThreadPoolDemo_log {
    public static class MyTask implements Runnable{

        public String name ;
        int a;
        int b;
        public MyTask(String name,int a ,int b){
            this.name = name;
            this.a = a;
            this.b= b;
        }
        @Override
        public void run() {
            System.out.println("正在执行 ---ThreadID:"+Thread.currentThread().getId()+" ,Task name="+name+"  "+a/b);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
    public class haddle implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    }
    public static void main(String[] args) throws InterruptedException {

        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024/1024.0
                +" \n"+Runtime.getRuntime().totalMemory()/1024/1024/1024.0
                +" \n"+Runtime.getRuntime().freeMemory()/1024/1024/1024.0
                +" \n"+Runtime.getRuntime().availableProcessors());
        ExecutorService es = new ThreadPoolExecutor(5,5,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("ready to execut"+ ((MyTask)r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("after execut"+ ((MyTask)r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("thread out");
            }

            @Override
            public void execute(Runnable command) {
                super.execute(wrap(command,clintent(),Thread.currentThread().getName()));
            }
        };


        for (int i = 0; i < 5; i++) {
            MyTask t = new MyTask("jary",5,i);
            es.execute(t);
            Thread.sleep(100);

            
        }
        es.shutdown();
    }

    private static Exception clintent(){
        return new Exception("client statck ,,,");
    }

     public static Runnable wrap (final Runnable task,final Exception clientStack,String threadName ){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                    throw e;
                }

            }
        };
    }



}
