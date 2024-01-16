package util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String path;
    private final String protocol;
    private final Map<String, String> properties;
    private final URI uri;

    private HttpRequest(String method, String path, String protocol, Map<String, String> properties) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.properties = properties;
        this.uri = URI.create(path);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public URI getUri() {
        return uri;
    }

    public String getProperty(String key) {
        return properties.getOrDefault(key, null);
    }

    public static class Builder {
        private String method;
        private String path;
        private String protocol;
        private final Map<String, String> properties = new HashMap<>();

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
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
            return new HttpRequest(method, path, protocol, properties);
        }
    }
}
