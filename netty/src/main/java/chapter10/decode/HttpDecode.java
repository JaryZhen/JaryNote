package chapter10.decode;

import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;

/**
 * Created by Jary on 2017/12/11 0011.
 */
public class HttpDecode extends HttpObjectDecoder {
    @Override
    protected boolean isDecodingRequest() {
        return false;
    }

    @Override
    protected HttpMessage createMessage(String[] initialLine) throws Exception {
        return null;
    }

    @Override
    protected HttpMessage createInvalidMessage() {
        return null;
    }
}
