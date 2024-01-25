package controller;

import util.http.ContentType;
import util.http.HttpRequest;
import util.http.HttpResponse;
import util.http.HttpStatus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ResourceController {

    private static final String TEMPLATE = "src/main/resources/templates";
    private static final String STATIC = "src/main/resources/static";

    public HttpResponse resourceHandler(HttpRequest request) {
        try {
            String path = redirectRoot(request.getMethod(), request.getPath());
            ContentType contentType = ContentType.getContentType(path);
            String base = (contentType == ContentType.HTML) ? TEMPLATE : STATIC;

            byte[] body = read(base, path);
            return new HttpResponse.Builder()
                    .status(HttpStatus.OK)
                    .contentType(contentType)
                    .body(body)
                    .build();
        } catch (IOException e) {
            return new HttpResponse.Builder()
                    .status(HttpStatus.FOUND)
                    .setHeader("Location", "/404.html")
                    .build();
        }
    }

    private String redirectRoot(String method, String path) {
        if ("GET".equals(method) && path.equals("/")) {
            return "/index.html";
        }
        return path;
    }

    private byte[] read(String base, String path) throws IOException {
        File file = new File(base + path);
        if (file.length() > Integer.MAX_VALUE) {
            throw new IOException("파일의 크기가 너무 큽니다.");
        }

        try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] data = new byte[(int) file.length()];
            int totalBytesRead = 0;
            int bytesRead;

            while (totalBytesRead < data.length && (bytesRead = bis.read(data, totalBytesRead, data.length - totalBytesRead)) != -1) {
                totalBytesRead += bytesRead;
            }

            if (totalBytesRead != data.length) {
                throw new IOException("파일 전체를 읽는데 실패했습니다.: " + file.getName());
            }

            return data;
        }
    }
}
