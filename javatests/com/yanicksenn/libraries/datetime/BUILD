load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "DateTimeUtilsValidateTimeTest",
    srcs = ["DateTimeUtilsValidateTimeTest.java"],
    deps = [
        "//java/com/yanicksenn/libraries/datetime",
        "//protos/datetime:datetime-proto-java",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_truth_truth",
        "@maven//:pl_pragmatists_JUnitParams",
    ],
)
