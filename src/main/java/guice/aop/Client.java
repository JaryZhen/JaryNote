package guice.aop;

/**
 * Created by Jary on 2017/5/9 0009.
 */
public class Client {
    private Hello hello ;
  </span>public static void main(String[] args) {
      </span>Injector in = Guice.createInjector(new Module(){
          </span>@Override
          </span>public void configure(Binder binder) {
              </span>//将MyInterceptor绑定到所有方法上带interceptor和log @Named注解的方法上，我们可以看到Guice的拦截器是可以匹配任意多个@Named注解的。
              </span>//Matchers 控制拦截器要拦截的范围，可以确定到某个package中，可以指定为某个class，可以指定为某个类的子类等等
              ></span>//下面我们用的是任何类Matchers.any()
              </span>binder.bindInterceptor(Matchers.any(), Matchers.annotatedWith(Names.named("interceptor")), new MyInterceptor()) ;
              </span>binder.bindInterceptor(Matchers.any(), Matchers.annotatedWith(Names.named("log")), new MyInterceptor()) ;
          </span>}
      </span>}) ;
      </span>//通过Guice来得到一个Hello实例，其实是指向的HelloImpl实例
      </span>Hello hello = in.getInstance(Hello.class) ;
      </span>
      </span>hello.sayHi() ;
      </span>hello.doSomething() ;
  </span>}
}
