package io.split.splitapiexample.client.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.split.splitapiexample.client.SplitApiClient;
import io.split.splitapiexample.dtos.BucketExternal;
import io.split.splitapiexample.dtos.ConditionExternal;
import io.split.splitapiexample.dtos.ConditionTypeExternal;
import io.split.splitapiexample.dtos.MatcherExternal;
import io.split.splitapiexample.dtos.RuleExternal;
import io.split.splitapiexample.dtos.SplitDefinitionExternal;
import io.split.splitapiexample.dtos.SplitExternal;
import io.split.splitapiexample.dtos.TreatmentExternal;

import java.util.Arrays;
import java.util.List;

public class SplitDefinitionUtils {


    private final SplitApiClient client;

    public SplitDefinitionUtils(SplitApiClient client) {
        this.client = Preconditions.checkNotNull(client);
    }

    public void maybeUnconfigureSplit(String environmentName, String splitName) {
        try {
            client
                    .splitDefinition()
                    .unconfigure(environmentName, splitName);
        } catch (ResponseStatusError e) {
            System.out.println(e.message());
        }
    }


    public void maybeConfigureSplit(String environmentName, String splitName) {
        SplitDefinitionExternal definitionExternal = baseSplitDefinition();
        try {
            client
                    .splitDefinition()
                    .configure(environmentName, splitName, definitionExternal);
        } catch (ResponseStatusError e) {
            Preconditions.checkArgument(e.message().contains("already exists"));
            System.out.println(e.message());
        }
    }

    public void maybeConfigureSplitWithRule(String environmentName, String splitName) {
        SplitDefinitionExternal definitionExternal = baseSplitDefinitionWithRule();
        try {
            client
                    .splitDefinition()
                    .configure(environmentName, splitName, definitionExternal);
        } catch (ResponseStatusError e) {
            Preconditions.checkArgument(e.message().contains("already exists"));
            System.out.println(e.message());
        }
    }

    public SplitDefinitionExternal baseSplitDefinition() {
        return baseSplitDefinition("on", "off");
    }

    public SplitDefinitionExternal baseSplitDefinition(String firstTreatment, String secondTreatment) {
        return SplitDefinitionExternal
                .builder()
                .treatments(twoTreatments(firstTreatment, secondTreatment))
                .defaultTreatment(secondTreatment)
                .defaultRule(defaultRule())
                .build();
    }

    public SplitDefinitionExternal baseSplitDefinitionWithRule() {
        return baseSplitDefinitionWithRule("on", "off");
    }

    public SplitDefinitionExternal baseSplitDefinitionWithRule(String firstTreatment, String secondTreatment) {
        SplitDefinitionExternal base = baseSplitDefinition();
        return SplitDefinitionExternal
                .builder(base)
                .rules(simpleRule(firstTreatment, secondTreatment))
                .build();
    }

    public List<RuleExternal> simpleRule() {
        return simpleRule("on", "off");
    }

    public List<RuleExternal> simpleSetRule() {
        return simpleSetRule("on", "off");
    }

    public List<RuleExternal> simpleSetRule(String firstTreatment, String secondTreatment) {
        return Lists.newArrayList(
                RuleExternal
                        .builder()
                        .buckets(twoBuckets(firstTreatment, 50, secondTreatment, 50))
                        .condition(ConditionExternal
                                .builder()
                                .matchers(Lists.newArrayList(MatcherExternal
                                        .builder()
                                        .type(ConditionTypeExternal.EQUAL_SET)
                                        .strings(Lists.newArrayList("first", "second"))
                                        .attribute("theset")
                                        .build()))
                                .build())
                        .build()
        );
    }

    public List<RuleExternal> simpleRule(String firstTreatment, String secondTreatment) {
        return Lists.newArrayList(
                RuleExternal
                        .builder()
                        .buckets(twoBuckets(firstTreatment, 50, secondTreatment, 50))
                        .condition(ConditionExternal
                                        .builder()
                                        .matchers(Lists.newArrayList(MatcherExternal
                                                    .builder()
                                                    .type(ConditionTypeExternal.STARTS_WITH_STRING)
                                                    .strings(Lists.newArrayList("start"))
                                                    .build()))
                                        .build())
                        .build()
        );
    }

    public List<BucketExternal> twoBuckets(String firstName, int firstValue, String secondName, int secondValue) {
        return Lists.newArrayList(
                BucketExternal
                    .builder()
                    .treatment(firstName)
                    .size(firstValue)
                    .build(),
                BucketExternal
                    .builder()
                    .treatment(secondName)
                    .size(secondValue)
                    .build()
        );
    }

    public List<TreatmentExternal> onOffTreatments(String... keysOn) {
        return Lists.newArrayList(
                TreatmentExternal
                        .builder()
                        .name("on")
                        .keys(Arrays.asList(keysOn))
                        .build(),
                TreatmentExternal
                        .builder()
                        .name("off")
                        .build());
    }

    public List<TreatmentExternal> twoTreatments(String first, String second) {
        return Lists.newArrayList(
                TreatmentExternal
                        .builder()
                        .name(first)
                        .build(),
                TreatmentExternal
                        .builder()
                        .name(second)
                        .build());
    }

    public List<TreatmentExternal> onOffTreatments() {
        return twoTreatments("on", "off");
    }

    public void maybeCreateSplit(String trafficTypeName, String name) {
        SplitExternal splitExternal = SplitExternal
                .builder()
                .name(Preconditions.checkNotNull(name))
                .build();
        try {
            client
                    .split()
                    .create(trafficTypeName, splitExternal);
        } catch (ResponseStatusError e) {
            Preconditions.checkArgument(e.message().contains("already exists"));
            System.out.println(e.message());
        }
    }

    public List<BucketExternal> defaultRule() {
        return Lists.newArrayList(
                BucketExternal
                        .builder()
                        .treatment("on")
                        .size(100)
                        .build());
    }
}
