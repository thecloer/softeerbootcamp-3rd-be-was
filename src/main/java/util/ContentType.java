package util;

import java.util.HashMap;
import java.util.Map;

public enum ContentType {

    OCTET_STREAM("application/octet-stream"),
    HTML("text/html"),
    CSS("text/css"),
    JS("application/javascript"),
    ICO("image/x-icon"),
    PNG("image/png"),
    SVG("image/svg+xml"),
    PLAIN("text/plain"),

    // font: https://www.iana.org/assignments/media-types/media-types.xhtml#font
    EOT("application/vnd.ms-fontobject"),
    TTF("font/ttf"),
    WOFF("font/woff"),
    WOFF2("font/woff2");

    private static final Map<String, ContentType> CONTENT_TYPE_MAP = new HashMap<>();
    static {
        CONTENT_TYPE_MAP.put("html", HTML);
        CONTENT_TYPE_MAP.put("css", CSS);
        CONTENT_TYPE_MAP.put("js", JS);
        CONTENT_TYPE_MAP.put("ico", ICO);
        CONTENT_TYPE_MAP.put("png", PNG);
        CONTENT_TYPE_MAP.put("svg", SVG);
        CONTENT_TYPE_MAP.put("txt", PLAIN);
        CONTENT_TYPE_MAP.put("eot", EOT);
        CONTENT_TYPE_MAP.put("ttf", TTF);
        CONTENT_TYPE_MAP.put("woff", WOFF);
        CONTENT_TYPE_MAP.put("woff2", WOFF2);
    }

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public static ContentType getContentType(String path) {
        return CONTENT_TYPE_MAP.getOrDefault(UriHelper.extractExtension(path), ContentType.OCTET_STREAM);
    }

    public String getValue() {
        return value;
    }
}