package custom;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import custom.extras.values.Constants;
import custom.extras.MyDebugger;


public class Encryptor {
    public static String encrypt(String key1, String key2, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));

            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"),
                    "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            value = value.concat(Constants.ENCRYPTION_STATUS_OK);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            MyDebugger.log("encrypted", Base64.encodeToString(encrypted, Base64.DEFAULT));
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String key1, String key2, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));

            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"),
                    "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));

            String decrypted = new String(original);
            String status = decrypted.substring(decrypted.length() - Constants.ENCRYPTION_STATUS_OK_LENGTH, decrypted.length());
            if(!status.equals(Constants.ENCRYPTION_STATUS_OK)) {
                MyDebugger.log("STATUS WRONG PASSWORD");
                return Constants.ENCRYPTION_STATUS_WRONG_PASS;
            }
            return decrypted.substring(0, decrypted.length() - Constants.ENCRYPTION_STATUS_OK_LENGTH);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}