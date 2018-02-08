package chp7;

/**
 * Created by Jary on 2018/2/5 0005.
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {

        ClassLoader myLoader = new MyClassLoader();
        Class<MyObject>  myObject = (Class<MyObject>) myLoader.loadClass("chp7.MyObject");
        Object myo = myObject.newInstance();
        Object myoo = myObject.newInstance();

        System.out.println(
                myo.getClass().getClassLoader()+"\n"+
                ClassLoaderTest.class.getClassLoader()+"\n"+
                MyObject.class.getClassLoader());

        System.out.println(myo instanceof MyObject);
        System.out.println(myo instanceof My);
        System.out.println(myo.equals(myoo));

        Object my01 =myObject.getClassLoader().getParent().loadClass("chp7.My");
        System.out.println(my01 instanceof My);



    }
}
