package annocations;

/**
 * Created by root on 5/15/17.
 */
public class SayHiEmlement {
    // 普通的方法
    public static void SayHiDefault(String name) {
        System.out.println("Hi, " + name);
    }

    // 使用注解并传入参数的方法
    @SayHiAnnotation(paramValue = "Jack")
    public static void SayHiAnnotation(String name) {
        System.out.println("Hi, " + name);
    }

    // 使用注解并使用默认参数的方法
    @SayHiAnnotation
    public static void SayHiAnnotationDefault(String name) {
        System.out.println("Hi, " + name);
    }

    public static void main(String[] args) {
        SayHiEmlement.SayHiAnnotation("dasd");
    }
}
