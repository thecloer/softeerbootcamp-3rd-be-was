package util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class RequestHeader {

    private final String header;
    private final String method;
    private final String url;


    public RequestHeader(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        if(line == null)
            throw new IOException("Empty Request Header");

        StringTokenizer st = new StringTokenizer(line);
        this.method = st.nextToken();
        this.url = st.nextToken();

        while (!line.isEmpty()) {
            sb.append(line).append("\n");
            line = br.readLine();
        }
        this.header = sb.toString();
    }

    public String getHeader()  {
        return this.header;
    }

    public String getMethod() {
        return this.method;
    }

    public String getURL() {
        return this.url;
    }
}
