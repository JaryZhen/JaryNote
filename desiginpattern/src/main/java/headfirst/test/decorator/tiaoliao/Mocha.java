package headfirst.test.decorator.tiaoliao;

import headfirst.test.decorator.condiment.Beverage;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class Mocha extends Condiment {
    Beverage beverage;

    public Mocha(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription()+" mocha";
    }

    @Override
    public double cost() {
        return .1 +beverage.cost();
    }
}
