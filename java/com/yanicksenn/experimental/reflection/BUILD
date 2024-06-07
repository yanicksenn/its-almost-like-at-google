load("@rules_java//java:defs.bzl", "java_binary", "java_library")

java_library(
    name = "class_path_lister_lib",
    srcs = ["TestClass.java"],
)

java_binary(
    name = "class_path_lister",
    srcs = ["ClassPathLister.java"],
    main_class = "com.yanicksenn.experimental.reflection.ClassPathLister",
    deps = [
        ":class_path_lister_lib",
        "//java/com/yanicksenn/flags",
        "@maven//:com_google_guava_guava",
    ],
)
