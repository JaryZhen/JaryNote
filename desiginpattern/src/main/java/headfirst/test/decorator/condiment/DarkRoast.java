package headfirst.test.decorator.condiment;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class DarkRoast extends Beverage {
    public DarkRoast(){
        description = "darkroast ";
    }

    @Override
    public double cost() {
        return .1;
    }
}
