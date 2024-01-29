package util.http;

import session.Session;
import util.UriHelper;

import java.util.*;

public class HttpRequest {

    private final HttpMethod method;
    private final String uri;
    private final String protocol;
    private final String path;
    private final String body;
    private final Map<String, String> queries;
    private final Map<String, String> properties;
    private Session session;

    private HttpRequest(HttpMethod method, String uri, String protocol, String body, Map<String, String> properties) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
        this.body = body;
        this.properties = properties;

        String[] splittedString = uri.split("\\?");
        this.path = splittedString[0];
        this.queries = Collections.unmodifiableMap(
                (splittedString.length > 1)
                        ? UriHelper.parseQueryString(splittedString[1])
                        : new HashMap<>()
        );
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getProperty(String key) {
        return properties.get(key.toLowerCase());
    }

    public String getPath() {
        return path;
    }

    public String getBody() {
        return body;
    }

    public String getQueryParam(String key) {
        return queries.getOrDefault(key, "");
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Boolean isLoggedIn() {
        return session != null;
    }

    public static class Builder {
        private final Map<String, String> properties = new HashMap<>();
        private HttpMethod method;
        private String uri;
        private String protocol;
        private String body = "";

        public Builder method(HttpMethod method) {
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

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder setProperty(String key, String value) {
            this.properties.put(key.toLowerCase(), value);
            return this;
        }

        public HttpMethod getMethod() {
            return method;
        }

        public String getProperty(String key) {
            return this.properties.getOrDefault(key, "");
        }

        public HttpRequest build() {
            return new HttpRequest(method, uri, protocol, body, properties);
        }
    }
}
