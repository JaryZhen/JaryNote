package ch05.pc;

/**
 * Created by Jary on 2017/8/19 0019.
 */
public class PCData {
    private final int intData;

    public PCData(int d){
        intData= d;
    }
    public PCData(String s){
        intData = Integer.valueOf(s);
    }

    public int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "data:"+intData;
    }
}
