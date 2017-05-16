package guice.mutiple;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by Jary on 2017/5/9 0009.
 */
public class Client {
    public static void main(String[] args) {

        Injector in = Guice.createInjector(new MyModule()) ;
        HelloCaller caller = in.getInstance(HelloCaller.class) ;

        caller.sayHello1() ;
        caller.sayHello2() ;
    }
}
