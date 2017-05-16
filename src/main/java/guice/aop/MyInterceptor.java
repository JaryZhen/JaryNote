package guice.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by Jary on 2017/5/9 0009.
 */
public class MyInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        doBefore() ;
        Object ret = invocation.proceed() ;
        doAfter() ;
        return ret;
    }
    //在执行真正的逻辑之前执行
    public void doBefore(){
        System.out.println("我是在方法之前要做的事情");
    }
    //支执行真正的逻辑之后执行
    public void doAfter(){
        System.out.println("我是在方法执行之后做的事情");
    }
}