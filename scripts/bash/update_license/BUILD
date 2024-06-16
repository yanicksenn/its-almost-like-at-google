sh_binary(
    name = "update_license",
    srcs = ["update_license.sh"],
    data = [
        "data/LICENSE.template",
        "data/LICENSE.template.rules",
        "//python/com/yanicksenn/templating",
    ],
    deps = ["@bazel_tools//tools/bash/runfiles"],
)
