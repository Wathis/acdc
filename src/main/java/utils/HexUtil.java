package utils;

import java.util.Formatter;

/**
 * Classe utile permettant de manipuler l'hexadecimale
 */
public class HexUtil {

    /**
     * Permet de convertir un tableau d'octets en chaine de caractères
     * @param bytes
     * @return
     */
    public static String byteArray2Hex(final byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

}
