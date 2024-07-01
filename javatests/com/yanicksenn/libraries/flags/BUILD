load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "test",
    srcs = glob(["*.java"]),
    test_class = "com.yanicksenn.libraries.flags.FlagsTest",
    deps = [
        "//java/com/yanicksenn/libraries/flags",
        "@maven//:junit_junit",
        "@maven//:com_google_truth_truth"
    ]
)