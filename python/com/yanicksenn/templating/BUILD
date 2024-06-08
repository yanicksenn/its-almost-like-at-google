load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "templating-engine",
    srcs = ["templating_engine.py"]
)

py_binary(
    name = "templating",
    srcs = ["templating.py"],
    deps = [
        ":templating-engine", 
        "//python/com/yanicksenn/flags"
    ],
    main = "templating.py"
)