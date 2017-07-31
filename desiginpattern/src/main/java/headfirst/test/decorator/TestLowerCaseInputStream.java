package headfirst.test.decorator;


import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class TestLowerCaseInputStream extends FilterInputStream {
    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param in the underlying input stream, or <code>null</code> if
     *           this instance is to be created without an underlying stream.
     */
    protected TestLowerCaseInputStream(InputStream in) {
        super(in);
    }

    public int read() throws IOException {
        int c = super.read();
        return (c == -1 ? c : Character.toLowerCase((char) c));
    }

    public int read(byte[] b, int offerset, int len) throws IOException {
        int result = super.read(b, offerset, len);
        for (int i = offerset; i < offerset + result; i++) {
            b[i] = (byte) Character.toLowerCase(b[i]);
        }
        return result;
    }
}
