load("@rules_java//java:defs.bzl", "java_library", "java_plugin")

java_plugin(
    name = "auto-value-plugin",
    generates_api = 1,
    processor_class = "com.google.auto.value.processor.AutoValueProcessor",
    deps = [
        "@maven//:com_google_auto_value_auto_value",
        "@maven//:javax_annotation_javax_annotation_api",
    ],
)

java_library(
    name = "auto-value-processor",
    exported_plugins = [":auto-value-plugin"],
    visibility = ["//visibility:public"],
)
