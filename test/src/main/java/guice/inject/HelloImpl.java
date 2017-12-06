package guice.inject;

/**
 * Created by Jary on 2017/5/10 0010.
 */
// 通过@ImplementedBy注解，将接口和实现类绑定在一起
public class HelloImpl implements Hello{

    public void sayHello() {
        System.out.println("HelloImpl Say Hello");
    }
}
