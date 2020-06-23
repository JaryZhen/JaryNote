package ch04;

/**
 * Created by Jary on 2017/8/18 0018.
 */
public class DeadLock extends Thread {
    protected Object tool;
    static Object fork1 = new Object();
    static Object fork2 = new Object();

    public DeadLock(Object obj){
        this.tool = obj;
        if(tool == fork1){
            this.setName("zej A");
        }
        if (tool == fork2){
            this.setName("zuj B");
        }
    }

    @Override
    public void run() {
        if(tool ==fork1){
            synchronized (fork1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork2){
                    System.out.println("");
                }
            }

        }
        if(tool ==fork2){
            synchronized (fork2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.print("");
                }
                synchronized (fork1){
                    System.out.println("");
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLock A  = new DeadLock(fork1);
        DeadLock B = new DeadLock(fork2);
        A.start();
        B.start();

        //Thread.sleep(10000);
    }
}
