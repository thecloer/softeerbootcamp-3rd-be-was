package util;

import util.http.HttpMethod;
import util.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;
import java.util.StringTokenizer;

public class RequestParser {

    private static final Set<HttpMethod> METHODS_WITH_BODY = Collections.unmodifiableSet(Set.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH));

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String line = br.readLine();
        HttpRequest.Builder requestBuilder = new HttpRequest.Builder();

        parseRequestHeaderLine(requestBuilder, line);
        parseRequestHeaderFields(requestBuilder, br);
        if (METHODS_WITH_BODY.contains(requestBuilder.getMethod()))
            parseRequestBody(requestBuilder, br);

        return requestBuilder.build();
    }

    private static void parseRequestHeaderLine(HttpRequest.Builder builder, String requestLine) {
        StringTokenizer st = new StringTokenizer(requestLine);

        if (st.countTokens() != 3)
            throw new IllegalArgumentException("Invalid Request Line");

        builder.method(HttpMethod.valueOf(st.nextToken()))
                .uri(st.nextToken())
                .protocol(st.nextToken());
    }

    private static void parseRequestHeaderFields(HttpRequest.Builder builder, BufferedReader requestHeader) throws IOException {
        for (String line = requestHeader.readLine(); !(line == null || line.isEmpty()); line = requestHeader.readLine()) {
            String[] tokens = line.split(":");
            if (tokens.length == 2) builder.setProperty(tokens[0].trim(), tokens[1].trim());
        }
    }

    private static void parseRequestBody(HttpRequest.Builder builder, BufferedReader br) throws IOException {
        try {
            String contentLengthValue = builder.getProperty("content-length");
            int contentLength = Integer.parseInt(contentLengthValue.trim()); // TODO: contentLength > Integer.MAX_VALUE 일 경우 예외처리
            char[] buffer = new char[contentLength]; // TODO: buffer size 작게 하고 while 문으로 읽기
            br.read(buffer, 0, contentLength);

            builder.body(new String(buffer));

        } catch (NumberFormatException e) {
            throw new IOException("Content-Length의 형식이 잘못됐습니다.");
        }
    }
}
