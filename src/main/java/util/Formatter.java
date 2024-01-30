package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    static private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }
}
