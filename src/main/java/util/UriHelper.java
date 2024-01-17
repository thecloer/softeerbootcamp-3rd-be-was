package util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UriHelper {

    public static String encode(String path) {
        return URLEncoder.encode(path, StandardCharsets.UTF_8);
    }
}