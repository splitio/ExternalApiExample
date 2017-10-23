package io.split.splitapiexample;

import io.split.splitapiexample.client.SplitApiClient;
import io.split.splitapiexample.client.util.Util;
import io.split.splitapiexample.dtos.SplitExternal;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SplitExample {

    /**
     * As an example, this will create one Split, get the Split, list the Splits of your organization and finally
     * delete the Split
     */
    public static void main(String[] args) throws Exception {
        List<String> argList = Arrays.asList(args);
        if (argList.size() == 0 || argList.size() > 2) {
            throw new IllegalArgumentException("Only Admin Token and URL allowd, got: " + argList);
        }
        String apiURL = argList.size() == 2 ? Util.stripBackslash(argList.get(1)) : Util.API_URL;
        String adminToken = argList.get(0);

        System.out.println("############################################");
        System.out.println("API URL: " + apiURL);
        System.out.println("Admin Token: " + Util.maskToken(adminToken));
        System.out.println("############################################");

        SplitApiClient client = new SplitApiClient(apiURL);
        client.withAdminApiToken(adminToken);
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
