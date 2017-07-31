package classes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static jdk.nashorn.internal.objects.Global.print;


/**
 * Created by Jary on 2017/7/11 0011.
 */

public class ToyTest {
    static int leth = 0x8FF;
    public static void main(String[] args) throws IOException {
      /*  String [] hs = "heloo heloo".split(" ");
        System.out.println(hs[0].hashCode());
        System.out.println(hs[1].hashCode());

        MappedByteBuffer out = new RandomAccessFile("README.md1","rw").getChannel().map(FileChannel.MapMode.READ_WRITE,0,leth);

        for(int i =0 ; i<leth ; i++){
            out.put((byte)'x');

        }
        print("wewe");
        for (int i =leth/2; i< leth/2+6 ;i++){
            print(out.get(i));
        }
        */

      MyClass a =  MyClass.getInstatans();
    }
}
