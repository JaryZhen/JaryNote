package ch03.Threadpool;

/**
 * Created by Jary on 2017/9/19 0019.
 */
public  class MyTask implements Runnable{

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
        try {
            System.out.println("正在执行 ---ThreadID:"+Thread.currentThread().getId()+" ,Task name = "+name+"  "+a/b);

            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name+":"+a;
    }
}