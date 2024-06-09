load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "templating-engine",
    srcs = [
        "rules.py",
        "templating_engine.py",
    ],
)

py_binary(
    name = "templating",
    srcs = ["templating.py"],
    main = "templating.py",
    deps = [
        ":templating-engine",
        "//python/com/yanicksenn/flags",
    ],
)
