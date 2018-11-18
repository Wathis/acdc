package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    public static String sha256File(String filePath) {
        byte[] hash = null;
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            hash = MessageDigest.getInstance("SHA-256").digest(b);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return HexUtil.byteArray2Hex(hash);
    }

}
