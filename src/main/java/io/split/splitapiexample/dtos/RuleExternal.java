package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import java.util.List;

@JsonDeserialize(builder = AutoValue_RuleExternal.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AutoValue
public abstract class RuleExternal {

    public static Builder builder() {
        return new AutoValue_RuleExternal.Builder();
    }

    /*package private*/ RuleExternal() {/*intentionally empty*/}

    @JsonProperty
    public abstract List<BucketExternal> buckets();

    @JsonProperty
    public abstract ConditionExternal condition();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder buckets(List<BucketExternal> buckets);
        public abstract Builder condition(ConditionExternal condition);
        public abstract RuleExternal build();
    }


}
