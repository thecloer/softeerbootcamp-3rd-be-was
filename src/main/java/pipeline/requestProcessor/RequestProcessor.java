package pipeline.requestProcessor;

import util.http.HttpRequest;

public interface RequestProcessor {
    HttpRequest process(HttpRequest request);
}
