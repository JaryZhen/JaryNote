import java.util.Random;

/**
 * Created by Jary on 2017/11/8 0008.
 */
public class test {
    public static void main(String[] args) {
        // 案例2
        // 对于种子相同的Random对象，生成的随机数序列是一样的。
        Random ran1 = new Random();
        System.out.println("使用种子为10的Random对象生成[0,10)内随机整数序列: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(ran1.nextInt(55) + " ");
        }
        System.out.println();
        Random ran2 = new Random(10);
        System.out.println("使用另一个种子为10的Random对象生成[0,10)内随机整数序列: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(ran2.nextInt(55) + " ");
        }
    }
}
