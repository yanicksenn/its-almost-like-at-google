load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "random",
    srcs = [
        "Random.java",
        "RandomImpl.java",
    ],
    deps = [
        "//java/com/yanicksenn/libraries/flags",
        "//java/com/yanicksenn/libraries/ranges",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_inject_guice",
    ],
)
