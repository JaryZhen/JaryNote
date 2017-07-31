package headfirst.test.decorator.tiaoliao;

import headfirst.test.decorator.condiment.Beverage;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class Whip extends Condiment {
    Beverage beverage;
    public Whip(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription()+" whip";
    }

    @Override
    public double cost() {
        return 0.1+ beverage.cost();
    }
}
