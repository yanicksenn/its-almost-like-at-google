load("@rules_go//go:def.bzl", "go_test")

go_test(
    name = "taste_parser",
    srcs = ["parser_test.go"],
    deps = [
        "//go/com/yanicksenn/taste/shared",
        "//go/com/yanicksenn/taste/taste_parser",
        "//go/com/yanicksenn/taste/taste_tokenizer"
    ],
    visibility = ["//visibility:private"],
)
