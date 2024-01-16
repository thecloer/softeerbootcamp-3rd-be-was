package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = RequestParser.parse(in);
            HttpResponse response = new HttpResponse(out);

            logger.debug("[{} {}] {}", request.getProtocol(), request.getMethod(), request.getPath());

            URI uri = request.getUri();
            ContentType type = ContentType.getContentType(uri.getPath());
            byte[] body = ResourceHandler.getResource(type, uri.getPath());

            response.status(HttpStatus.OK)
                    .contentType(type)
                    .body(body)
                    .send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
