package headfirst.test.ducks;

import headfirst.designpatterns.strategy.FlyBehavior;
import headfirst.designpatterns.strategy.QuackBehavior;

/**
 * Created by Jary on 2017/7/17 0017.
 */
public class Duck {

    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public void performQuack() {
        quackBehavior.quack();
    }

    public void performFly() {
        flyBehavior.fly();
    }

    public void setFlyBehavior(FlyBehavior fb ){
        this.flyBehavior = fb;
    }
    public void setQuackBehavior(QuackBehavior qb){
        this.quackBehavior = qb;
    }
    public void swim() {
        System.out.println("all ducks float , even decoys");
    }
}
