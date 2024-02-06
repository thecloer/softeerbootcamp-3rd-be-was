package pipeline.responseProcessor;

import pipeline.responseProcessor.templateEngine.TemplateComponents;
import util.http.HttpRequest;
import util.http.HttpResponse;

public class CommonComponentInjector implements ResponseProcessor {

    @Override
    public HttpResponse process(HttpRequest request, HttpResponse response) {
        TemplateComponents.NavItem activeItem = TemplateComponents.NavItem.activeItem(request.getPath());
        response.setTemplateData("navHeaderComponent", TemplateComponents.navHeader(request.isLoggedIn(), activeItem));
        return response;
    }
}
