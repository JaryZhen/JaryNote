package chp7.test;

/**
 * Created by Jary on 2017/11/17 0017.
 */
public class Per implements Hum {
    @Override
    public void say() {
        System.out.println("saying ...");
    }

    @Override
    public int hashCode() {
        return 3433535;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getClassLoader() != obj.getClass().getClassLoader())
            return false;
        return this.hashCode()==obj.hashCode();
    }
}
