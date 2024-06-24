load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "templating-engine",
    srcs = [
        "templating_engine.py",
    ],
    deps = [
        "//python/com/yanicksenn/libraries/logging",
        "//python/com/yanicksenn/tools/templating/rules",
    ]
)

py_binary(
    name = "templating",
    srcs = ["templating.py"],
    main = "templating.py",
    deps = [
        ":templating-engine",
        "//python/com/yanicksenn/libraries/flags",
    ],
)
