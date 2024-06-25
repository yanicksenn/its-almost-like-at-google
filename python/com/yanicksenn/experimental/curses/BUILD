load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "curses-lib",
    srcs = [
        "lib.py",
    ],
    deps = [
        "//python/com/yanicksenn/libraries/logging",
    ]
)

py_binary(
    name = "curses",
    srcs = ["main.py"],
    main = "main.py",
    deps = [
        ":curses-lib",
        "//python/com/yanicksenn/libraries/flags",
    ],
)
