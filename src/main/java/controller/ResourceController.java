package controller;

import exception.RedirectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileReader;
import util.http.*;

import java.io.IOException;

public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private static final String TEMPLATE = "src/main/resources/templates";
    private static final String STATIC = "src/main/resources/static";

    public HttpResponse resourceHandler(HttpRequest request) {
        String path = redirectRoot(request.getMethod(), request.getPath());
        ContentType contentType = ContentType.getContentType(path);
        String base = (contentType == ContentType.HTML) ? TEMPLATE : STATIC;

        byte[] body = read(base + path);
        return new HttpResponse()
                .setStatus(HttpStatus.OK)
                .setContentType(contentType)
                .setBody(body);
    }

    private String redirectRoot(HttpMethod method, String path) {
        if (method == HttpMethod.GET && path.equals("/")) {
            return "/index.html";
        }
        return path;
    }

    private byte[] read(String path) {
        try {
            return FileReader.read(path);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RedirectException("/404.html");
        }
    }
}
