package pipeline;

import pipeline.requestProcessor.RequestProcessor;
import pipeline.responseProcessor.ResponseProcessor;
import util.http.HttpMessage;
import util.http.HttpRequest;
import exception.HttpBaseException;
import util.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class RequestPipeline {

    private final List<RequestProcessor> requestProcessors = new ArrayList<>();
    private final List<ResponseProcessor> responseProcessors = new ArrayList<>();

    public HttpMessage process(HttpRequest request) {
        try {
            request = processRequest(request);

            HttpResponse response = Router.route(request);

            return processResponse(response);
        } catch (HttpBaseException errorResponse) {
            return errorResponse;
        }
    }

    private HttpRequest processRequest(HttpRequest request) {
        for (RequestProcessor requestProcessor : requestProcessors)
            request = requestProcessor.process(request);

        return request;
    }

    private HttpResponse processResponse(HttpResponse response) {
        for (ResponseProcessor responseProcessor : responseProcessors)
            response = responseProcessor.process(response);

        return response;
    }

    public RequestPipeline addRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessors.add(requestProcessor);
        return this;
    }

    public RequestPipeline addResponseProcessor(ResponseProcessor responseProcessor) {
        this.responseProcessors.add(responseProcessor);
        return this;
    }
}
