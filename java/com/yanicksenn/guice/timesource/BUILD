load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "timesource",
    srcs = glob(["*.java"]),
    deps = [
        "//java/plugins/autovalue:processor",
        "//protos/datetime:datetime-proto-java",
        "@maven//:com_google_auto_value_auto_value",
        "@maven//:com_google_auto_value_auto_value_annotations",
        "@maven//:com_google_guava_guava",
    ],
)

java_binary(
    name = "timesource-bin",
    srcs = ["DateTimeUtils.java"],
    main_class = "com.yanicksenn.guice.timesource.TimeSourceConversion",
    deps = [
        "//java/com/yanicksenn/libraries/ranges",
        "//protos/datetime:datetime-proto-java",
        "@maven//:com_google_guava_guava",
    ],
)
