package chp2;

/**
 * 线程数多 而导致内存溢出 也就是TLAB多
 * * VM Args：-Xss2M （这时候不妨设大些）

 * Created by Jary on 2017/10/11 0011.
 *
 */
public class JavaVMStackOOM {

    private void dontStop() {
        //while (true) {
        //}
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}