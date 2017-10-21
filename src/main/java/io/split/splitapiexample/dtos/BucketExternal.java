package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@JsonDeserialize(builder = AutoValue_BucketExternal.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AutoValue
public abstract class BucketExternal {

    public static Builder builder() {
        return new AutoValue_BucketExternal.Builder();
    }

    /*package private*/ BucketExternal() {/*intentionally empty*/}

    @JsonProperty
    public abstract String treatment();

    @JsonProperty
    public abstract Integer size();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder treatment(String treatment);
        public abstract Builder size(Integer size);
        public abstract BucketExternal build();
    }
}
