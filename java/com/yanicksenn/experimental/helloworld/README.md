# Hello, World!

Simple Hello-World program using java and bazel.

## Run

```bash
bazel run //java/com/yanicksenn/experimental/helloworld

# Output
Hello, World!

bazel run //java/com/yanicksenn/experimental/helloworld -- --name=Yanick

# Output
Hello, Yanick!
```