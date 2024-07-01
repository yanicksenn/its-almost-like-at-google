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
__BAZEL_ROOT=/Users/yanicksenn/Documents/Projects/bazel
source "$__BAZEL_ROOT/source_me.sh"
relink
```

## Binaries

List of binaries present in this mono repo.

### Hello, World!
```bash
bazel run //java/com/yanicksenn/helloworld -- --name=Yanick
```

### Hello, Proto!
```bash
bazel run //java/com/yanicksenn/helloproto
```

### Hello, Guice!
```bash
bazel run //java/com/yanicksenn/helloguice
```

### Hello, gRPC!
```bash
# Server
bazel run //java/com/yanicksenn/hellogrpc:hello_service_server

# Client
bazel run //java/com/yanicksenn/hellogrpc:hello_service_client
```

### Templating

Simple templating engine with small set of replacement rules.

See [docs](/python/com/yanicksenn/tools/templating/README.md).

### TODOs

Recursively finds all TODOs inside all files of a directory.

See [docs](/python/com/yanicksenn/tools/todos/README.md).

### Proofs

Proofs and thought experiments such as the Gambler's Fallacy and Monty Hall.

See [docs](/python/com/yanicksenn/proof/README.md).

## Libraries

### Flags

A very simple command-line flag parser.

```bash
# Java
//java/com/yanicksenn/libraries/flags
```

```bash
# Python
//python/com/yanicksenn/libraries/flags
```