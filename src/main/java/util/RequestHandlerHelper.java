package util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RequestHandlerHelper {

    public static String getHeader(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        StringBuilder header = new StringBuilder();
        String line = br.readLine();
        while (!(line == null || line.isEmpty())) {
            header.append(line).append("\n");
            line = br.readLine();
        }
        return header.toString();
    }
}
