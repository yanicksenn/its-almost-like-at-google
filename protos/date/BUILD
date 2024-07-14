load("@rules_proto//proto:defs.bzl", "proto_library")

package(default_visibility = ["//visibility:public"])

proto_library(
    name = "dates-proto",
    srcs = ["data.proto"],
)

java_proto_library(
    name = "dates-proto-java",
    deps = [":dates-proto"],
)
