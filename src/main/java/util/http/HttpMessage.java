package util.http;

public interface HttpMessage {
    Integer getStatusCode();
    String getStatusMessage();
    ContentType getContentType();
    String getAdditionalHeaders();
    Integer getBodyLength();
    byte[] getBody();
}
