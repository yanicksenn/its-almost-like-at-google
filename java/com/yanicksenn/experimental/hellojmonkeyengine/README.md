# Hello, jmonkeyengine

Hello world application using the jmonkeyengine.

- [website](https://jmonkeyengine.org/)
- [javadoc](https://javadoc.jmonkeyengine.org/v3.6.0-stable/index.html)

## Usage

Using macOS:

```
bazel run //java/com/yanicksenn/experimental/hellojmonkeyengine:hellojmonkeyengine-macos
```

Using anything else:

```
bazel run //java/com/yanicksenn/experimental/hellojmonkeyengine:hellojmonkeyengine
```

Showing framerate and stats:

```
bazel run //java/com/yanicksenn/experimental/hellojmonkeyengine:hellojmonkeyengine -- --show_info
```