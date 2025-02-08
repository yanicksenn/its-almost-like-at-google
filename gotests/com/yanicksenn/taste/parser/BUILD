load("@rules_go//go:def.bzl", "go_test")

go_test(
    name = "parser",
    srcs = ["parser_test.go"],
    deps = [
        "//go/com/yanicksenn/taste/parser",
        "//go/com/yanicksenn/taste/shared",
        "//go/com/yanicksenn/taste/tokenizer"
    ],
    visibility = ["//visibility:private"],
)
