load("@rules_java//java:defs.bzl", "java_binary")

java_binary(
    name = "writer",
    srcs = ["SqliteWriter.java"],
    main_class = "com.yanicksenn.experimental.sqlite.SqliteWriter",
    deps = [
        "//java/com/yanicksenn/libraries/flags",
        "@maven//:org_xerial_sqlite_jdbc"
    ],
)

java_binary(
    name = "reader",
    srcs = ["SqliteReader.java"],
    main_class = "com.yanicksenn.experimental.sqlite.SqliteReader",
    deps = [
        "//java/com/yanicksenn/libraries/flags",
        "@maven//:org_xerial_sqlite_jdbc"
    ],
)