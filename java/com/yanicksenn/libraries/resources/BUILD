load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "resources",
    srcs = glob(["*.java"]),
    deps = [
        "//java/plugins/autovalue:processor",
        "@maven//:com_google_auto_value_auto_value",
        "@maven//:com_google_auto_value_auto_value_annotations",
        "@maven//:com_google_guava_guava",
    ],
)
