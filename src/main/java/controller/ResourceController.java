package controller;

import exception.RedirectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.http.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private static final String TEMPLATE = "src/main/resources/templates";
    private static final String STATIC = "src/main/resources/static";

    public HttpResponse resourceHandler(HttpRequest request) {
        String path = redirectRoot(request.getMethod(), request.getPath());
        ContentType contentType = ContentType.getContentType(path);
        String base = (contentType == ContentType.HTML) ? TEMPLATE : STATIC;

        byte[] body = read(base, path);
        return new HttpResponse.Builder()
                .status(HttpStatus.OK)
                .contentType(contentType)
                .body(body)
                .build();
    }

    private String redirectRoot(HttpMethod method, String path) {
        if (method == HttpMethod.GET && path.equals("/")) {
            return "/index.html";
        }
        return path;
    }

    private byte[] read(String base, String path) {
        File file = new File(base + path);
        if (file.length() > Integer.MAX_VALUE) {
            logger.debug("파일의 크기가 너무 큽니다.: {}", file.getName());
            throw new RedirectException("/404.html");
        }

        try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] data = new byte[(int) file.length()];
            int totalBytesRead = 0;
            int bytesRead;

            while (totalBytesRead < data.length && (bytesRead = bis.read(data, totalBytesRead, data.length - totalBytesRead)) != -1) {
                totalBytesRead += bytesRead;
            }

            if (totalBytesRead != data.length) {
                logger.debug("파일 전체를 읽는데 실패했습니다.: {}", file.getName());
                throw new IOException();
            }

            return data;
        } catch (IOException e) {
            throw new RedirectException("/404.html");
        }
    }
}
