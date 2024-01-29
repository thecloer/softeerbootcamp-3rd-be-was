package pipeline.responseProcessor.templateEngine;

import model.User.User;

import java.util.Collection;

public class TemplateComponents {

    public static String userListComponent(Collection<User> users) {
        StringBuilder component = new StringBuilder();

        int nth = 0;
        for (User user : users) {
            component.append("<tr>");
            component.append("<th scope=\"row\">").append(++nth).append("</th>");
            component.append("<td>").append(user.getUserId()).append("</td>");
            component.append("<td>").append(user.getName()).append("</td>");
            component.append("<td>").append(user.getEmail()).append("</td>");
            component.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
            component.append("</tr>");
        }

        return component.toString();
    }
}
