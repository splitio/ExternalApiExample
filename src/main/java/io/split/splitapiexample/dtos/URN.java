package io.split.splitapiexample.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

/**
 * Created on 10/20/17.
 */
@JsonDeserialize(builder = AutoValue_URN.Builder.class)
@AutoValue
public abstract class URN {

    public static Builder builder() {
        return new AutoValue_URN.Builder();
    }

    public static Builder builder(URN urn) {
        return new AutoValue_URN.Builder()
                .type(urn.type())
                .id(urn.id())
                .name(urn.name());
    }


    /*package private*/ URN() {/*intentionally empty*/}

    /**
     * The type of this URN.
     *
     * @return MUST NOT BE null.
     */
    @JsonProperty
    public abstract String type();

    /**
     * The id of this URN.
     *
     * @return MUST NOT BE null.
     */
    @JsonProperty
    public abstract String id();

    @JsonProperty
    @Nullable
    public abstract String name();

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof URN) {
            URN that = (URN) o;
            return (type().equals(that.type()))
                    && id().equals(that.id());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= type().hashCode();
        h *= 1000003;
        h ^= id().hashCode();
        return h;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder type(String type);

        public abstract Builder id(String id);

        public abstract Builder name(String name);

        public abstract URN build();
    }
}
