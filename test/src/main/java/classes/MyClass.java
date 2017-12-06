package classes;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class MyClass {
    private static volatile  MyClass myClass;
    private MyClass(){}
    public static MyClass getInstatans(){
        synchronized(MyClass.class) {
            if(myClass == null ) {
                myClass = new MyClass();
            }
        }
        return myClass;
    }
}
