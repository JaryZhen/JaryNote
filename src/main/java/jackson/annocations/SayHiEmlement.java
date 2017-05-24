package jackson.annocations;

/**
 * Created by root on 5/15/17.
 */
public class SayHiEmlement {
    // 普通的方法
    public  void SayHiDefault(String name,int a ) {
        System.out.println("Hi, " + name+ " im " +a);
    }

    // 使用注解并传入参数的方法
    @SayHiAnnotation(paramValue = "Jack",intV =12)
    public  void SayHiAnnotation(String name,int a) {
        System.out.println("Hi, " + name + " im " +a);
    }

    // 使用注解并使用默认参数的方法
    @SayHiAnnotation(intV = 20)
    public  void SayHiAnnotationDefault(String name,int a) {
        System.out.println("Hi, " + name+ " im " +a);
    }
}
