package ch05.future;

/**
 * Created by Jary on 2017/8/21 0021.
 */
public class RealData implements Data {
    protected final String result;
    RealData(String para){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = sb.toString();
    }
    @Override
    public String getResult() {
        return result;
    }
}
