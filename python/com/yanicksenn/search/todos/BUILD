load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "todos-lib",
    srcs = [
        "search_todos_lib.py",
    ]
)

py_binary(
    name = "todos",
    srcs = ["search_todos.py"],
    main = "search_todos.py",
    deps = [
        ":todos-lib",
        "//python/com/yanicksenn/libraries/flags",
    ],
)
