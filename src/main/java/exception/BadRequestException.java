package exception;

import util.http.HttpStatus;

public class BadRequestException extends HttpBaseException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
