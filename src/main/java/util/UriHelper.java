package util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class UriHelper {

    public static String extractExtension(String path) {
        int index = path.lastIndexOf(".");

        if (index == -1)
            return "";

        return path.substring(index + 1);
    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> queries = new HashMap<>();

        String[] params = queryString.split("&");
        for (String param : params) {
            StringTokenizer st = new StringTokenizer(param, "=");
            if (st.countTokens() != 2)
                continue;
            String key = st.nextToken();
            String value = st.nextToken();
            queries.put(key.toLowerCase(), value);
        }

        return queries;
    }
}
