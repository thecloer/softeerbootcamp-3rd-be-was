package util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {
    private final String method;
    private final String uri;
    private final String protocol;
    private final Map<String, String> properties;

    private final String path;
    private final Map<String, String> queries;

    private HttpRequest(String method, String uri, String protocol, Map<String, String> properties) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
        this.properties = properties;

        String[] splitString = uri.split("\\?");
        this.path = splitString[0];

        if(splitString.length == 1) {
            this.queries = Collections.unmodifiableMap(new HashMap<>());
            return;
        }

        Map<String, String> queries = new HashMap<>();
        String[] params = splitString[1].split("&");
        for (String param : params) {
            StringTokenizer st = new StringTokenizer(param, "=");
            if(st.countTokens() == 2)
                queries.put(st.nextToken(), st.nextToken());
        }
        this.queries = Collections.unmodifiableMap(queries);
    }

    public String getMethod() {
        return method;
    }
    public String getUri() {
        return uri;
    }
    public String getProtocol() {
        return protocol;
    }
    public String getProperty(String key) {
        return properties.getOrDefault(key, null);
    }
    public String getPath() {
        return path;
    }
    public Map<String, String> getQueries() {
        return queries;
    }

    public static class Builder {
        private String method;
        private String uri;
        private String protocol;
        private final Map<String, String> properties = new HashMap<>();

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder setProperty(String key, String value) {
            this.properties.put(key, value);
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(method, uri, protocol, properties);
        }
    }
}
