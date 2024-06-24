load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "gamblers_fallacy-lib",
    srcs = [
        "lib.py",
    ]
)

py_binary(
    name = "gamblers_fallacy",
    srcs = ["main.py"],
    main = "main.py",
    deps = [
        ":gamblers_fallacy-lib",
        "//python/com/yanicksenn/libraries/flags",
    ],
)
