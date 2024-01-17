package controller;

import util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourceController {

    private static final String TEMPLATE = "src/main/resources/templates";
    private static final String STATIC = "src/main/resources/static";

    public void resourceHandler(HttpRequest request, HttpResponse response) {
        String path = request.getPath();

        if(path.equals("/"))
            path = "/index.html";

        ContentType contentType = ContentType.getContentType(path);
        String base = contentType == ContentType.HTML ? TEMPLATE : STATIC;

        try {
            byte[] body = read(base, path);
            response.status(HttpStatus.OK)
                    .contentType(ContentType.getContentType(path))
                    .body(body)
                    .send();
        } catch (IOException e) {
            response.status(HttpStatus.FOUND)
                    .addHeader("Location", "/404.html")
                    .send();
        }
    }

    private byte[] read(String base, String path) throws IOException {
        return Files.readAllBytes(new File(base + path).toPath());
    }
}