package headfirst.test.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jary on 2017/7/31 0031.
 */
public interface MyRemote extends Remote {
    public String sayHello() throws RemoteException;
}
