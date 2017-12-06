package guice.inject;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Created by Jary on 2017/5/10 0010.
 */
public class Client {
    //创建一个测试程序
    public static void main(String[] args) {
        shuXing();
    }
    public static void shuXing(){
        Injector in = Guice.createInjector(new Module(){

            public void configure(Binder arg0) {
                //什么也不写
            }
        }) ;
        //得到HelloCaller的实例
        HelloCaller helloCaller = in.getInstance(HelloCaller.class) ;
        //调用sayHello方法
        helloCaller.sayHello() ;
    }
}
