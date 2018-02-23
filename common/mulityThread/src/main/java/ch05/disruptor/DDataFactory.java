package ch05.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Jary on 2018/2/23 0023.
 */
public class DDataFactory implements EventFactory<DData> {
    @Override
    public DData newInstance() {
        return new DData();
    }
}
