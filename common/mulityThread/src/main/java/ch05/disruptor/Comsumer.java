package ch05.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * Created by Jary on 2017/8/19 0019.
 */
public class Comsumer implements WorkHandler<DData> {

    @Override
    public void onEvent(DData event) throws Exception {
        System.out.println(Thread.currentThread().getId()+ " :Event: --"+event.getValue());
    }
}
