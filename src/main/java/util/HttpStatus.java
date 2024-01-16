package util;

import java.util.HashMap;
import java.util.Map;

public enum HttpStatus {
    OK("200", "OK"),
    CREATED("201", "Created"),
    FOUND("302", "Found"),
    BAD_REQUEST("400", "Bad Request"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error");

    private static final Map<Integer, HttpStatus> CODE_MAP = new HashMap<>();
    static {
        CODE_MAP.put(200, OK);
        CODE_MAP.put(201, CREATED);
        CODE_MAP.put(302, FOUND);
        CODE_MAP.put(400, BAD_REQUEST);
        CODE_MAP.put(403, FORBIDDEN);
        CODE_MAP.put(404, NOT_FOUND);
        CODE_MAP.put(500, INTERNAL_SERVER_ERROR);
    }

    public static HttpStatus getStatus(Integer code) {
        return CODE_MAP.getOrDefault(code, INTERNAL_SERVER_ERROR);
    }

    private final String code;
    private final String message;

    HttpStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
