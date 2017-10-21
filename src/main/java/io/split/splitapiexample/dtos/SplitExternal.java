package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

/**
 * Created on 10/20/17.
 */
@JsonDeserialize(builder = AutoValue_SplitExternal.Builder.class)
@AutoValue
public abstract class SplitExternal {

    public static Builder builder() {
        return new AutoValue_SplitExternal.Builder();
    }

    public static Builder builder(SplitExternal from) {
        return new AutoValue_SplitExternal.Builder(from);
    }


    /*package private*/ SplitExternal() {/*intentionally empty*/}

    @JsonProperty
    public abstract String name();

    @JsonProperty
    @Nullable
    public abstract String description();

    @JsonProperty
    @Nullable
    public abstract URN trafficType();

    @JsonProperty
    @Nullable
    public abstract Long creationTime();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);
        public abstract Builder description(String description);
        public abstract Builder trafficType(URN trafficType);
        public abstract Builder creationTime(Long creationTime);
        public abstract SplitExternal build();
    }
}
