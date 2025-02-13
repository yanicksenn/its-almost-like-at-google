load("@rules_go//go:def.bzl", "go_binary")

go_binary(
    name = "main",
    srcs = ["main.go"],
    deps = [
	"//go/com/yanicksenn/taste/shared",
	"//go/com/yanicksenn/taste/taste_parser",
    	"//go/com/yanicksenn/taste/taste_tokenizer",
    	"//go/com/yanicksenn/taste/taste_writer"
    ]

)
