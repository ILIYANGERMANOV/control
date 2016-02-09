package custom;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import custom.extras.values.Constants;
import custom.extras.MyDebugger;

@TargetApi(Build.VERSION_CODES.FROYO)
public class MCrypt {

    private String iv;
    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;
    private Cipher cipher;

    public MCrypt(String pass) {


        try {
            iv = SHA1(pass).substring(0, 16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //iv= "fedcba9876543210";
        //iv    ="kolioepi4zadvama";


        //SecretKey = generatePBKDF(pass).substring(30,62);
        String secretKey = bytesToHex(getSha256(pass)).substring(30, 62);

        //SecretKey = "0123456789abcdef";

        ivspec = new IvParameterSpec(iv.getBytes());

        keyspec = new SecretKeySpec(secretKey.getBytes(), "AES");

        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public byte[] encrypt(String text) throws Exception
    //public String encrypt(String text) throws Exception
    {
        if (text == null || text.length() == 0)
            throw new Exception("Empty string");

        byte[] encrypted;

        try {
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            text = text.concat(Constants.ENCRYPTION_STATUS_OK);
            encrypted = cipher.doFinal(padString(text).getBytes());
        } catch (Exception e) {
            throw new Exception("[encrypt] " + e.getMessage());
        }

        return encrypted;
        //return SecretKey;

    }

    public String decrypt(String code) {
        if (code == null || code.length() == 0)
            return null;

        String decrypted;

        try {
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            decrypted = new String(cipher.doFinal(hexToBytes(code)));

            String status = decrypted.substring(decrypted.trim().length() - Constants.ENCRYPTION_STATUS_OK_LENGTH);
            if (!status.trim().equals(Constants.ENCRYPTION_STATUS_OK)) {
                MyDebugger.log("MCrypt", "STATUS WRONG PASSWORD");
                return Constants.ENCRYPTION_STATUS_WRONG_PASS;
            }
        } catch (Exception e) {
            MyDebugger.log("decrypt() exception", e.getMessage());
            return null;
        }
        return decrypted.substring(0, decrypted.trim().length() - Constants.ENCRYPTION_STATUS_OK_LENGTH);
    }


    public static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        }

        String str = "";
        for (byte aData : data) {
            if ((aData & 0xFF) < 16)
                str = str + "0" + Integer.toHexString(aData & 0xFF);
            else
                str = str + Integer.toHexString(aData & 0xFF);
        }
        return str;
    }


    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }


    private static String padString(String source) {
        char paddingChar = ' ';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;

        for (int i = 0; i < padLength; i++) {
            source += paddingChar;
        }

        return source;
    }


    public byte[] getSha256(String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return null;
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }
}
