package util;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/javascript"),
    ICO("image/x-icon"),
    PNG("image/png"),
    SVG("image/svg+xml"),
    PLAIN("text/plain"),

    // font
    EOT("application/vnd.ms-fontobject"),
    TTF("application/x-font-ttf"),
    WOFF("application/font-woff"),
    WOFF2("application/font-woff2")
    ;


    private final String type;

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
