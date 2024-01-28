package webserver;

import java.io.*;
import java.net.Socket;

import middleware.AuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;
import util.http.ContentType;
import util.http.HttpMessage;
import util.http.HttpRequest;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final RequestPipeline requestPipeline = new RequestPipeline();

    static {
        requestPipeline.use(new AuthFilter());
    }

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = RequestParser.parse(in);

            logger.debug("[{} {}] {}", request.getProtocol(), request.getMethod(), request.getUri());

            HttpMessage response = requestPipeline.process(request);

            send(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void send(OutputStream out, HttpMessage response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeBytes("HTTP/1.1 " + response.getStatusCode() + " " + response.getStatusMessage() + " \r\n");
        if (response.getContentType() != ContentType.NONE)
            dos.writeBytes("Content-Type: " + response.getContentType() + ";charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + response.getBodyLength() + "\r\n");
        dos.writeBytes(response.getAdditionalHeaders());
        dos.writeBytes(response.getCookies());
        dos.writeBytes("\r\n");

        if (response.getBodyLength() > 0)
            dos.write(response.getBody(), 0, response.getBodyLength());

        dos.flush();
    }
}
