package util;

import java.util.HashMap;
import java.util.Map;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private static final Map<Integer, HttpStatus> CODE_MAP = new HashMap<>();
    static {
        CODE_MAP.put(OK.code, OK);
        CODE_MAP.put(CREATED.code, CREATED);
        CODE_MAP.put(FOUND.code, FOUND);
        CODE_MAP.put(BAD_REQUEST.code, BAD_REQUEST);
        CODE_MAP.put(FORBIDDEN.code, FORBIDDEN);
        CODE_MAP.put(NOT_FOUND.code, NOT_FOUND);
        CODE_MAP.put(INTERNAL_SERVER_ERROR.code, INTERNAL_SERVER_ERROR);
    }

    public static HttpStatus getStatus(Integer code) {
        return CODE_MAP.getOrDefault(code, INTERNAL_SERVER_ERROR);
    }

    private final Integer code;
    private final String message;

    HttpStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
