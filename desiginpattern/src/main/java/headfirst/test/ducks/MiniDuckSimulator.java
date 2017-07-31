package headfirst.test.ducks;

/**
 * Created by Jary on 2017/7/17 0017.
 */
public class MiniDuckSimulator {
    public static void main(String[] args) {
        Duck duck = new MallardDuck();
        duck.performFly();
        duck.performQuack();
        duck.setFlyBehavior(new FliyRocketPowered());
        duck.performFly();
    }
}
