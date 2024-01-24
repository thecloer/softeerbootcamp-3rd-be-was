package exception;

import util.http.HttpStatus;

public class RedirectException extends HttpBaseException {
    public RedirectException(String location) {
        super(HttpStatus.FOUND);
        setHeader("Location", location);
    }
}
