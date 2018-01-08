package chp2;

/** -Xss128k 虚拟机方法栈大小
 * Created by Jary on 2017/10/11 0011. 虚拟机栈溢出
 */
public class JavaVMStackSOF {

    //-Xss128k
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }


}