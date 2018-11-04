package utils;

import java.util.Formatter;

public class HexUtil {

    public static String byteArray2Hex(final byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

}
