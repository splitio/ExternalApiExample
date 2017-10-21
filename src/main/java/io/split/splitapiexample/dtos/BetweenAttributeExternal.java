package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@JsonDeserialize(builder = AutoValue_BetweenAttributeExternal.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AutoValue
public abstract class BetweenAttributeExternal {

    public static Builder builder() {
        return new AutoValue_BetweenAttributeExternal.Builder();
    }

    /*package private*/ BetweenAttributeExternal() {/*intentionally empty*/}

    @JsonProperty
    public abstract Long from();

    @JsonProperty
    public abstract Long to();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder from(Long from);
        public abstract Builder to(Long to);

        public abstract BetweenAttributeExternal build();
    }
}
