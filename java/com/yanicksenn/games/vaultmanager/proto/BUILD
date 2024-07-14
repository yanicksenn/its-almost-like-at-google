load("@rules_proto//proto:defs.bzl", "proto_library")

package(default_visibility = ["//java/com/yanicksenn/games/vaultmanager:__subpackages__"])

proto_library(
    name = "proto",
    srcs = ["data.proto"],
    deps = [
        "//protos/date:dates-proto",
    ],
)

java_proto_library(
    name = "proto-java",
    deps = [":proto"],
)
