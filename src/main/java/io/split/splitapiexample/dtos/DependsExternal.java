package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import java.util.List;

@JsonDeserialize(builder = AutoValue_DependsExternal.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AutoValue
public abstract class DependsExternal {

    public static Builder builder() {
        return new AutoValue_DependsExternal.Builder();
    }

    /*package private*/ DependsExternal() {/*intentionally empty*/}

    @JsonProperty
    public abstract String splitName();

    @JsonProperty
    public abstract List<String> treatments();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder splitName(String splitName);
        public abstract Builder treatments(List<String> treatment);

        public abstract DependsExternal build();
    }

}
