package exception;

import util.http.HttpStatus;

public class RedirectException extends HttpBaseException {
    public RedirectException(String path) {
        super(HttpStatus.FOUND);
        setHeader("Location", path);
    }
}
