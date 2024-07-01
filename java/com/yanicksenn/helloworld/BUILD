load("@rules_java//java:defs.bzl", "java_binary", "java_library")

java_library(
    name = "helloworld-lib",
    srcs = glob(["*.java"]),
    deps = ["//java/com/yanicksenn/libraries/flags"],
)

java_binary(
    name = "helloworld",
    main_class = "com.yanicksenn.helloworld.HelloWorld",
    runtime_deps = [":helloworld-lib"],
)
