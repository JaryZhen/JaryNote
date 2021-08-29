package swordforoffer.problem02_Singleton;

/**
 * @Author: Jary
 * @Date: 2021/2/1 2:43 PM
 */
public class TestInner {
    private int number = 100;

    public static class Inner {
        private int number = 200;

        public void print() {
            System.out.println(number);
            System.out.println(this.number);
        }
    }

    private class Inner_P {
        private int number = 300;

    }

    public static void main(String[] args) {
        TestInner.Inner in = new TestInner.Inner();
        in.print();
    }
}
