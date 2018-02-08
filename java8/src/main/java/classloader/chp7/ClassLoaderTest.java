package classloader.chp7;

/**
 * Created by Jary on 2018/2/5 0005.
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {

        ClassLoader myLoader = new MyClassLoader();
        Class<MyObject>  myObject = (Class<MyObject>) myLoader.loadClass("classloader.MyObject");
        Object myo = myObject.newInstance();

        System.out.println(
                myo.getClass().getClassLoader()+"\n"+
                ClassLoaderTest.class.getClassLoader()+"\n"+
                MyObject.class.getClassLoader());

        System.out.println(myo instanceof MyObject);
        System.out.println(myo instanceof MyInterf);

        Object my01 =myObject.getClassLoader().getParent().loadClass("classloader.MyInterf");
        System.out.println(my01);



    }
}
