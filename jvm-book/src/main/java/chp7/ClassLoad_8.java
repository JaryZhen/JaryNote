package chp7;


import chp7.test.Hum;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jary on 2017/11/17 0017.
 */
public class ClassLoad_8 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException {
        ClassLoader cl = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {

                    String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                    System.out.println(fileName);
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is ==null){
                        return super.loadClass(name);
                    }
                    byte [] b = new byte[is.available()];
                    is.read();
                    return defineClass(name,b,0,b.length);
                }catch (IOException e){
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj =  cl.loadClass("chp7.test.Per").newInstance();

        if (obj instanceof Hum){
            Hum per = (Hum) obj;
            per.say();

        }


    }
}
