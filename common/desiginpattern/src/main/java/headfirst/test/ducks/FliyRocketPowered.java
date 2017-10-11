package headfirst.test.ducks;

import headfirst.designpatterns.strategy.FlyBehavior;

/**
 * Created by Jary on 2017/7/17 0017.
 */
public class FliyRocketPowered implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("im fly with rocket!");
    }
}
