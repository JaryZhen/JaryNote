package ch05.future.jdk;

import java.util.concurrent.Callable;

/**
 * Created by Jary on 2017/8/21 0021.
 */
public class RealData implements Callable<String> {
    private String para;
    RealData(String p ){
        this.para = p;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <10; i++) {
            sb.append(para+" ");
            Thread.sleep(100);
        }
        return sb.toString();
    }
}
