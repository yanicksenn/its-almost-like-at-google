load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "test",
    srcs = glob(["*.java"]),
    test_class = "com.yanicksenn.numbers.NumbersTest",
    deps = [
        "//java/com/yanicksenn/functions",
        "//java/com/yanicksenn/numbers",
        "@maven//:junit_junit",
        "@maven//:com_google_truth_truth"
    ]
)