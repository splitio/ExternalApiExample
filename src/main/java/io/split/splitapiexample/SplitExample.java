package io.split.splitapiexample;

import io.split.api.SplitApiClient;
import io.split.api.dtos.split.Split;
import io.split.splitapiexample.client.util.Util;

import java.util.Arrays;

public class SplitExample {
    //Change these fields to create splits with other traffic type.
    private static final String TRAFFIC_TYPE_NAME = "user";

    /**
     * As an example, this will create one Split, get the Split, list the Splits of your organization and finally
     * delete the Split
     */
    public static void main(String[] args) throws Exception {
        SplitApiClient client = Util.getClient(Arrays.asList(args));
        String splitName = "paywall_beta_2";
        Split split = Split
                .builder()
                .name(splitName)
                .build();
        client
                .split()
                .create(split, TRAFFIC_TYPE_NAME);

        client
                .split()
                .get(splitName);

        client
                .split()
                .list(0, 2);

        client
                .split()
                .delete(splitName);
    }
}
