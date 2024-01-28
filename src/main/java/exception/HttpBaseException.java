package exception;

import util.JSON;
import util.http.ContentType;
import util.http.HttpMessage;
import util.http.HttpResponse;
import util.http.HttpStatus;

import java.util.Map;

public class HttpBaseException extends RuntimeException implements HttpMessage {

    private final HttpResponse response;

    public HttpBaseException(HttpStatus status) {
        this.response = new HttpResponse()
                .setStatus(status);
    }

    public HttpBaseException(HttpStatus status, String message) {
        super(message);
        String jsonMessage = JSON.stringify(Map.of("message", message));
        this.response = new HttpResponse()
                .setStatus(status)
                .setContentType(ContentType.JSON)
                .setBody(jsonMessage);
    }

    @Override
    public Integer getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public String getStatusMessage() {
        return response.getStatusMessage();
    }

    @Override
    public ContentType getContentType() {
        return response.getContentType();
    }

    @Override
    public String getAdditionalHeaders() {
        return response.getAdditionalHeaders();
    }

    @Override
    public Integer getBodyLength() {
        return response.getBodyLength();
    }

    @Override
    public byte[] getBody() {
        return response.getBody();
    }

    @Override
    public String getCookies() {
        return response.getCookies();
    }

    public HttpBaseException setHeader(String key, String value) {
        response.setHeader(key, value);
        return this;
    }

    public HttpBaseException addCookie(String cookie) {
        response.addCookie(cookie);
        return this;
    }
}
