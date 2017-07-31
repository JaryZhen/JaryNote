package headfirst.test.decorator.condiment;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class Decat extends Beverage {
    public Decat(){
        description = "decat ";
    }
    @Override
    public double cost() {
        return .1;
    }
}
