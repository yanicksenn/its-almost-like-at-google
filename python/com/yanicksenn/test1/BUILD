load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "test1-lib",
    srcs = [
        "lib.py",
    ]
)

py_binary(
    name = "test1",
    srcs = ["main.py"],
    main = "main.py",
    deps = [
        ":test1-lib",
        "//python/com/yanicksenn/flags",
    ],
)
