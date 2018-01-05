package note.zk.cpt07;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Created by Jary on 2018/1/4 0004.
 */
public class TWatcher implements Watcher {
    @Override
    public void process(WatchedEvent event) {
        System.out.println(event.getState());
        System.out.println(event.getType());
        System.out.println(event.getPath());
        System.out.println(event.getWrapper().toString());

        System.out.println(  " ................. ");
    }
}
