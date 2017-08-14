package ch02;

/**
 * Created by Jary on 2017/8/7 0007.
 */
public class StopThreadUnsafe {

    public static void main(String[] args) throws InterruptedException {
        Thread a = new ReadObjectThread();
        a.setDaemon(true);
        a.start();
        System.out.println("//");
        a.join();
        //a.interrupt();

        Thread b = new ChangeObjectThread();
        b.start();
        Thread.sleep(5000);
        b.interrupt();
/*
        for (int i1 = 0; i1 <30 ; i1++) {
            Thread t = new ChangeObjectThread();
            t.start();
            Thread.sleep(100);
            for (long i = 0; i < 10000000000000L; i++) {
                ((ChangeObjectThread) t).stopMe();
            }
        }*/
    /*    int a = 0;
        while (a<1000) {
            Thread t = new ChangeObjectThread();
            t.start();
            Thread.sleep(150);
            t.stop();
            a++;
        }*/
    }

    public static User u = new User();

    public static class User {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        public User() {
            id = 0;
            name = "0";
        }

        @Override
        public String toString() {
            return "User [id=" + id + ",name=" + name + "]";
        }
    }

    public static class ChangeObjectThread extends Thread {
        volatile boolean stopme = false;

        public void stopMe() {
            stopme = true;
        }

        Thread a;

        ChangeObjectThread() {
        }

        ChangeObjectThread(Thread t) {
            this.a = t;
        }

        @Override
        public void run() {
            while (true) {
                if (stopme) {
                    System.out.println(this.getClass()+"exit by stop me ");
                    break;
                }
                synchronized (u) {
                    int v = (int) (System.currentTimeMillis() / 1000);
                    u.setId(v);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        stopMe();
                    }
                    u.setName(String.valueOf(v));
                }
                System.out.println("ch ... " + u.toString());
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread {
        volatile boolean stopme = false;

        public void stopMe() {
            stopme = true;
        }
        @Override
        public void run() {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("interupteing");
                    break;
                }
                if (stopme) {
                    System.out.println("exit by stop me ");
                    break;
                }
                System.out.println("interupted = "+Thread.currentThread().isInterrupted());
                synchronized (u) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        stopMe();
                    }
                    System.out.println("read ..." + u.toString());
                    if (u.getId() != Integer.parseInt(u.getName())) {
                        System.out.println(u.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

}
