package headfirst.test.ducks;

import headfirst.designpatterns.strategy.FlyNoWay;
import headfirst.designpatterns.strategy.FlyWithWings;
import headfirst.designpatterns.strategy.Quack;

/**
 * Created by Jary on 2017/7/17 0017.
 */
public class MallardDuck extends Duck{
    public MallardDuck(){
        quackBehavior = new Quack();
        flyBehavior = new FlyNoWay();
    }
    public void display(){
        System.out.println("im a real mallard duck");
    }
}
