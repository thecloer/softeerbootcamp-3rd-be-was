package util.http;

import java.nio.charset.StandardCharsets;

public class HttpResponse {

    private final HttpStatus status;
    private final ContentType contentType;
    private final String additionalHeaders;
    private final byte[] body;

    public HttpResponse(HttpStatus status, ContentType contentType, String additionalHeaders, byte[] body) {
        this.status = status;
        this.contentType = contentType;
        this.additionalHeaders = additionalHeaders;
        this.body = body;
    }

    public Integer getStatusCode() {
        return status.getCode();
    }

    public String getStatusMessage() {
        return status.getMessage();
    }

    public ContentType getContentType() {
        return contentType;
    }

    public Integer getBodyLength() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }

    public String getAdditionalHeaders() {
        return additionalHeaders;
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

    public static class Builder {
        private final StringBuilder additionalHeader = new StringBuilder();
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

        public Builder addHeader(String key, String value) {
            additionalHeader.append(key).append(": ").append(value).append("\r\n");
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(status, contentType, additionalHeader.toString(), body);
        }
    }
}
