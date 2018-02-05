package chp7.test;

/**
 * Created by Jary on 2018/2/5 0005.
 */
class Test {
    static int i = 2;

    static {
        i = 1;
        System.out.println(""+i);
    }
}
class Te{
    public static void main(String[] args) {
        new Test();
    }

}

