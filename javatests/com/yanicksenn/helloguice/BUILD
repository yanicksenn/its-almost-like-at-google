load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "test",
    srcs = glob(["*.java"]),
    test_class = "com.yanicksenn.helloguice.HelloGuiceTest",
    deps = [
        "//java/com/yanicksenn/helloguice:helloguice-lib",
        "@maven//:junit_junit",
        "@maven//:com_google_truth_truth",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_inject_guice",
        "@maven//:com_google_protobuf_protobuf_java",
    ]
)