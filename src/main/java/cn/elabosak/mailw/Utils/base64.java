package cn.elabosak.mailw.Utils;

import java.util.Base64;

public class base64 {

    /**
     * @param string Need to be encrypted
     * @return Encrypted data
     */
    public static String encoder(String string) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(string.getBytes());
    }

    /**
     * @param string Encrypted data
     * @return Decrypted data
     */
    public static String decoder(String string) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(string));
    }
}
