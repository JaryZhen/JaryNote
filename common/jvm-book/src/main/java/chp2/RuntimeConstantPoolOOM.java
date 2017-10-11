package chp2;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量池溢出  本示例中用的是jdk1.8  常量池在jdk1.7以后 放到方法区外了
 * -XX:PermSize=10M -XX:MaxPermSize=10M
 * Created by Jary on 2017/10/11 0011.
 */
public class RuntimeConstantPoolOOM {
    public static void main2(String[] args) {
        // 使用List保持着常量池引用，避免Full GC回收常量池行为
        List<String> list = new ArrayList<String>();
        // 10MB的PermSize在integer范围内足够产生OOM了
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }


    public static void main(String[] args) {
        // java
        String str1 = new StringBuilder("中国").append("钓鱼岛").toString();
        System.out.println(str1.intern() == str1);

        String s = "中国钓鱼岛";
        System.out.println(str1.intern() == s);

        //java int double 等是java的关键字 所以在常量池中
        String str2 = new StringBuilder("in").append("t").toString();
        System.out.println(str2.intern() == str2);
    }
}
