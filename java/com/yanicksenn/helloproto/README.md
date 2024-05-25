# Hello, Proto!

Simple program using java, bazel and protobuf.

## Run

```
% bazel run //java/com/yanicksenn/helloproto
name: "Payload"
timestamp: 1716664411389
data: "Lorem ipsum dolor sit amet"
flags {
  key: "ABC"
  value: "DEF"
}
flags {
  key: "123"
  value: "456"
}
```