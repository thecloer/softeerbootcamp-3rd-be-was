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

        if(line == null)
            throw new IOException("Empty Request");

        StringTokenizer st = new StringTokenizer(line);
        HttpRequest.Builder requestBuilder = new HttpRequest.Builder()
                .method(st.nextToken())
                .path(st.nextToken())
                .protocol(st.nextToken());

        line = br.readLine();
        while(!(line == null || line.isEmpty())) {
            st = new StringTokenizer(line, ": ");
            requestBuilder.setProperty(st.nextToken(), st.nextToken());
            line = br.readLine();
        }

        return requestBuilder.build();
    }
}
