package headfirst.test.decorator.condiment;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class Espresso extends Beverage {

    public Espresso(){
        description = "Espresso";
    }

    @Override
    public double cost() {
        return .1;
    }
}
