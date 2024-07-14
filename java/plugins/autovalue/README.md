# Auto-Value

This is the integration of Google's [auto-value](https://github.com/google/auto/tree/main/value) in this bazel codebase.

## Usage

```build
deps = [
    "//java/plugins/autovalue:processor",
    "@maven//:com_google_auto_value_auto_value",
    "@maven//:com_google_auto_value_auto_value_annotations",
]
```

```java
@AutoValue
public abstract class Resource {
    public abstract Class<?> getModule();
    public abstract String getResourcePath();

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
```