package util;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String protocol;
    private final Map<String, String> properties;
    private final URI uri;
    private final String path;
    private final String fragment;
    private final Map<String, String> queries;

    private HttpRequest(String method, String uri, String protocol, Map<String, String> properties) {
        this.method = method;
        this.protocol = protocol;
        this.properties = properties;

        URI parsedUri = URI.create(uri);
        this.uri = parsedUri;
        this.path = parsedUri.getPath();
        this.fragment = parsedUri.getFragment();

        String query = parsedUri.getQuery();
        if(query == null) {
            this.queries = Collections.unmodifiableMap(new HashMap<>());
            return;
        }

        Map<String, String> queries = new HashMap<>();
        String[] params = query.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            queries.put(keyValue[0], keyValue[1]);
        }
        this.queries = Collections.unmodifiableMap(queries);
    }

    public String getMethod() {
        return method;
    }
    public String getProtocol() {
        return protocol;
    }
    public String getProperty(String key) {
        return properties.getOrDefault(key, null);
    }
    public URI getUri() {
        return uri;
    }
    public String getPath() {
        return path;
    }
    public String getFragment() {
        return fragment;
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
