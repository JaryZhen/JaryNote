package note.zk.cpt07;
import java.security.NoSuchAlgorithmException;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

//瀵光�渦sername:password鈥濊繘琛岀紪鐮�
public class DigestAuthenticationProviderUsage {
	public static void main( String[] args ) throws NoSuchAlgorithmException {
		System.out.println( DigestAuthenticationProvider.generateDigest( "super:zk-book" ) );
	}
}