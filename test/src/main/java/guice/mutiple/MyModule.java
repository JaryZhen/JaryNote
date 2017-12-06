package guice.mutiple;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

/**
 * Created by Jary on 2017/5/9 0009.
 */
public class MyModule implements Module {

    public void configure(Binder binder) {
        //通过annotatedWith 中使用Named方法来绑定多个实现类
        binder.bind(Hello.class).annotatedWith(Names.named("HelloImpl1")).to(HelloImpl1.class) ;
        binder.bind(Hello.class).annotatedWith(Names.named("HelloImpl2")).to(HelloImpl2.class) ;
    }
}