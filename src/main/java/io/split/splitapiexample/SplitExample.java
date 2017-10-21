package io.split.splitapiexample;

import io.split.splitapiexample.client.ApiClient;
import io.split.splitapiexample.dtos.ResultExternal;
import io.split.splitapiexample.dtos.SplitExternal;

import java.util.Optional;

public class SplitExample {
    private final static String API_URL = "https://api-aws-staging.split.io/internal/api";
    private final static String ADMIN_API_TOKEN = "ADMI_API_TOKEN";

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
        SplitExternal create = client.split().create("user", splitExternal);
        System.out.println("RESULT: ");
        System.out.println(create);
        System.out.println("--------------------------------------------");
        SplitExternal get = client.split().get(splitName);
        System.out.println("RESULT: ");
        System.out.println(get);
        System.out.println("--------------------------------------------");
        ResultExternal<SplitExternal> list = client.split().list(Optional.of(0), Optional.of(2));
        System.out.println("RESULT: ");
        System.out.println(list);
        System.out.println("--------------------------------------------");
        Boolean delete = client.split().delete(splitName);
        System.out.println("RESULT: ");
        System.out.println(delete);
        System.out.println("--------------------------------------------");
    }
}
