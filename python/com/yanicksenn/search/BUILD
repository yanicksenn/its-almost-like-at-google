load("@rules_python//python:defs.bzl", "py_binary", "py_library")

package(default_visibility = ["//visibility:public"])

py_library(
    name = "search-todos-lib",
    srcs = [
        "search_todos_lib.py",
    ]
)

py_binary(
    name = "search-todos",
    srcs = ["search_todos.py"],
    main = "search_todos.py",
    deps = [
        ":search-todos-lib",
        "//python/com/yanicksenn/flags",
    ],
)
