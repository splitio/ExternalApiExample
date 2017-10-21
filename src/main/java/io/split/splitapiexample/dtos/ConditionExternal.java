package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(builder = AutoValue_ConditionExternal.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AutoValue
public abstract class ConditionExternal {

    public static Builder builder() {
        return new AutoValue_ConditionExternal.Builder();
    }

    /*package private*/ ConditionExternal() {/*intentionally empty*/}

    @JsonProperty
    @Nullable
    public abstract CombinerExternal combiner();

    @JsonProperty
    public abstract List<MatcherExternal> matchers();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder combiner(CombinerExternal combiner);
        public abstract Builder matchers(List<MatcherExternal> matchers);

        public abstract ConditionExternal build();
    }
}
