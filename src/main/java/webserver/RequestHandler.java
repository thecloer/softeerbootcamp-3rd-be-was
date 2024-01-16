package webserver;

import java.io.*;
import java.net.Socket;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = RequestParser.parse(in);
            HttpResponse response = new HttpResponse(out);

            logger.debug("[{} {}] {}", request.getProtocol(), request.getMethod(), request.getPath());

            BiConsumer<HttpRequest, HttpResponse> handler = Router.route(request);

            handler.accept(request, response);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
