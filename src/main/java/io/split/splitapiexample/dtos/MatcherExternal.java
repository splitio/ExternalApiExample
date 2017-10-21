package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(builder = AutoValue_MatcherExternal.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AutoValue
public abstract class MatcherExternal {

    public static Builder builder() {
        return new AutoValue_MatcherExternal.Builder();
    }

    /*package private*/ MatcherExternal() {/*intentionally empty*/}

    @JsonProperty
    public abstract ConditionTypeExternal type();

    @JsonProperty
    @Nullable
    public abstract Boolean negate();

    @JsonProperty
    @Nullable
    public abstract String attribute();

    @JsonProperty
    @Nullable
    public abstract Boolean bool();

    @JsonProperty
    @Nullable
    public abstract String string();

    @JsonProperty
    @Nullable
    public abstract Long number();

    @JsonProperty
    @Nullable
    public abstract Long date();

    @JsonProperty
    @Nullable
    public abstract List<String> strings();

    @JsonProperty
    @Nullable
    public abstract BetweenAttributeExternal between();

    @JsonProperty
    @Nullable
    public abstract DependsExternal depends();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder negate(Boolean negate);
        public abstract Builder type(ConditionTypeExternal type);
        public abstract Builder attribute(String attribute);
        public abstract Builder string(String string);
        public abstract Builder bool(Boolean bool);
        public abstract Builder number(Long number);
        public abstract Builder date(Long date);
        public abstract Builder strings(List<String> strings);
        public abstract Builder between(BetweenAttributeExternal between);
        public abstract Builder depends(DependsExternal depends);

        public abstract MatcherExternal build();
    }
}
