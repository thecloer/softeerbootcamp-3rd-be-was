package controller;

import util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourceController {

    private static final String TEMPLATE = "src/main/resources/templates";
    private static final String STATIC = "src/main/resources/static";

    public static void resourceHandler(HttpRequest request, HttpResponse response) {
        String url = request.getUri().getPath();
        ContentType contentType = ContentType.getContentType(url);
        String base = contentType == ContentType.HTML ? TEMPLATE : STATIC;

        try {
            byte[] body = read(base, url);
            response.status(HttpStatus.OK)
                    .contentType(ContentType.getContentType(url))
                    .body(body)
                    .send();
        } catch (IOException e) {
            response.status(HttpStatus.NOT_FOUND)
                    .send();
        }
    }

    private static byte[] read(String base, String url) throws IOException {
        return Files.readAllBytes(new File(base + url).toPath());
    }
}