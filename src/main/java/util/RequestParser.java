package util;

import util.http.HttpMethod;
import util.http.HttpRequest;

import java.io.*;
import java.util.Collections;
import java.util.Set;
import java.util.StringTokenizer;

public class RequestParser {

    private static final int BUFFER_SIZE = 1024;
    private static final Set<HttpMethod> METHODS_WITH_BODY = Collections.unmodifiableSet(Set.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH));

    public static HttpRequest parse(InputStream inputStream) throws IOException {

        HttpRequest.Builder requestBuilder = new HttpRequest.Builder();

        byte[] inputBytes = readByteArrayFromInputStream(inputStream);

        int cursor = 0;
        cursor = parseRequestHeaderLine(inputBytes, cursor, requestBuilder);
        cursor = parseRequestHeaderFields(inputBytes, cursor, requestBuilder);
        if (METHODS_WITH_BODY.contains(requestBuilder.getMethod()))
            parseRequestBody(inputBytes, cursor, requestBuilder);

        return requestBuilder.build();
    }

    private static byte[] readByteArrayFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream inputByteArrayStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            inputByteArrayStream.write(buffer, 0, bytesRead);

            if (bytesRead < BUFFER_SIZE) break;
        }

        return inputByteArrayStream.toByteArray();
    }

    private static int parseRequestHeaderLine(byte[] inputByte, int cursor, HttpRequest.Builder builder) throws IOException {

        StringBuilder requestLineBuilder = new StringBuilder();
        while (!isEndOfLine(inputByte, cursor)) {
            requestLineBuilder.append((char) inputByte[cursor]);
            cursor++;
        }
        cursor = skipEndOfLine(cursor);

        StringTokenizer tokenizer = new StringTokenizer(requestLineBuilder.toString());
        if (tokenizer.countTokens() != 3)
            throw new IOException("HTTP 요청 헤더 라인이 잘못됐습니다.");

        builder.method(HttpMethod.valueOf(tokenizer.nextToken()))
                .uri(tokenizer.nextToken())
                .protocol(tokenizer.nextToken());

        return cursor;
    }

    private static int parseRequestHeaderFields(byte[] inputByte, int cursor, HttpRequest.Builder builder) throws IOException {

        StringBuilder fieldLindBuilder = new StringBuilder();

        while (cursor < inputByte.length) {
            fieldLindBuilder.append((char) inputByte[cursor++]);
            if (isEndOfLine(inputByte, cursor)) {
                cursor = skipEndOfLine(cursor);
                String fieldLine = fieldLindBuilder.toString();
                int idx = fieldLine.indexOf(':');
                if (idx == -1) continue;
                String key = fieldLine.substring(0, idx).trim();
                String value = fieldLine.substring(idx + 1).trim();
                builder.setProperty(key, value);
                fieldLindBuilder.setLength(0);

                if (isEndOfLine(inputByte, cursor)) {
                    cursor = skipEndOfLine(cursor);
                    break;
                }
            }
        }

        return cursor;
    }

    private static void parseRequestBody(byte[] inputByte, int cursor, HttpRequest.Builder builder) throws IOException {
        ByteArrayOutputStream requestBodyStream = new ByteArrayOutputStream();

        while (cursor < inputByte.length)
            requestBodyStream.write(inputByte[cursor++]);

        byte[] body = requestBodyStream.toByteArray();

        builder.body(body);
    }

    private static boolean isEndOfLine(byte[] inputByte, int cursor) {
        return cursor + 1 < inputByte.length && inputByte[cursor] == '\r' && inputByte[cursor + 1] == '\n';
    }

    private static int skipEndOfLine(int cursor) {
        return cursor + 2;
    }
}
