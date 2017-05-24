package guice.test;

/**
 * Created by Jary on 2017/5/18 0018.
 */

import com.google.inject.Guice;
import com.google.inject.Injector;

public class AddClient {

    public static void main(String[] args) {
        AddModule addModule = new AddModule();
        Injector injector = Guice.createInjector(addModule);
        Add add = injector.getInstance(Add.class);
        System.out.println(add.add(10, 54));
    }
}
