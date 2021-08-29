package swordforoffer.problem02_Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jary on 2017/9/19 0019.
 * 2. 静态内部类不会随着外部类的加载而加载 ,只有静态内部类的静态成员被调用时才会进行加载 ,
 * 这样既保证的惰性初始化（Lazy-Initialazation），又由JVM保证了多线程并发访问的正确性
 *
 * 3.静态内部类有特殊的地方吗？
 * 　　从前面可以知道，静态内部类是不依赖于外部类的，也就说可以在不创建外部类对象的情况下创建内部类的对象。
 *   另外，静态内部类是不持有指向外部类对象的引用的，这个读者可以自己尝试反编译class文件看一下就知道了，是没有Outter this&0引用的。
 * 4.静态内部类
 * 　    静态内部类也是定义在另一个类里面的类，只不过在类的前面多了一个关键字static。
 *      静态内部类是不需要依赖于外部类的，这点和类的静态成员属性有点类似，并且它不能使用外部类的非static成员变量或者方法，这点很好理解，
 *      因为在没有外部类的对象的情况下，可以创建静态内部类的对象，如果允许访问外部类的非static成员就会产生矛盾，因为外部类的非static成员必须依附于具体的对象。
 *
 *
 * 而静态内部类不用产生外部类的实例化对象即可产生内部类的实例化对象
 */
public class NoLockSingleton {
    private NoLockSingleton(){}

    private static class help{
        private static NoLockSingleton singleton = new NoLockSingleton();
    }

    public static NoLockSingleton getInstance(){
        return help.singleton;
    }
    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println( NoLockSingleton.getInstance());
                }
            });
        }

    }
    /*
    静态内部类的优点是：外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化INSTANCE，故而不占内存。
    即当SingleTon第一次被加载时，并不需要去加载SingleTonHoler，只有当getInstance()方法第一次被调用时，才会去初始化INSTANCE,
    第一次调用getInstance()方法会导致虚拟机加载SingleTonHoler类，这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。

    那么，静态内部类又是如何实现线程安全的呢？首先，我们先了解下类的加载时机。
类加载时机：JAVA虚拟机在有且仅有的5种场景下会对类进行初始化。
1.遇到new、getstatic、setstatic或者invokestatic这4个字节码指令时，对应的java代码场景为：new一个关键字或者一个实例化对象时、
    读取或设置一个静态字段时(final修饰、已在编译期把结果放入常量池的除外)、调用一个类的静态方法时。
2.使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没进行初始化，需要先调用其初始化方法进行初始化。
3.当初始化一个类时，如果其父类还未进行初始化，会先触发其父类的初始化。
4.当虚拟机启动时，用户需要指定一个要执行的主类(包含main()方法的类)，虚拟机会先初始化这个类。
5.当使用JDK 1.7等动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化。
这5种情况被称为是类的主动引用，注意，这里《虚拟机规范》中使用的限定词是"有且仅有"，
那么，除此之外的所有引用类都不会对类进行初始化，称为被动引用。静态内部类就属于被动引用的行列
     */
}
