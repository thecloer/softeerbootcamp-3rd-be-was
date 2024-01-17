package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class RequestParser {

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String line = br.readLine();
        HttpRequest.Builder requestBuilder = new HttpRequest.Builder();

        parseRequestLine(requestBuilder, line);
        parseRequestHeader(requestBuilder, br);

        return requestBuilder.build();
    }

    private static void parseRequestLine(HttpRequest.Builder builder, String requestLine) {
        StringTokenizer st = new StringTokenizer(requestLine);

        if(st.countTokens() != 3)
            throw new IllegalArgumentException("Invalid Request Line");

        builder.method(st.nextToken())
                .uri(st.nextToken())
                .protocol(st.nextToken());
    }
    private static void parseRequestHeader(HttpRequest.Builder builder, BufferedReader requestHeader) throws IOException {
        for(String line = requestHeader.readLine(); !(line == null || line.isEmpty()); line = requestHeader.readLine()) {

            StringTokenizer st = new StringTokenizer(line, ": ");
            if(st.countTokens() != 2)
                continue;

            builder.setProperty(st.nextToken(), st.nextToken());
        }
    }
}
