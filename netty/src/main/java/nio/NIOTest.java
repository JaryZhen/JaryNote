package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Jary on 2017/11/30 0030.
 */
public class NIOTest {
    public static void main(String[] args) throws IOException, InterruptedException {

        //read();
        //write();
        //readAndWrite();
        //others();
        useFloatBuffer();

    }

    public static void read() throws IOException, InterruptedException {

        FileInputStream fin = new FileInputStream("velocity.log");
        FileChannel inChannel = fin.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(512);

        int bytesRead = inChannel.read(buf); //read into buffer.
        int i = 0;
        while (bytesRead != -1) {
            System.out.println("" + i++);
            buf.flip();  //make buffer ready for read

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get()); // read 1 byte at a time
            }
            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        fin.close();
    }

    public static void write() throws IOException {
        FileOutputStream fout = new FileOutputStream("velocity1.log");
        FileChannel fc = fout.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String m = "dabhyhsdfas\n asdfasdf";
        byte[] message = m.getBytes();
        for (int i = 0; i < message.length; ++i) {
            buffer.put(message[i]);
        }
        buffer.flip();

        fc.write(buffer);
    }

    public static void readAndWrite() throws IOException {
        //read
        FileInputStream fi = new FileInputStream("velocity.log");
        FileChannel fcin = fi.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //write
        FileOutputStream fo = new FileOutputStream("velocity2.log");
        FileChannel fcout = fo.getChannel();
        // ByteBuffer wbuf = ByteBuffer.allocate(1024);


        int i = 0;

        while (true) {
            System.out.println("" + i++);
            /*
            1.它将 limit 设置为与 capacity 相同。
            2.它设置 position 为 0
             */
            buf.clear();
            int r = fcin.read(buf);
            if (r == -1) {
                break;
            }
            /*
            1.它将 limit 设置为当前 position。
            2.它将 position 设置为 0。
             */
            buf.flip();
            fcout.write(buf);
        }

    }

    public static void others() {
        //wrap
        //byte array[] = new byte[1024];
        //ByteBuffer buffer = ByteBuffer.wrap( array );

        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        /*
        创建一个包含槽 3 到槽 6 的子缓冲区
        窗口的起始和结束位置通过设置 position 和 limit 值来指定
         */
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();

        /*
        遍历子缓冲区，将每一个元素乘以 11 来改变它
         */
        for (int i = 0; i < slice.capacity(); ++i) {
            byte b = slice.get(i);
            b *= 11;
            slice.put(i, b);
        }

        /*
        重新设置回原来的位置
         */
        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining() > 0) {
            System.out.println(buffer.get());
        }
    }


    public static void useFloatBuffer() {
        FloatBuffer buffer = FloatBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            float f = (float)i;
            buffer.put(f);
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            float f = buffer.get();
            System.out.println(f);
        }
    }
}

