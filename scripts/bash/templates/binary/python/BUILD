sh_binary(
    name = "run",
    srcs = ["run.sh"],
    data = [
        "data/BUILD.template",
        "data/README.md.template",
        "data/main.py.template",
        "data/lib.py.template",
        "data/RULES",
        "//python/com/yanicksenn/templating",
    ],
    deps = ["@bazel_tools//tools/bash/runfiles"],
)