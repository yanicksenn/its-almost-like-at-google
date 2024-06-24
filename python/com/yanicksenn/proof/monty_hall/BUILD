load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "monty_hall-lib",
    srcs = [
        "lib.py",
    ],
    deps = [
        "//python/com/yanicksenn/libraries/logging",
    ]
)

py_binary(
    name = "monty_hall",
    srcs = ["main.py"],
    main = "main.py",
    deps = [
        ":monty_hall-lib",
        "//python/com/yanicksenn/libraries/flags",
        "//python/com/yanicksenn/libraries/logging",
    ],
)
