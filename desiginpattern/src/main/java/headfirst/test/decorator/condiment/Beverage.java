package headfirst.test.decorator.condiment;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public abstract class Beverage {
    public String description = "unknow beverge ";

    public String getDescription(){
        return description;
    }

    public abstract double cost();

}
