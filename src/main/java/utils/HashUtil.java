package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe utile permettant de hasher des informations
 */
public class HashUtil {

    /**
     * Donner le hash d'un fichier
     * @param filePath
     * @return le hash au format SHA-256
     */
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
