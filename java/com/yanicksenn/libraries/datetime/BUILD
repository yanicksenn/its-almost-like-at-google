load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "datetime",
    srcs = glob(["*.java"]),
    deps = [
        "//java/com/yanicksenn/libraries/ranges",
        "//protos/datetime:datetime-proto-java",
        "@maven//:com_google_guava_guava",
    ],
)
