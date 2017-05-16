package guice.aop;

import com.google.inject.ImplementedBy;

/**
 * Created by Jary on 2017/5/9 0009.
 */
@ImplementedBy(HelloImpl.class)
public interface Hello {
    void sayHi() ;

    void doSomething() ;
}
