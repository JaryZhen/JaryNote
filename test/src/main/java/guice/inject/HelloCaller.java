package guice.inject;

import com.google.inject.Inject;

/**
 * Created by Jary on 2017/5/10 0010.
 */
public class HelloCaller {

    //通过@Inject，来完成属性的注入(1.注入：就是实例化对接口的实现，这里Hello是接口。2. 被注入的对象是个接口interface)
    //@Inject
    private Hello hello ;

    //@Inject注解写在构造方法上，通过构造方法的方式注入属性hello
    @Inject
    public HelloCaller(Hello hello){
        this.hello = hello ;
    }
    //调用Hello的sayHello方法（实际上就是去调用HelloImpl的sayHello，因为我们将Hello的实现指定是HelloImpl）
    public void sayHello(){
        hello.sayHello() ;
    }
}
