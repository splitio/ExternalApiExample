package io.split.splitapiexample.client.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created on 10/20/17.
 */
public class Util {

    public static URL getURLorElse(String url) {
        try {
            return new URL(stripBackslash(url));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String stripBackslash(String url) {
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
}
