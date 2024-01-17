package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final OutputStream out;
    private HttpStatus status = HttpStatus.OK;
    private ContentType contentType = ContentType.OCTET_STREAM;
    private final StringBuilder additionalHeader = new StringBuilder();
    private byte[] body = new byte[0];

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    public HttpResponse status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public HttpResponse contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public HttpResponse body(byte[] body) {
        this.body = body;
        return this;
    }

    public HttpResponse addHeader(String key, String value) {
        additionalHeader.append(key).append(": ").append(value).append("\r\n");
        return this;
    }

    public void send() {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes("HTTP/1.1 " + status.getCode() + " " + status.getMessage() + " \r\n");
            dos.writeBytes("Content-Type: " + contentType.getValue() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes(additionalHeader.toString());
            dos.writeBytes("\r\n");

            if(body.length > 0)
                dos.write(body, 0, body.length);

            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("HttpResponse ")
                .append("{ status=").append(status.getCode())
                .append(", contentType=").append(contentType.getValue())
                .append(", additionalHeader=").append(additionalHeader)
                .append(", body=").append(new String(body))
                .append(" }")
                .toString();
    }
}
