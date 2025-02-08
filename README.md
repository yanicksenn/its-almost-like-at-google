# Bazel mono repo

A mono repo consisting of mainly Google's open source tools, libraries and frameworks. 

- [Bazel](https://bazel.build/)
- [Protobuf](https://protobuf.dev/)
- [Guice](https://github.com/google/guice)
- [gRPC](https://grpc.io/)

## Why?

Because somehow I really enjoy that tech stack. It's quite different to what most common software engineering companies use.

## Usage

Add the following lines to your preferred shell .rc file:

```bash
# E.g. ~/.zshrc
__BAZEL_ROOT=$USER/Work
source "$__BAZEL_ROOT/source_me.sh"
relink
```

