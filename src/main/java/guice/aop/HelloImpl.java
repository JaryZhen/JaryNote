package guice.aop;

import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Created by Jary on 2017/5/9 0009.
 */
@Singleton
public class HelloImpl implements Hello{

    /**
     * Guice中给某个方法添加拦截器是通过@Named注解来完成的，一个拦截器会绑定一个带xxx的@Named注解，
     * 当你这个对象时通过guice容器创建的，当你访问带xxx的@Named注解的时候，就会被拦截器拦截到
     */

    @Named("interceptor")
    public void sayHi() {
        System.out.println("HelloImpl exec sayHi method");
    }


    @Named("log")
    public void doSomething() {
        System.out.println("HelloImpl exec doSomething method");
    }
}
