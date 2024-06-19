# Search TODOs

Recursively finds all TODOs inside all files of a directory.

## TODO format

All TODOs have to be in the following format to be found.

`TODO - yanicksenn: Change this to do something else.` \
`TODO - context: description`

## Usage

```bash
# Finds all TODOs with any context in the workspace.ss
bazel run //python/com/yanicksenn/search/todos:todos -- --root_dir=/Path/to/code
```

```bash
# Finds all TODOs with the given context in the workspace.
bazel run //python/com/yanicksenn/search/todos:todos -- --root_dir=/Path/to/code \
    --context=yanicksenn
```

```bash
# Finds all TODOs with any context in the workspace and returns some debugging information.
bazel run //python/com/yanicksenn/search/todos:todos -- --root_dir=/Path/to/code \
    --debug
```