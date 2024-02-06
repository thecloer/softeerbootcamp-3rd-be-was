package util.http;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod of(String method) {
        return HttpMethod.valueOf(method.toUpperCase());
    }

    public String getValue() {
        return method;
    }
}
