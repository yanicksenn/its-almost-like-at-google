load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "dates",
    srcs = glob(["*.java"]),
    deps = [
        "//java/com/yanicksenn/libraries/ranges",
        "//java/plugins/autovalue:processor",
        "//protos/date:dates-proto-java",
        "@maven//:com_google_auto_value_auto_value",
        "@maven//:com_google_auto_value_auto_value_annotations",
        "@maven//:com_google_guava_guava",
    ],
)
