package headfirst.test.proxy;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Jary on 2017/7/31 0031.
 */
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
    protected MyRemoteImpl() throws RemoteException {
    }


    @Override
    public String sayHello() throws RemoteException {
        return "server say hello ... ";
    }

    public static void main(String[] args) {
        try {
            MyRemote ser = new MyRemoteImpl();
            Naming.bind("remote1",ser);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
