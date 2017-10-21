package io.split.splitapiexample;

import io.split.splitapiexample.client.ApiClient;
import io.split.splitapiexample.dtos.SplitExternal;

import java.util.Optional;

public class SplitExample {
    private final static String API_URL = "https://api-aws-staging.split.io/internal/api";
    private final static String ADMIN_API_TOKEN = "ADMIN_API_TOKEN";

    /**
     * As an example, this will create one Split, get the Split, list the Splits of your organization and finally
     * delete the Split
     */
    public static void main(String[] args) throws Exception {
        ApiClient client = new ApiClient(API_URL);
        client.withAdminApiToken(ADMIN_API_TOKEN);
        String splitName = "paywall_beta_2";
        SplitExternal splitExternal = SplitExternal
                .builder()
                .name(splitName)
                .build();
        System.out.println("--------------------------------------------");
        System.out.println(client.externalSplit().create("user", splitExternal));
        System.out.println("--------------------------------------------");
        System.out.println(client.externalSplit().get(splitName));
        System.out.println("--------------------------------------------");
        System.out.println(client.externalSplit().list(Optional.empty(), Optional.empty()));
        System.out.println("--------------------------------------------");
        System.out.println(client.externalSplit().delete(splitName));
        System.out.println("--------------------------------------------");
    }
}
