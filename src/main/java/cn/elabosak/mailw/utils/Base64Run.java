package cn.elabosak.mailw.utils;

import java.util.Base64;

/**
 * @author ElaBosak
 */
public class Base64Run {

    /**
     * @param string Need to be encrypted
     * @return Encrypted data
     */
    public static String encoder(String string) {
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        return encoder.encodeToString(string.getBytes());
    }

    /**
     * @param string Encrypted data
     * @return Decrypted data
     */
    public static String decoder(String string) {
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        return new String(decoder.decode(string));
    }
}
