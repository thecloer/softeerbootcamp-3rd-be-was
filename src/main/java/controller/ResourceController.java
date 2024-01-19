package controller;

import util.http.ContentType;
import util.http.HttpRequest;
import util.http.HttpResponse;
import util.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourceController {

    private static final String TEMPLATE = "src/main/resources/templates";
    private static final String STATIC = "src/main/resources/static";

    public HttpResponse resourceHandler(HttpRequest request) {
        String path = redirectRoot(request.getMethod(), request.getPath());
        ContentType contentType = ContentType.getContentType(path);
        String base = (contentType == ContentType.HTML) ? TEMPLATE : STATIC;

        HttpResponse.Builder response = new HttpResponse.Builder();
        try {
            byte[] body = read(base, path);
            response
                    .status(HttpStatus.OK)
                    .contentType(contentType)
                    .body(body);
        } catch (IOException e) {
            response
                    .status(HttpStatus.FOUND)
                    .addHeader("Location", "/404.html");
        }
        return response.build();
    }

    private String redirectRoot(String method, String path) {
        if ("GET".equals(method) && path.equals("/")) {
            return "/index.html";
        }
        return path;
    }

    private byte[] read(String base, String path) throws IOException {
        return Files.readAllBytes(new File(base + path).toPath());
    }
}
