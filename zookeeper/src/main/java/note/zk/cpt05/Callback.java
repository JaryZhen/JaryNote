package note.zk.cpt05;

import org.apache.zookeeper.AsyncCallback;

/**
 * Created by Jary on 2017/12/26 0026.
 */
class Callback implements AsyncCallback.StringCallback{
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("Create path result: [" + rc + ", " + path + ", "
                + ctx + ", real path name: " + name);
    }
}
