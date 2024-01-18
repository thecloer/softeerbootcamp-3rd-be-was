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

        parseRequestHeaderLine(requestBuilder, line);
        parseRequestHeaderFields(requestBuilder, br);

        return requestBuilder.build();
    }

    private static void parseRequestHeaderLine(HttpRequest.Builder builder, String requestLine) {
        StringTokenizer st = new StringTokenizer(requestLine);

        if(st.countTokens() != 3)
            throw new IllegalArgumentException("Invalid Request Line");

        builder.method(st.nextToken())
                .uri(st.nextToken())
                .protocol(st.nextToken());
    }

    private static void parseRequestHeaderFields(HttpRequest.Builder builder, BufferedReader requestHeader) throws IOException {
        for(String line = requestHeader.readLine(); !(line == null || line.isEmpty()); line = requestHeader.readLine()) {

            String[] tokens = line.split(": ");
            if(tokens.length == 2)
                builder.setProperty(tokens[0], tokens[1]);
        }
    }
}
