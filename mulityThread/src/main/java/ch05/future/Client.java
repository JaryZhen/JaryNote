package ch05.future;

/**
 * Created by Jary on 2017/8/21 0021.
 */
public class Client {
    public Data request(final String qureStr){
        final FutureData futureData = new FutureData();
        new Thread(){

            @Override
            public void run() {
                RealData realData = new RealData(qureStr);
                futureData.setRealData(realData);
            }
        }.start();
        return futureData;
    }
}
