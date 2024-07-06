load("@rules_java//java:defs.bzl", "java_binary", "java_library")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "hellojmonkeyengine-lib",
    srcs = glob(["*.java"]),
    deps = [
        "//java/com/yanicksenn/libraries/flags",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_desktop",
        "@maven//:org_jmonkeyengine_jme3_lwjgl3",
    ],
)

java_binary(
    name = "hellojmonkeyengine",
    main_class = "com.yanicksenn.experimental.hellojmonkeyengine.Launcher",
    runtime_deps = [":hellojmonkeyengine-lib"],
)

java_binary(
    name = "hellojmonkeyengine-macos",
    jvm_flags = [
        "-XstartOnFirstThread",
    ],
    main_class = "com.yanicksenn.experimental.hellojmonkeyengine.Launcher",
    runtime_deps = [":hellojmonkeyengine-lib"],
)
