import com.gdtcs.batch.dataCollectors.util.NewGpkiUtil;
import com.gdtcs.batch.dataCollectors.util.ShareGpki;

public class CertTest {

	public static void main(String[] args) throws Exception {
		final String charset = "UTF-8";
		final String certPassword = "bisco@7622";
		final String serverId = "SVRB552587005";
		final String envCertFilePathName = "C:/Users/djjeong/Desktop/gpki_certs/cert/SVRB552587005_env.cer";
		final String envPrivateKeyFilePathName = "C:/Users/djjeong/Desktop/gpki_certs/cert/SVRB552587005_env.key";
		final String sigCertFilePathName = "C:/Users/djjeong/Desktop/gpki_certs/cert/SVRB552587005_sig.cer";
		final String sigPrivateKeyFilePathName = "C:/Users/djjeong/Desktop/gpki_certs/cert/SVRB552587005_sig.key";
		final String gpkiLicPath = "C:/Users/djjeong/Desktop/gpki_certs/conf";

		NewGpkiUtil g;
		ShareGpki shareGpki = ShareGpki.builder()
			.certPassword(certPassword)
			.serverId(serverId)
			.envCertFilePathName(envCertFilePathName)
			.envPrivateKeyFilePathName(envPrivateKeyFilePathName)
			.sigCertFilePathName(sigCertFilePathName)
			.sigPrivateKeyFilePathName(sigPrivateKeyFilePathName)
			.gpkiLicPath(gpkiLicPath)
			.build();

		g = shareGpki.getGpkiUtil();
		String encoded;
		byte[] encrypted = g.encrypt("test".getBytes(charset), serverId);
		byte[] signed = g.sign(encrypted);
		encoded = g.encode(signed);
		System.out.println(">>>>> findVeteranVehicle requestXml : {} " + encoded);

		String decrypted;
		byte[] decoded;
		decoded = g.decode(encoded);
		byte[] validated = g.validate(decoded);
		decrypted = new String(g.decrypt(validated), charset);
		System.out.println(">>>>> findVeteranVehicle requestXml : {} " + decrypted);
	}

}
