package headfirst.test.decorator;

import headfirst.test.decorator.condiment.Beverage;
import headfirst.test.decorator.condiment.Espresso;
import headfirst.test.decorator.condiment.HoustBlend;
import headfirst.test.decorator.tiaoliao.Mocha;
import headfirst.test.decorator.tiaoliao.Soy;
import headfirst.test.decorator.tiaoliao.Whip;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class StarbuzzCoffee {
    public static void main(String[] args) {
        Beverage beverage = new Espresso();
        System.out.println(beverage.getDescription() +"$" + beverage.cost());

        Beverage beverage1 = new HoustBlend();
        beverage1 = new Mocha(beverage1);
        beverage1 = new Soy(beverage1);
        beverage1 = new Whip(beverage1);
        System.out.println(beverage1.getDescription() +"$" + beverage1.cost());

    }

}
