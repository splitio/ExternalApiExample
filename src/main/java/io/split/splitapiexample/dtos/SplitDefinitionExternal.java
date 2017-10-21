package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(builder = AutoValue_SplitDefinitionExternal.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AutoValue
public abstract class SplitDefinitionExternal {

    public static Builder builder() {
        return new AutoValue_SplitDefinitionExternal.Builder();
    }

    public static Builder builder(SplitDefinitionExternal splitExternal) {
        return builder()
                .name(splitExternal.name())
                .environment(splitExternal.environment())
                .defaultTreatment(splitExternal.defaultTreatment())
                .treatments(splitExternal.treatments())
                .killed(splitExternal.killed())
                .defaultRule(splitExternal.defaultRule())
                .trafficAllocation(splitExternal.trafficAllocation())
                .rules(splitExternal.rules())
                .lastUpdateTime(splitExternal.lastUpdateTime())
                .creationTime(splitExternal.creationTime());
    }

    /*package private*/ SplitDefinitionExternal() {/*intentionally empty*/}

    /**
     * This is a customer facing DTO, and the order of these fields will determine the order that
     * the payload is delivered. Be thoughtful of that
     */
    @JsonProperty
    @Nullable
    public abstract String name();

    @JsonProperty
    @Nullable
    public abstract URN environment();

    @JsonProperty
    @Nullable
    public abstract Boolean killed();

    @JsonProperty
    @Nullable
    public abstract List<TreatmentExternal> treatments();

    @JsonProperty
    @Nullable
    public abstract String defaultTreatment();

    @JsonProperty
    @Nullable
    public abstract Integer trafficAllocation();

    @JsonProperty
    @Nullable
    public abstract List<RuleExternal> rules();

    @JsonProperty
    @Nullable
    public abstract List<BucketExternal> defaultRule();

    @JsonProperty
    @Nullable
    public abstract Long creationTime();

    @JsonProperty
    @Nullable
    public abstract Long lastUpdateTime();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);
        public abstract Builder environment(URN environment);
        public abstract Builder killed(Boolean killed);
        public abstract Builder defaultTreatment(String defaultTreatment);
        public abstract Builder treatments(List<TreatmentExternal> treatments);
        public abstract Builder trafficAllocation(Integer trafficAllocation);
        public abstract Builder rules(List<RuleExternal> rules);
        public abstract Builder defaultRule(List<BucketExternal> defaultRule);
        public abstract Builder lastUpdateTime(Long creationTime);
        public abstract Builder creationTime(Long creationTime);
        public abstract SplitDefinitionExternal build();
    }

}