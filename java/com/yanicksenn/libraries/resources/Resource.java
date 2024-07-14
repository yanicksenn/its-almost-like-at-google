package com.yanicksenn.libraries.resources;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;

@AutoValue
public abstract class Resource {
    public abstract Class<?> getModule();
    public abstract String getResourcePath();

    public ImmutableSet<String> parseList() throws IOException {
        return Resources.parseList(this);
    }

    public byte[] parseRaw() throws IOException {
        return Resources.parseRaw(this);
    }

    public static Builder builder() {
        return new AutoValue_Resource.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setModule(Class<?> clazz);
        public abstract Builder setResourcePath(String resourcePath);

        public abstract Resource build();
    }
}