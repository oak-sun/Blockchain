package blockchain.util;

import lombok.NoArgsConstructor;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor
public class StringUtil {
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input){
        try {
            var digest = MessageDigest
                                   .getInstance("SHA-256");
            /* Applies sha256 to our input */
            var hash = digest
                              .digest(input.getBytes(
                                       StandardCharsets.UTF_8));
            var hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}