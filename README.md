# Bazel mono repo

A mono repo consisting of mainly Google's open source tools, libraries and frameworks. 

- [Bazel](https://bazel.build/)
- [Protobuf](https://protobuf.dev/)
- [gRPC](https://grpc.io/)
- [Guice](https://github.com/google/guice)
- [Mercurial](https://www.mercurial-scm.org/)

## Why?

Because somehow I really enjoy that tech stack. It's fundamentally different to what most common software engineering companies use.

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

## Libraries

### Flags

A very simple command-line flag parser.

```
//java/com/yanicksenn/flags
```