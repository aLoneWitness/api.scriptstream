package scriptstream.util;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class EncryptionManager {
    private Key encryptionKey;

    public EncryptionManager() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            this.encryptionKey = keyGen.generateKey();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Key getEncryptionKey() {
        return encryptionKey;
    }
}
