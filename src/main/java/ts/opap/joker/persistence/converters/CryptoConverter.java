package ts.opap.joker.persistence.converters;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

	private static final byte[] KEY = "h0t3lg3n!0u$.c0.".getBytes();

//	private Key key;

	public CryptoConverter() throws Exception {
		// this.key = new KeyLoader().load();
//		this.key = new SecretKeySpec(KEY, ALGORITHM);
	}

	@Override
	public String convertToDatabaseColumn(String value) {

		try {
			Cipher c = Cipher.getInstance(TRANSFORMATION);
			c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, ALGORITHM));

			return Base64.getEncoder().encodeToString(c.doFinal(value.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(String value) {
		try {
			Cipher c = Cipher.getInstance(TRANSFORMATION);
			c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY, ALGORITHM));
			return new String(c.doFinal(Base64.getDecoder().decode(value)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
