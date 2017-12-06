package guice.inject;

import com.google.inject.ImplementedBy;

/**
 * Created by Jary on 2017/5/10 0010.
 */
//通过@ImplementedBy注解，将接口和实现类绑定在一起
@ImplementedBy(HelloImpl.class)
public interface Hello {
    void sayHello() ;
}
