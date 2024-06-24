load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "__PACKAGE_NAME-lib",
    srcs = [
        "lib.py",
    ],
    deps = [
        "//python/com/yanicksenn/libraries/logging",
    ]
)

py_binary(
    name = "__PACKAGE_NAME",
    srcs = ["main.py"],
    main = "main.py",
    deps = [
        ":__PACKAGE_NAME-lib",
        "//python/com/yanicksenn/libraries/flags",
        "//python/com/yanicksenn/libraries/logging",
    ],
)
