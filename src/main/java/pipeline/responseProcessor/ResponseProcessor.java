package pipeline.responseProcessor;

import util.http.HttpResponse;

public interface ResponseProcessor {
    HttpResponse process(HttpResponse request);
}
