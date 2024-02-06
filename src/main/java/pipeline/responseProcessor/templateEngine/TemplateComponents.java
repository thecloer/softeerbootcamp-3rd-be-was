package pipeline.responseProcessor.templateEngine;

import model.User.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TemplateComponents {

    private static final List<NavItem> LOGGED_IN_NAV_ITEMS = Arrays.asList(NavItem.POSTS, NavItem.LOGOUT, NavItem.MODIFY);
    private static final List<NavItem> LOGGED_OUT_NAV_ITEMS = Arrays.asList(NavItem.POSTS, NavItem.LOGIN, NavItem.REGISTER);

    public enum NavItem {
        NONE("", ""),
        POSTS("Posts", "/index.html"),
        LOGIN("로그인", "/user/login.html"),
        REGISTER("회원가입", "/user/form.html"),
        LOGOUT("로그아웃", "/user/logout"),
        MODIFY("개인정보수정", "#");

        private final String name;
        private final String url;

        public static NavItem activeItem(String path) {
            for (NavItem navItem : LOGGED_IN_NAV_ITEMS)
                if (navItem.url.equals(path))
                    return navItem;

            for (NavItem navItem : LOGGED_OUT_NAV_ITEMS)
                if (navItem.url.equals(path))
                    return navItem;

            return NONE;
        }

        NavItem(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getHtml(boolean isActive) {
            return "<li " + (isActive ? "class=\"active\"" : "") + "><a href=\"" + url + "\">" + name + "</a></li>";
        }
    }

    // TODO: 로그인 여부에 따라 다른 nav bar를 보여주도록 수정(client에서 혹은 다른 방법)
    public static String navHeader(boolean isLoggedIn, NavItem active) {
        List<NavItem> navItems = isLoggedIn ? LOGGED_IN_NAV_ITEMS : LOGGED_OUT_NAV_ITEMS;

        StringBuilder component = new StringBuilder();

        for (NavItem navItem : navItems)
            component.append(navItem.getHtml(navItem == active));

        return component.toString();
    }

    public static String userList(Collection<User> users) {
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
