package io.split.splitapiexample;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Preconditions;
import io.split.api.SplitApiClient;
import io.split.api.dtos.split.Rule;
import io.split.api.dtos.split.SplitDefinition;
import io.split.api.util.JsonPatchUtil;
import io.split.splitapiexample.client.util.SplitDefinitionUtils;
import io.split.splitapiexample.client.util.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SplitDefinitionExample {
    //Change these fields to create splits with other traffic type or in other environment.
    private static final String TRAFFIC_TYPE_NAME = "user";
    private static final String ENVIRONMENT_NAME = "Staging";

    private final String trafficTypeName;
    private final SplitApiClient client;
    private final String environmentName;
    private final SplitDefinitionUtils util;

    public SplitDefinitionExample(SplitApiClient client) {
        this.client = Preconditions.checkNotNull(client);
        this.trafficTypeName = TRAFFIC_TYPE_NAME;
        this.environmentName = ENVIRONMENT_NAME;
        this.util = new SplitDefinitionUtils(client);
    }

    public void killAndRestoreFromApiCommand() {
        printName("Killng Restoring From Api");
        String splitName = "kill_restore_api";
        util.maybeCreateSplit(trafficTypeName, splitName);
        util.maybeUnconfigureSplit(environmentName, splitName);
        util.maybeConfigureSplit(environmentName, splitName);

        client
                .splitDefinition()
                .kill(environmentName, splitName);

        client
                .splitDefinition()
                .get(environmentName, splitName);

        client
                .splitDefinition()
                .restore(environmentName, splitName);

        client
                .splitDefinition()
                .get(environmentName, splitName);
    }

    private void configureGetListUnconfigure() {
        printName("Configuring Getting Listing Unconfiguring");
        String splitName = "conf_unconf";
        util.maybeCreateSplit(trafficTypeName, splitName);
        util.maybeUnconfigureSplit(environmentName, splitName);
        util.maybeConfigureSplit(environmentName, splitName);

        client
                .splitDefinition()
                .get(environmentName, splitName);

        client
                .split()
                .list(0, 2);

        client
                .splitDefinition()
                .unconfigure(environmentName, splitName);
    }

    private void changeTrafficAllocation() throws IOException {
        printName("Changing Traffic Allocation");
        String splitName = "change_traffic";
        util.maybeCreateSplit(trafficTypeName, splitName);
        util.maybeUnconfigureSplit(environmentName, splitName);
        util.maybeConfigureSplit(environmentName, splitName);

        SplitDefinition base = util.baseSplitDefinition();
        SplitDefinition modified = SplitDefinition
                .builder(base)
                .trafficAllocation(100)
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        SplitDefinition updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        modified = SplitDefinition
                .builder(updated)
                .trafficAllocation(0)
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        client
                .splitDefinition()
                .update(environmentName, splitName, patch);

    }

    private void killAndRestoreFromUpdate() throws IOException {
        printName("Kill And Restore from update");
        String splitName = "kill_res_update";
        util.maybeCreateSplit(trafficTypeName, splitName);
        util.maybeUnconfigureSplit(environmentName, splitName);
        util.maybeConfigureSplit(environmentName, splitName);
        SplitDefinition base = util.baseSplitDefinition();
        SplitDefinition modified = SplitDefinition
                .builder(base)
                .killed(true)
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        SplitDefinition updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        modified = SplitDefinition
                .builder(updated)
                .killed(false)
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        client
                .splitDefinition()
                .update(environmentName, splitName, patch);

    }

    private void changeDefaultTreatment() throws IOException {
        printName("Changing Default Treatment");
        String splitName = "def_treatment";
        util.maybeCreateSplit(trafficTypeName, splitName);
        util.maybeUnconfigureSplit(environmentName, splitName);
        util.maybeConfigureSplit(environmentName, splitName);
        SplitDefinition base = util.baseSplitDefinition();
        SplitDefinition modified = SplitDefinition
                .builder(base)
                .defaultTreatment("on")
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        SplitDefinition updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        modified = SplitDefinition
                .builder(updated)
                .defaultTreatment("off")
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        client
                .splitDefinition()
                .update(environmentName, splitName, patch);
    }

    private void addRemoveReplaceKey() throws IOException {
        printName("Add Remove Replace Key");
        String splitName = "add_remove_key";
        util.maybeCreateSplit(trafficTypeName, splitName);
        util.maybeUnconfigureSplit(environmentName, splitName);
        util.maybeConfigureSplit(environmentName, splitName);

        //Add one when there is no key at all
        SplitDefinition base = util.baseSplitDefinition();
        SplitDefinition modified = SplitDefinition
                .builder(base)
                .treatments(util.onOffTreatments("one"))
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        SplitDefinition updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Add one when there is one already
        modified = SplitDefinition
                .builder(updated)
                .treatments(util.onOffTreatments("one", "two"))
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Replace one
        modified = SplitDefinition
                .builder(updated)
                .treatments(util.onOffTreatments("one", "replaced"))
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Delete one when there is one left
        modified = SplitDefinition
                .builder(updated)
                .treatments(util.onOffTreatments("one"))
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Delete all
        modified = SplitDefinition
                .builder(updated)
                .treatments(util.onOffTreatments())
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        client
                .splitDefinition()
                .update(environmentName, splitName, patch);
    }

    private void renameTreatment() throws IOException {
        printName("Rename Treatment");
        String splitName = "rename_treatment";
        util.maybeCreateSplit(trafficTypeName, splitName);
        util.maybeUnconfigureSplit(environmentName, splitName);
        util.maybeConfigureSplitWithRule(environmentName, splitName);

        SplitDefinition base = util.baseSplitDefinitionWithRule();
        SplitDefinition modified = SplitDefinition
                .builder(base)
                .treatments(util.twoTreatments("on", "newValue"))
                .defaultTreatment("newValue")
                .rules(util.simpleRule("on", "newValue"))
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        client
                .splitDefinition()
                .update(environmentName, splitName, patch);
    }

    private void addRemoveRule() throws IOException {
        printName("Add Remove Rule");
        String splitName = "add_remove_rule";
        util.maybeCreateSplit(trafficTypeName, splitName);
        util.maybeUnconfigureSplit(environmentName, splitName);
        // Create a Split Definition with only one rule
        util.maybeConfigureSplit(environmentName, splitName);

        // Add first Rule
        SplitDefinition base = util.baseSplitDefinition();
        SplitDefinition modified = SplitDefinition
                .builder(base)
                .rules(util.simpleRule())
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        SplitDefinition updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        // Add a second rule.
        List<Rule> twoRules = util.simpleRule();
        twoRules.addAll(util.simpleSetRule());
        modified = SplitDefinition
                .builder(updated)
                .rules(twoRules)
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Remove the rule
        modified = SplitDefinition
                .builder(updated)
                .rules(util.simpleRule())
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        client
                .splitDefinition()
                .update(environmentName, splitName, patch);
    }

    private void changeDefaultRule() throws IOException {
        printName("Changing Default Rule");
        String splitName = "default_rule";
        util.maybeCreateSplit(trafficTypeName, splitName);
        util.maybeUnconfigureSplit(environmentName, splitName);
        util.maybeConfigureSplit(environmentName, splitName);

        SplitDefinition base = util.baseSplitDefinition();
        SplitDefinition modified = SplitDefinition
                .builder(base)
                .defaultRule(util.twoBuckets("on", 20, "off", 80))
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        client
                .splitDefinition()
                .update(environmentName, splitName, patch);
    }


    /**
     * Several Examples for the Split Definition API
     */
    public static void main(String[] args) throws Exception {
        SplitApiClient client = Util.getClient(Arrays.asList(args));
        SplitDefinitionExample example = new SplitDefinitionExample(client);
//        example.configureGetListUnconfigure();
//        example.killAndRestoreFromApiCommand();
//        example.killAndRestoreFromUpdate();
//        example.changeTrafficAllocation();
//        example.changeDefaultTreatment();
        example.addRemoveReplaceKey();
//        example.renameTreatment();
//        example.addRemoveRule();
//        example.changeDefaultRule();
    }

    private void printName(String name) {
        System.out.println("");
        System.out.println("############################################");
        System.out.println(name);
        System.out.println("############################################");
        System.out.println("");
    }
}
