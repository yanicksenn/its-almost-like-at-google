load("@grpc-java//:java_grpc_library.bzl", "java_grpc_library")
load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_proto//proto:defs.bzl", "proto_library")

proto_library(
    name = "hello_service_proto",
    srcs = ["hello_service.proto"],
)

java_proto_library(
    name = "hello_service_java_proto",
    deps = [":hello_service_proto"],
)

java_grpc_library(
    name = "hello_service_java_grpc",
    srcs = [":hello_service_proto"],
    deps = [":hello_service_java_proto"],
)

java_binary(
    name = "hello_service_client",
    srcs = ["HelloClient.java"],
    main_class = "com.yanicksenn.hellogrpc.HelloClient",
    deps = [
        ":hello_service_java_grpc",
        ":hello_service_java_proto",
        "@grpc-java//api",
    ],
    runtime_deps = [
        "@grpc-java//netty",
    ],
)

java_binary(
    name = "hello_service_server",
    srcs = ["HelloServer.java"],
    main_class = "com.yanicksenn.hellogrpc.HelloServer",
    deps = [
        ":hello_service_java_grpc",
        ":hello_service_java_proto",
        "@grpc-java//api",
        "@grpc-java//stub"
    ],
    runtime_deps = [
        "@grpc-java//netty",
    ],
)
