package ch06;

/**
 * @Author: Jary
 * @Date: 2020/2/10 11:26 AM
 */
public class Java8 implements A {
    private int m = 11;

    public int inc() {
        synchronized (this.getClass()) {
            try {
                if (m > 0) m = 1; else m = 2;
            } catch (Exception e) {
                throw e;
            }
        }
        return m + 1;
    }

}

