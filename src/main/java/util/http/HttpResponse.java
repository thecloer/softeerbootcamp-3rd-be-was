package util.http;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpResponse implements HttpMessage {

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private ContentType contentType = ContentType.NONE;
    private final Map<String, String> fields = new HashMap<>();
    private byte[] body = new byte[0];
    private final List<String> cookies = new ArrayList<>();

    public HttpResponse() {
    }

    @Override
    public Integer getStatusCode() {
        return status.getCode();
    }

    @Override
    public String getStatusMessage() {
        return status.getMessage();
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public String getFields() {
        StringBuilder additionalHeader = new StringBuilder();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            additionalHeader.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        return additionalHeader.toString();
    }

    @Override
    public Integer getBodyLength() {
        return body.length;
    }

    @Override
    public byte[] getBody() {
        return body;
    }


    @Override
    public String getCookies() {
        StringBuilder cookieBuilder = new StringBuilder();
        for (String cookie : cookies) {
            cookieBuilder.append("Set-Cookie: ").append(cookie).append("\r\n");
        }
        return cookieBuilder.toString();
    }

    public HttpResponse setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public HttpResponse setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public HttpResponse setField(String key, String value) {
        fields.put(key, value);
        return this;
    }

    public HttpResponse addCookie(String cookie) {
        cookies.add(cookie);
        return this;
    }

    public HttpResponse setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public HttpResponse setBody(byte[] body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("HttpResponse ")
                .append("{ status=").append(status.getCode())
                .append(", contentType=").append(contentType.getValue())
                .append(", fields=").append(fields)
                .append(", body=").append(new String(body))
                .append(" }")
                .toString();
    }
}
