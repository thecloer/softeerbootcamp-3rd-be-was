package util.http;

public interface HttpMessage {
    Integer getStatusCode();
    String getStatusMessage();
    ContentType getContentType();
    String getFields();
    Integer getBodyLength();
    byte[] getBody();
    String getCookies();
}
