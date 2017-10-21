package io.split.splitapiexample;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Preconditions;
import io.split.splitapiexample.client.SplitApiClient;
import io.split.splitapiexample.client.util.JsonPatchUtil;
import io.split.splitapiexample.client.util.SplitDefinitionUtils;
import io.split.splitapiexample.dtos.RuleExternal;
import io.split.splitapiexample.dtos.SplitDefinitionExternal;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SplitDefinitionExample {
    private final static String API_URL = "https://api-aws-staging.split.io/internal/api";
    public final static String ADMIN_API_TOKEN = "ADMIN_API_TOKEN";

    private final String trafficTypeName;
    private final SplitApiClient client;
    private final String environmentName;
    private final SplitDefinitionUtils util;

    public SplitDefinitionExample(SplitApiClient client) {
        this.client = Preconditions.checkNotNull(client);
        this.trafficTypeName = "user";
        this.environmentName = "Staging";
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
                .list(Optional.of(0), Optional.of(2));

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

        SplitDefinitionExternal base = util.baseSplitDefinition();
        SplitDefinitionExternal modified = SplitDefinitionExternal
                .builder(base)
                .trafficAllocation(100)
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        SplitDefinitionExternal updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        modified = SplitDefinitionExternal
                .builder(updated)
                .trafficAllocation(0)
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
        SplitDefinitionExternal base = util.baseSplitDefinition();
        SplitDefinitionExternal modified = SplitDefinitionExternal
                .builder(base)
                .defaultTreatment("on")
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        SplitDefinitionExternal updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        modified = SplitDefinitionExternal
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
        SplitDefinitionExternal base = util.baseSplitDefinition();
        SplitDefinitionExternal modified = SplitDefinitionExternal
                .builder(base)
                .treatments(util.onOffTreatments("one"))
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        SplitDefinitionExternal updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Add one when there is one already
        modified = SplitDefinitionExternal
                .builder(updated)
                .treatments(util.onOffTreatments("one", "two"))
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Replace one
        modified = SplitDefinitionExternal
                .builder(updated)
                .treatments(util.onOffTreatments("one", "replaced"))
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Delete one when there is one left
        modified = SplitDefinitionExternal
                .builder(updated)
                .treatments(util.onOffTreatments("one"))
                .build();
        patch = JsonPatchUtil.createPatch(updated, modified);
        updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Delete all
        modified = SplitDefinitionExternal
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

        SplitDefinitionExternal base = util.baseSplitDefinitionWithRule();
        SplitDefinitionExternal modified = SplitDefinitionExternal
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
        util.maybeConfigureSplitWithRule(environmentName, splitName);

        // Add a second rule.
        SplitDefinitionExternal base = util.baseSplitDefinitionWithRule();
        List<RuleExternal> twoRules = util.simpleRule();
        twoRules.addAll(util.simpleSetRule());
        SplitDefinitionExternal modified = SplitDefinitionExternal
                .builder(base)
                .rules(twoRules)
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        SplitDefinitionExternal updated = client
                .splitDefinition()
                .update(environmentName, splitName, patch);

        //Remove the rule
        modified = SplitDefinitionExternal
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

        SplitDefinitionExternal base = util.baseSplitDefinition();
        SplitDefinitionExternal modified = SplitDefinitionExternal
                .builder(base)
                .defaultRule(util.twoBuckets("on", 20, "off", 80))
                .build();
        JsonNode patch = JsonPatchUtil.createPatch(base, modified);
        client
                .splitDefinition()
                .update(environmentName, splitName, patch);
    }


    /**
     * As an example, this will create one Split, get the Split, list the Splits of your organization and finally
     * delete the Split
     */
    public static void main(String[] args) throws Exception {
        SplitApiClient client = new SplitApiClient(API_URL);
        client.withAdminApiToken(ADMIN_API_TOKEN);
        SplitDefinitionExample example = new SplitDefinitionExample(client);

        example.configureGetListUnconfigure();
        example.killAndRestoreFromApiCommand();
        example.changeTrafficAllocation();
        example.changeDefaultTreatment();
        example.addRemoveReplaceKey();
        example.renameTreatment();
        example.addRemoveRule();
        example.changeDefaultRule();
    }

    private void printName(String name) {
        System.out.println("");
        System.out.println("############################################");
        System.out.println(name);
        System.out.println("############################################");
        System.out.println("");
    }
}
