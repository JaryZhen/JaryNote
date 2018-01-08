package chp9.app;

/**
 * Created by Jary on 2017/11/22 0022.
 */
public class TestClass {

    public TestClass(){
        //System.out.println("this is test");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("this is test1");
        System.out.println("this is test2");
        System.out.println("this is test3");
        exc();

    }

    public static void exc() throws InterruptedException {
        for (int j = 0; j < 10; j++) {
            Thread.sleep(1000);
            System.out.println("i="+j);
        }
    }
}
