package io.split.splitapiexample.client.util;

import com.google.common.base.Preconditions;
import io.split.api.SplitApiClient;
import io.split.api.SplitApiClientConfig;

import java.util.List;
import java.util.Optional;

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

    public static SplitApiClient getClient(List<String> argList) {
        if (argList.size() == 0 || argList.size() > 2) {
            throw new IllegalArgumentException("Only Admin Token and URL allowed, got: " + argList);
        }
        String apiURL = argList.size() == 2 ? Util.stripBackslash(argList.get(1)) : "DEFAULT";
        String adminToken = argList.get(0);
        return getClient(adminToken, argList.size() == 2 ? Optional.of(apiURL) : Optional.empty());
    }

    private static SplitApiClient getClient(String adminToken, Optional<String> apiURL) {
        System.out.println("############################################");
        System.out.println("API URL: " + (apiURL.isPresent() ? apiURL.get() : "DEFAULT"));
        System.out.println("Admin Token: " + Util.maskToken(adminToken));
        System.out.println("############################################");

        if (apiURL.isPresent()) {
            SplitApiClientConfig config = SplitApiClientConfig
                    .builder()
                    .endpoint(apiURL.get())
                    .build();
            return SplitApiClient.client(adminToken, config);
        } else {
            return SplitApiClient.client(adminToken);
        }
    }
}
