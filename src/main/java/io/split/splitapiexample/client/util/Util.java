package io.split.splitapiexample.client.util;

import com.google.common.base.Preconditions;

public class Util {
    public final static String API_URL = "https://api.split.io/internal/api";

    public static String stripBackslash(String url) {
        return stripBackslash(url, false);
    }

    private static String stripBackslash(String url, boolean stripStarting) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        if (stripStarting && url.startsWith("/")) {
            url = url.substring(1, url.length());
        }
        return url;
    }

    public static String maskToken(String adminToken) {
        Preconditions.checkNotNull(adminToken);
        int asteriskCount = adminToken.length() - 3;
        StringBuilder builder = new StringBuilder();
        builder.append(adminToken.substring(0, 3));
        for(int count = 0; count < asteriskCount; count++) {
            builder.append("*");
        }
        return builder.toString();
    }
}
