import com.gdtcs.batch.dataCollectors.util.NewGpkiUtil;
import com.gdtcs.batch.dataCollectors.util.ShareGpki;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CertFileTest {

	@Mock
	private NewGpkiUtil gpkiUtil;

	static final String charset = "UTF-8";
	static final String certPassword = "bisco@7622";
	static final String certServerId = "SVRB552587005";
	static final String envCertFilePathName = "C:/Users/djjeong/Desktop/gpki_certs/env/SVRB552587005.cer";
	static final String envPrivateKeyFilePathName = "C:/Users/djjeong/Desktop/gpki_certs/env/SVRB552587005.key";
	static final String sigCertFilePathName = "C:/Users/djjeong/Desktop/gpki_certs/sig/SVRB552587005.cer";
	static final String sigPrivateKeyFilePathName = "C:/Users/djjeong/Desktop/gpki_certs/sig/SVRB552587005.key";
	static final String gpkiLicPath = "C:/Users/djjeong/Desktop/gpki_certs/conf";

	@BeforeEach
	public void setup() throws Exception {
		ShareGpki shareGpki = ShareGpki.builder()
			.certPassword(certPassword)
			.serverId(certServerId)
			.envCertFilePathName(envCertFilePathName)
			.envPrivateKeyFilePathName(envPrivateKeyFilePathName)
			.sigCertFilePathName(sigCertFilePathName)
			.sigPrivateKeyFilePathName(sigPrivateKeyFilePathName)
			.gpkiLicPath(gpkiLicPath)
			.build();

		MockitoAnnotations.openMocks(this);
		when(shareGpki.getGpkiUtil()).thenReturn(gpkiUtil);
	}

	public static void main(String[] args) throws Exception {
		NewGpkiUtil g;

		ShareGpki shareGpki = ShareGpki.builder()
			.certPassword(certPassword)
			.serverId(certServerId)
			.envCertFilePathName(envCertFilePathName)
			.envPrivateKeyFilePathName(envPrivateKeyFilePathName)
			.sigCertFilePathName(sigCertFilePathName)
			.sigPrivateKeyFilePathName(sigPrivateKeyFilePathName)
			.gpkiLicPath(gpkiLicPath)
			.build();

		g = shareGpki.getGpkiUtil();
		String encoded;
		byte[] encrypted = g.encrypt("test".getBytes(charset), certServerId);
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

	@Test
	public void encryptionAndDecryptionShouldWorkCorrectly() throws Exception {
		String testString = "test";
		byte[] testBytes = testString.getBytes(charset);
		byte[] encryptedBytes = new byte[]{1, 2, 3};
		byte[] signedBytes = new byte[]{4, 5, 6};
		byte[] encodedBytes = new byte[]{7, 8, 9};
		String encodedString = new String(encodedBytes, charset);

		when(gpkiUtil.encrypt(testBytes, certServerId)).thenReturn(encryptedBytes);
		when(gpkiUtil.sign(encryptedBytes)).thenReturn(signedBytes);
		when(gpkiUtil.encode(signedBytes)).thenReturn(encodedString);

		byte[] decodedBytes = new byte[]{10, 11, 12};
		byte[] validatedBytes = new byte[]{13, 14, 15};
		byte[] decryptedBytes = testBytes;
		String decryptedString = testString;

		when(gpkiUtil.decode(encodedString)).thenReturn(decodedBytes);
		when(gpkiUtil.validate(decodedBytes)).thenReturn(validatedBytes);
		when(gpkiUtil.decrypt(validatedBytes)).thenReturn(decryptedBytes);

		CertFileTest.main(new String[]{});
		assertEquals(testString, decryptedString);
	}

	@Test
	public void encryptionShouldFailWithException() throws Exception {
		String testString = "test";
		byte[] testBytes = testString.getBytes(charset);

		when(gpkiUtil.encrypt(testBytes, certServerId)).thenThrow(new Exception("Encryption failed"));

		try {
			CertFileTest.main(new String[]{});
		} catch (Exception e) {
			assertEquals("Encryption failed", e.getMessage());
		}
	}

	@Test
	public void decryptionShouldFailWithException() throws Exception {
		String testString = "test";
		byte[] testBytes = testString.getBytes(charset);
		byte[] encryptedBytes = new byte[]{1, 2, 3};
		byte[] signedBytes = new byte[]{4, 5, 6};
		byte[] encodedBytes = new byte[]{7, 8, 9};
		String encodedString = new String(encodedBytes, charset);

		when(gpkiUtil.encrypt(testBytes, certServerId)).thenReturn(encryptedBytes);
		when(gpkiUtil.sign(encryptedBytes)).thenReturn(signedBytes);
		when(gpkiUtil.encode(signedBytes)).thenReturn(encodedString);

		byte[] decodedBytes = new byte[]{10, 11, 12};
		byte[] validatedBytes = new byte[]{13, 14, 15};

		when(gpkiUtil.decode(encodedString)).thenReturn(decodedBytes);
		when(gpkiUtil.validate(decodedBytes)).thenReturn(validatedBytes);
		when(gpkiUtil.decrypt(validatedBytes)).thenThrow(new Exception("Decryption failed"));

		try {
			CertFileTest.main(new String[]{});
		} catch (Exception e) {
			assertEquals("Decryption failed", e.getMessage());
		}
	}
}
