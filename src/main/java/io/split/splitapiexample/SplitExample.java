package io.split.splitapiexample;

import io.split.splitapiexample.client.SplitApiClient;
import io.split.splitapiexample.dtos.SplitExternal;

import java.util.Optional;

public class SplitExample {
    private final static String API_URL = "https://api-aws-staging.split.io/internal/api";

    /**
     * As an example, this will create one Split, get the Split, list the Splits of your organization and finally
     * delete the Split
     */
    public static void main(String[] args) throws Exception {
        SplitApiClient client = new SplitApiClient(API_URL);
        client.withAdminApiToken(SplitDefinitionExample.ADMIN_API_TOKEN);
        String splitName = "paywall_beta_2";
        SplitExternal splitExternal = SplitExternal
                .builder()
                .name(splitName)
                .build();
        client.split().create("user", splitExternal);
        client.split().get(splitName);
        client.split().list(Optional.of(0), Optional.of(2));
        client.split().delete(splitName);
    }
}
