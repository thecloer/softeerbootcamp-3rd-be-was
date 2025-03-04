package exception;

import util.http.HttpStatus;

public class InternalServerErrorException extends HttpBaseException {
    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
