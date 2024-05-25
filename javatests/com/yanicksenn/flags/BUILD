load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "test",
    srcs = glob(["*.java"]),
    test_class = "com.yanicksenn.flags.FlagsTest",
    deps = [
        "//java/com/yanicksenn/flags",
        "@maven//:junit_junit"
    ]
)