package zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Jary on 2018/1/31 0031.
 */
public class TransferToServer {
    ServerSocketChannel listener = null;
    public static void main(String[] args) {
        TransferToServer dns = new TransferToServer();
        dns.mySetup();
        dns.readData();
    }
    protected void mySetup(){
        InetSocketAddress listenAddr  = new InetSocketAddress(9026);
        try {

            listener =  ServerSocketChannel.open();
            ServerSocket ss  = listener.socket();
            ss.bind(listenAddr);
            System.out.println("listen on :" + listenAddr.toString());
        }catch (IOException o ){
            System.out.println("fail");

        }
    }
    private void readData() {
        ByteBuffer dst = ByteBuffer.allocate(4096);
        try {
            while (true) {
                SocketChannel conn = listener.accept();
                System.out.println("创建的连接: " + conn);
                conn.configureBlocking(true);
                int nread = 0;
                while (nread != -1) {
                    try {
                        nread = conn.read(dst);
                    } catch (IOException e) {
                        e.printStackTrace();
                        nread = -1;
                    }
                    dst.rewind();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
