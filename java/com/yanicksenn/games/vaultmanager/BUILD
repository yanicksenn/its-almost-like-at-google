load("@rules_java//java:defs.bzl", "java_binary", "java_library")

package(default_visibility = ["//visibility:public"])

package_group(
    name = "vaultmanager",
)

java_library(
    name = "vaultmanager-lib",
    srcs = glob(["*.java"]),
    resources = [
        "resources/firstnames_female.txt",
        "resources/firstnames_male.txt",
        "resources/lastnames.txt",
    ],
    deps = [
        "//java/com/yanicksenn/games/vaultmanager/proto:proto-java",
        "//java/com/yanicksenn/guice/random",
        "//java/com/yanicksenn/libraries/datetime",
        "//java/com/yanicksenn/libraries/flags",
        "//java/com/yanicksenn/libraries/ranges",
        "//java/com/yanicksenn/libraries/resources",
        "//java/plugins/autovalue:processor",
        "//protos/datetime:datetime-proto-java",
        "@maven//:com_google_auto_value_auto_value",
        "@maven//:com_google_auto_value_auto_value_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_inject_guice",
    ],
)

java_binary(
    name = "vaultmanager-bin",
    main_class = "com.yanicksenn.games.vaultmanager.Launcher",
    runtime_deps = [":vaultmanager-lib"],
)
