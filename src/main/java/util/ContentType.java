package util;

public enum ContentType {
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

    private final String type;

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
