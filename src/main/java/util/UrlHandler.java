package util;

import java.util.Map;

public class UrlHandler {

    private static final Map<String, ContentType> CONTENT_TYPE_MAP = Map.of(
            "html", ContentType.HTML,
            "css", ContentType.CSS,
            "js", ContentType.JS,
            "ico", ContentType.ICO
    );

    public static ContentType getContentType(String url) {
        String extension = getExtension(url);

        if(extension == null)
            return ContentType.PLAIN;

        return CONTENT_TYPE_MAP.getOrDefault(extension, ContentType.PLAIN);
    }

    private static String getExtension(String url) {
        int index = url.lastIndexOf(".");

        if (index == -1)
            return null;

        return url.substring(index + 1);
    }
}