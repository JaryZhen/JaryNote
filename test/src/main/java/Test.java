import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by Jary on 2017/11/8 0008.
 */
public class Test {

    public static void main(String[] args) throws Exception {

        Test a = new Test(); //9dahxn76jr9ghlrfequiua8cflnlwlxfy8krtl3s
        String code = a.authToken("9dahxn76jr9ghlrfequiua8cflnlwlxfy8krtl3s", "nxog09md", "0a1b4118dd954ec3bcc69da5138bdb96", System.currentTimeMillis());
        System.out.print(code);
    }
    public String authToken(String secret, String project, String ai, Long tm) throws Exception {
        System.out.println(tm);
        String message = "POST\n/auth/token\nproject=" + project + "&ai=" + ai + "&tm=" + tm;
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signature = hmac.doFinal(message.getBytes("UTF-8"));
        return Hex.encodeHexString(signature);
    }}
