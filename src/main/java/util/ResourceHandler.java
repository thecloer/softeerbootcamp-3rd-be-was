package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourceHandler {

    private static final String TEMPLATE_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";

    public static byte[] getResource(ContentType type, String url) throws IOException {
        if(type == ContentType.HTML)
            return read(TEMPLATE_PATH, url);

        return read(STATIC_PATH, url);
    }

    private static byte[] read(String base, String url) throws IOException {
        return Files.readAllBytes(new File(base + url).toPath());
    }

}
