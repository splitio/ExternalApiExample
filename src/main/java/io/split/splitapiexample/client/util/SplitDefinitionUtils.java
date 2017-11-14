package io.split.splitapiexample.client.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.split.api.SplitApiClient;
import io.split.api.client.exceptions.SplitRequestException;
import io.split.api.dtos.split.Bucket;
import io.split.api.dtos.split.Condition;
import io.split.api.dtos.split.ConditionType;
import io.split.api.dtos.split.Matcher;
import io.split.api.dtos.split.Rule;
import io.split.api.dtos.split.Split;
import io.split.api.dtos.split.SplitDefinition;
import io.split.api.dtos.split.Treatment;

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
        } catch (SplitRequestException e) {
            System.out.println(e.getMessage());
        }
    }

    public void maybeConfigureSplit(String environmentName, String splitName) {
        SplitDefinition  definitionExternal = baseSplitDefinition();
        try {
            client
                    .splitDefinition()
                    .configure(environmentName, splitName, definitionExternal);
        } catch (SplitRequestException e) {
            Preconditions.checkArgument(e.getMessage().contains("already exists"));
            System.out.println(e.getMessage());
        }
    }

    public void maybeConfigureSplitWithRule(String environmentName, String splitName) {
        SplitDefinition definitionExternal = baseSplitDefinitionWithRule();
        try {
            client
                    .splitDefinition()
                    .configure(environmentName, splitName, definitionExternal);
        } catch (SplitRequestException e) {
            Preconditions.checkArgument(e.getMessage().contains("already exists"));
            System.out.println(e.getMessage());
        }
    }

    public SplitDefinition baseSplitDefinition() {
        return baseSplitDefinition("on", "off");
    }

    public SplitDefinition baseSplitDefinition(String firstTreatment, String secondTreatment) {
        return SplitDefinition
                .builder()
                .treatments(twoTreatments(firstTreatment, secondTreatment))
                .rules(Lists.newArrayList())
                .defaultTreatment(secondTreatment)
                .defaultRule(defaultRule())
                .build();
    }

    public SplitDefinition baseSplitDefinitionWithRule() {
        return baseSplitDefinitionWithRule("on", "off");
    }

    public SplitDefinition baseSplitDefinitionWithRule(String firstTreatment, String secondTreatment) {
        SplitDefinition base = baseSplitDefinition();
        return SplitDefinition
                .builder(base)
                .rules(simpleRule(firstTreatment, secondTreatment))
                .build();
    }

    public List<Rule> simpleRule() {
        return simpleRule("on", "off");
    }

    public List<Rule> simpleSetRule() {
        return simpleSetRule("on", "off");
    }

    public List<Rule> simpleSetRule(String firstTreatment, String secondTreatment) {
        return Lists.newArrayList(
                Rule
                        .builder()
                        .buckets(twoBuckets(firstTreatment, 50, secondTreatment, 50))
                        .condition(Condition
                                .builder()
                                .matchers(Lists.newArrayList(Matcher
                                        .builder()
                                        .type(ConditionType.EQUAL_SET)
                                        .strings(Lists.newArrayList("first", "second"))
                                        .attribute("theset")
                                        .build()))
                                .build())
                        .build()
        );
    }

    public List<Rule> simpleRule(String firstTreatment, String secondTreatment) {
        return Lists.newArrayList(
                Rule
                        .builder()
                        .buckets(twoBuckets(firstTreatment, 50, secondTreatment, 50))
                        .condition(Condition
                                        .builder()
                                        .matchers(Lists.newArrayList(Matcher
                                                    .builder()
                                                    .type(ConditionType.STARTS_WITH_STRING)
                                                    .strings(Lists.newArrayList("start"))
                                                    .build()))
                                        .build())
                        .build()
        );
    }

    public List<Bucket> twoBuckets(String firstName, int firstValue, String secondName, int secondValue) {
        return Lists.newArrayList(
                Bucket
                    .builder()
                    .treatment(firstName)
                    .size(firstValue)
                    .build(),
                Bucket
                    .builder()
                    .treatment(secondName)
                    .size(secondValue)
                    .build()
        );
    }

    public List<Treatment> onOffTreatments(String... keysOn) {
        return Lists.newArrayList(
                Treatment
                        .builder()
                        .name("on")
                        .keys(Arrays.asList(keysOn))
                        .build(),
                Treatment
                        .builder()
                        .name("off")
                        .build());
    }

    public List<Treatment> twoTreatments(String first, String second) {
        return Lists.newArrayList(
                Treatment
                        .builder()
                        .name(first)
                        .keys(Lists.newArrayList())
                        .segments(Lists.newArrayList())
                        .build(),
                Treatment
                        .builder()
                        .name(second)
                        .keys(Lists.newArrayList())
                        .segments(Lists.newArrayList())
                        .build());
    }

    public List<Treatment> onOffTreatments() {
        return twoTreatments("on", "off");
    }

    public void maybeCreateSplit(String trafficTypeName, String name) {
        Split split = Split
                .builder()
                .name(Preconditions.checkNotNull(name))
                .build();
        try {
            client
                    .split()
                    .create(split, trafficTypeName);
        } catch (SplitRequestException e) {
            Preconditions.checkArgument(e.getMessage().contains("already exists"));
            System.out.println(e.getMessage());
        }
    }

    public List<Bucket> defaultRule() {
        return Lists.newArrayList(
                Bucket
                        .builder()
                        .treatment("on")
                        .size(100)
                        .build());
    }
}
