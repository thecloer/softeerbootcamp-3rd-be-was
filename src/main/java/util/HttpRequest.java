package util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final String method;
    private final String uri;
    private final String protocol;
    private final String path;
    private final Map<String, String> queries;
    private final Map<String, String> properties;

    private HttpRequest(String method, String uri, String protocol, Map<String, String> properties) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
        this.properties = properties;

        String[] splittedString = uri.split("\\?");
        this.path = splittedString[0];
        this.queries = Collections.unmodifiableMap(
                (splittedString.length > 1)
                        ? UriHelper.parseQueryString(splittedString[1])
                        : new HashMap<>()
        );
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
        private final Map<String, String> properties = new HashMap<>();
        private String method;
        private String uri;
        private String protocol;

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
