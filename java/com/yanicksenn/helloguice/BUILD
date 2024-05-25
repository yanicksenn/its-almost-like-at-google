load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("@rules_proto//proto:defs.bzl", "proto_library")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "helloguice-lib",
    srcs = glob(["*.java"]),
    deps = [
        ":data_java_proto",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_inject_guice",
        "@maven//:com_google_protobuf_protobuf_java",
    ],
)

java_binary(
    name = "helloguice",
    main_class = "com.yanicksenn.helloguice.HelloGuiceLauncher",
    runtime_deps = [":helloguice-lib"],
)

proto_library(
    name = "data_proto",
    srcs = ["data.proto"],
)

java_proto_library(
    name = "data_java_proto",
    deps = [":data_proto"],
)
