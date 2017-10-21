package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(builder = AutoValue_TreatmentExternal.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AutoValue
public abstract class TreatmentExternal {

    public static Builder builder() {
        return new AutoValue_TreatmentExternal.Builder();
    }

    /*package private*/ TreatmentExternal() {/*intentionally empty*/}

    @JsonProperty
    public abstract String name();

    @JsonProperty
    @Nullable
    public abstract String description();

    @JsonProperty
    @Nullable
    public abstract List<String> keys();

    @JsonProperty
    @Nullable
    public abstract List<String> segments();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);
        public abstract Builder description(String description);
        public abstract Builder keys(List<String> keys);
        public abstract Builder segments(List<String> segments);
        public abstract TreatmentExternal build();
    }
}
