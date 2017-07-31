package headfirst.test.decorator.condiment;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class HoustBlend extends Beverage {
    public HoustBlend(){
        description = "housblend ";
       // System.out.println(this.getDescription());
    }
    @Override
    public double cost() {
        return .1;
    }
}
