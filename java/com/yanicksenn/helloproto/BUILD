load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("@rules_proto//proto:defs.bzl", "proto_library")

proto_library(
    name = "payload_proto",
    srcs = ["data.proto"],
)

java_proto_library(
    name = "payload_java_proto",
    deps = [":payload_proto"],
)

java_library(
    name = "helloproto-lib",
    srcs = glob(["*.java"]),
    deps = [
        ":payload_java_proto",
        "@maven//:com_google_guava_guava",
    ],
)

java_binary(
    name = "helloproto",
    main_class = "com.yanicksenn.helloproto.HelloProto",
    runtime_deps = [":helloproto-lib"],
)
