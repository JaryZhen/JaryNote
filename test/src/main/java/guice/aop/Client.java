package guice.aop;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

/**
 * Created by Jary on 2017/5/9 0009.
 */
public class Client {
    private Hello hello;

    public static void main(String[] args) {
        Injector in = Guice.createInjector(new Module() {

            public void configure(Binder binder) {
                //将MyInterceptor绑定到所有方法上带interceptor和log @Named注解的方法上，
                // 我们可以看到Guice的拦截器是可以匹配任意多个@Named注解的。
                //Matchers 控制拦截器要拦截的范围，可以确定到某个package中，可以指定为某个class，
                // 可以指定为某个类的子类等等/下面我们用的是任何类Matchers.any()
                binder.bindInterceptor(Matchers.any(), Matchers.annotatedWith(Names.named("interceptor")), new MyInterceptor());
                binder.bindInterceptor(Matchers.any(), Matchers.annotatedWith(Names.named("log")), new MyInterceptor());
            }
        });
        //通过Guice来得到一个Hello实例，其实是指向的HelloImpl实例
        Hello hello = in.getInstance(Hello.class);
        hello.sayHi();
        System.out.println(".................");
        hello.doSomething();
    }
}
