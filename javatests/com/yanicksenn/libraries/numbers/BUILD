load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "test",
    srcs = glob(["*.java"]),
    test_class = "com.yanicksenn.libraries.numbers.NumbersTest",
    deps = [
        "//java/com/yanicksenn/libraries/functions",
        "//java/com/yanicksenn/libraries/numbers",
        "@maven//:junit_junit",
        "@maven//:com_google_truth_truth"
    ]
)