package util.http;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpResponse implements HttpMessage {

    private final HttpStatus status;
    private final ContentType contentType;
    private final Map<String, String> additionalHeaders;
    private final byte[] body;
    private final List<String> cookies;

    private HttpResponse(HttpStatus status, ContentType contentType, Map<String, String> additionalHeaders, byte[] body, List<String> cookies) {
        this.status = status;
        this.contentType = contentType;
        this.additionalHeaders = additionalHeaders;
        this.body = body;
        this.cookies = cookies;
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
    public Integer getBodyLength() {
        return body.length;
    }

    @Override
    public byte[] getBody() {
        return body;
    }

    @Override
    public String getAdditionalHeaders() {
        StringBuilder additionalHeader = new StringBuilder();
        for (Map.Entry<String, String> entry : additionalHeaders.entrySet()) {
            additionalHeader.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        return additionalHeader.toString();
    }

    @Override
    public String getCookies() {
        StringBuilder cookieBuilder = new StringBuilder();
        for (String cookie : cookies) {
            cookieBuilder.append("Set-Cookie: ").append(cookie).append("\r\n");
        }
        return cookieBuilder.toString();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("HttpResponse ")
                .append("{ status=").append(status.getCode())
                .append(", contentType=").append(contentType.getValue())
                .append(", additionalHeader=").append(additionalHeaders)
                .append(", body=").append(new String(body))
                .append(" }")
                .toString();
    }

    public void setHeader(String key, String value) {
        additionalHeaders.put(key, value);
    }

    public void addCookie(String cookie) {
        cookies.add(cookie);
    }

    public static class Builder {
        private final Map<String, String> additionalHeaders = new HashMap<>();
        private final List<String> cookies = new ArrayList<>();
        private HttpStatus status;
        private ContentType contentType = ContentType.NONE;
        private byte[] body = new byte[0];

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder contentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public Builder body(String body) {
            this.body = body.getBytes(StandardCharsets.UTF_8);
            return this;
        }

        public Builder setHeader(String key, String value) {
            additionalHeaders.put(key, value);
            return this;
        }

        public Builder addCookie(String cookie) {
            cookies.add(cookie);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(status, contentType, additionalHeaders, body, cookies);
        }
    }
}
