package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import java.util.List;

@JsonDeserialize(builder = AutoValue_ResultExternal.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AutoValue
public abstract class ResultExternal<T> {

    public static <T> Builder<T> builder(Class<T> clazz) {
        return new AutoValue_ResultExternal.Builder();
    }

    /*package private*/ ResultExternal() {/*intentionally empty*/}

    @JsonProperty
    public abstract List<T> objects();

    @JsonProperty
    public abstract Integer offset();

    @JsonProperty
    public abstract Integer limit();

    @JsonProperty
    public abstract Long totalCount();

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder<T> {
        public abstract Builder<T> objects(List<T> objects);
        public abstract Builder<T> offset(Integer offset);
        public abstract Builder<T> limit(Integer limit);
        public abstract Builder<T> totalCount(Long totalCount);
        public abstract ResultExternal<T> build();
    }
}
