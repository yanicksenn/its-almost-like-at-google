load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "test",
    srcs = glob(["*.java"]),
    test_class = "com.yanicksenn.experimental.helloguice.HelloGuiceTest",
    deps = [
        "//java/com/yanicksenn/experimental/helloguice:helloguice-lib",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_inject_guice",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@protobuf//java/core",
    ],
)
