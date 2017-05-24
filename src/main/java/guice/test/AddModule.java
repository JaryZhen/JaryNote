package guice.test;

/**
 * Created by Jary on 2017/5/18 0018.
 */
import com.google.inject.Binder;
import com.google.inject.Module;

public class AddModule implements Module{

    public void configure(Binder binder) {
        binder.bind(Add.class).to(SimpleAdd.class);
    }
}
