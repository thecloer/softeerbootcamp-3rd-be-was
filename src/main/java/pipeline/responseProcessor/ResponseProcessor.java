package pipeline.responseProcessor;

import util.http.HttpRequest;
import util.http.HttpResponse;

public interface ResponseProcessor {
    HttpResponse process(HttpRequest request, HttpResponse response);
}
