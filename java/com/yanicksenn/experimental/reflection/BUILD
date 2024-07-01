load("@rules_java//java:defs.bzl", "java_binary")

java_binary(
    name = "class_path_lister",
    srcs = ["ClassPathLister.java"],
    main_class = "com.yanicksenn.experimental.reflection.ClassPathLister",
    deps = [
        "//java/com/yanicksenn/libraries/flags",
        "@maven//:com_google_guava_guava",
    ],
)
