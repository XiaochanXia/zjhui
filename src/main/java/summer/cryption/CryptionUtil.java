package summer.cryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.Console;
import java.io.IOException;
import java.security.SecureRandom;


public class CryptionUtil {

    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();
    private final static String DES = "DES";
    private final static String KEY = "KeY!$%&90^KYe!";

    /**
     * Encrypt username or email using base64
     * @param word
     * @return encoder
     */
    public static String encryptByBase64(String word) {
        return encoder.encode(word.getBytes());
    }

    /**
     * Decrypt username or email using base64
     * @param encode
     * @return decoder
     * @throws IOException
     */
    public static String decryptByBase64(String encode) throws IOException{
        byte[] bytes = decoder.decodeBuffer(encode);
        return new String(bytes);
    }

    /**
     * Decrypt using DES
     * @param encoder
     * @return password
     */
    public static String decrypt(String encoder) throws Exception{
        SecureRandom random = new SecureRandom();

        DESKeySpec desKeySpec = new DESKeySpec(KEY.getBytes());

        SecretKeyFactory factory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = factory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, random);

        //because encoder is encoded by BASE64
        byte[] bytes = cipher.doFinal(decoder.decodeBuffer(encoder));

        if (null == bytes) {
            return null;
        }
        return new String(bytes);
    }

    public static String encrypt(String password) throws Exception{
        //conduct one reliable random;
        SecureRandom random = new SecureRandom();

        //conduct one dks object from original KEY;
        DESKeySpec desKeySpec = new DESKeySpec(KEY.getBytes());

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        //Cipher object to encrypt;
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);

        //get byte encoder;
        byte[] bytes = cipher.doFinal(password.getBytes());

        if (null == bytes) {
            return null;
        }

        return encoder.encode(bytes);
    }

    public static void main(String[] args) throws Exception{
        //write your own test case;
    }
}